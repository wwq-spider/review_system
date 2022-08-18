<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>配置专家</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <meta charset='utf-8'/>
    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
</head>
<body style="overflow-y: hidden" scroll="no">
<form id="addForm" method="post" action="reviewExpertController.do?saveCalendarInfo" enctype="multipart/form-data">
    <table id="SchedulingTimeTable" style="width: 100%;" align="center" cellpadding="0" cellspacing="1" class="formtable">
        <thead>
        <tr>
            <th colspan="1" style="width: 10px">
                <label class="Validform_label">专家姓名</label>
            </th>
            <th colspan="1" style="width: 20px">
                <label class="Validform_label">日期</label>
            </th>
            <th colspan="1" style="width: 35px">
                <label class="Validform_label">时间段</label>
            </th>
            <th colspan="1" style="width: 35px">
                <label class="Validform_label">日历</label>
            </th>
        </tr>
        <tbody id="weektd">
        <tr>
            <td style="text-align: center" rowspan="4">
                ${expertName}
            </td>
            <td>
                <input type="text" name="visitDay" id="visitDay" style="width: 100px; height: 18px;"></span>
            </td>
            <td id="allCheckBox" style="width: 100px">
                <input name="time" type="checkbox" value="09:00-10:00" style="width: 50px" onclick="javascript:selectAll(this.value);">09:00 - 10:00</input>
                <input name="time" type="checkbox" value="10:00-11:00" style="width: 50px" onclick="javascript:selectAll(this.value);">10:00 - 11:00</input>
                <input name="time" type="checkbox" value="11:00-12:00" style="width: 50px" onclick="javascript:selectAll(this.value);">11:00 - 12:00</input>
                <input name="time" type="checkbox" value="13:00-14:00" style="width: 50px" onclick="javascript:selectAll(this.value);">13:00 - 14:00</input>
                <input name="time" type="checkbox" value="14:00-15:00" style="width: 50px" onclick="javascript:selectAll(this.value);">14:00 - 15:00</input>
                <input name="time" type="checkbox" value="15:00-16:00" style="width: 50px" onclick="javascript:selectAll(this.value);">15:00 - 16:00</input>
                <input name="time" type="checkbox" value="16:00-17:00" style="width: 50px" onclick="javascript:selectAll(this.value);">16:00 - 17:00</input>
                <input name="time" type="checkbox" value="17:00-18:00" style="width: 50px" onclick="javascript:selectAll(this.value);">17:00 - 18:00</input>
                <input name="time" type="checkbox" value="18:00-19:00" style="width: 50px" onclick="javascript:selectAll(this.value);">18:00 - 19:00</input>
                <input name="time" type="checkbox" value="19:00-20:00" style="width: 50px" onclick="javascript:selectAll(this.value);">19:00 - 20:00</input>
                <input name="time" type="checkbox" value="20:00-21:00" style="width: 50px" onclick="javascript:selectAll(this.value);">20:00 - 21:00</input>
            </td>
            <td id="timeSlot" style="text-align: center">

            </td>
        </tr>
        </tbody>
        </thead>
    </table>
    <table style="width: 100%;" align="center" cellpadding="0" cellspacing="1" class="formtable">
        <div style="text-align: center">
            <input type="button" onclick="addTime()" value="添加到日历" />
        </div>
    </table>
    <table id="timeListTable" style="width: 100%;" align="center" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <th style="text-align: center" colspan="3">
                <label class="Validform_label">当月日历列表</label>
            </th>
        </tr>
        <tr style="text-align: center">
            <th colspan="1" style="width: 30px">
                <label class="Validform_label">日期</label>
            </th>
            <th colspan="1" style="width: 30px">
                <label class="Validform_label">周几</label>
            </th>
            <th colspan="1" style="width: 40px">
                <label class="Validform_label">时间段</label>
            </th>
        </tr>
        <tbody>
        <c:forEach items="${list}" var="item">
            <tr style="text-align: center">
                <td>
                        ${item.visitDate}
                </td>
                <td>
                        ${item.weekDayName}
                </td>
                <td>
                        ${item.beginTime}-${item.endTime}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
<style type="text/css">
    table {
        table-layout: fixed;
        word-break: break-all;
    }
</style>
<script type="text/javascript">
    var alltime;
    //全局变量-专家id
    var id = ${id};

    //初始化时间插件
    $(function () {
        $("#visitDay").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        var year = new Date().getFullYear();
        var month = new Date().getMonth()+1;
        var day = new Date().getDate();
        $("#visitDay").val(year + "-" + month + "-" + day);
    })
    function selectAll(flag){
        var beginTimeCurrent = flag.split("-")[0];
        var td = document.getElementById("timeSlot");
        var timeSlotText = td.textContent.trim();
        var allTimeSlotArray = timeSlotText.split(",");
        for (var i = 0; i < allTimeSlotArray.length-1; i++) {
            beginTime = allTimeSlotArray[i].split("-")[0];
            if (beginTime == beginTimeCurrent){//时间段重复，不添加
                return false;
            }
        }
        var td = document.getElementById("timeSlot");
        var div = document.createElement('div');
        var ids = document.getElementsByName("time");
        //选中
        if (ids.length>0){
            for (var i = 0; i < ids.length; i++) {
                if (ids[i].checked) {
                    div.innerHTML = flag + "-" + "[<a href= 'javascript:;'>删除</a>]" +",";
                    td.appendChild(div);
                }
            }
        }
        div.onclick = function () {
            div.innerHTML = "";
            var ids = document.getElementsByName("time");
            for (var i = 0; i < ids.length; i++) {
                ids[i].checked = false;
            }
        }
    }
    function addTime(){
        var visitDay = document.getElementById("visitDay").value;
        if (visitDay==null || visitDay ==""){
            alert("请选择日期！");
            return false;
        }
        //所选日期是否过期,未过期则保存
        checkDateLegal(visitDay);
    }
    function saveAllTimeInfo(visitDay,beginTime,endTime){
        var url = "reviewExpertController.do?saveCalendarInfo";
        var param = {
            "expertId" : id,
            "visitDate" : visitDay,
            "beginTime" : beginTime,
            "endTime" : endTime
        };
        $.post(url, param, function (data) {
            var d = $.parseJSON(data);
            if (d.success) {
                history.go(0);
            }
        });
    }
    function checkDateLegal(visitDay){
        //获取当前时间
        var currentTime = new Date().toLocaleDateString('cn',{hour12:false}); //转化成"yyyy/MM/dd"格式
        currentTime = new Date(currentTime);
        var visitDayTemp = visitDay.replace(/\-/g, "\/");//转化成"yyyy/MM/dd"格式
        visitDayTemp = new Date(visitDayTemp);
        if (currentTime > visitDayTemp){
            alert("所选日期过期，请重新选择！");
            return false;
        }else {
            var beginTime = "";
            var endTime = "";
            var td = document.getElementById("timeSlot");
            var timeSlotText = td.textContent.trim();
            var allTimeSlotArray = timeSlotText.split(",");
            for (var i = 0; i < allTimeSlotArray.length - 1; i++) {
                beginTime = allTimeSlotArray[i].split("-")[0];
                endTime = allTimeSlotArray[i].split("-")[1];
                saveAllTimeInfo(visitDay,beginTime,endTime);
            }
        }
    }

</script>
</body>