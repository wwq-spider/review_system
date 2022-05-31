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
    <%--<script src="plug-in/jquery/jquery-1.8.3.js"/>--%>
    <%--<script src='plug-in/fullCalendar/lib/jquery.min.js'></script>--%>
    <%--<script src='plug-in/fullCalendar/lib/moment.min.js'></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>--%>
    <%--<script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>--%>
</head>
<body style="overflow-y: hidden" scroll="no">
<form id="addForm" method="post" action="reviewExpertController.do?saveCalendarInfo" enctype="multipart/form-data">
    <input type="hidden" id="btn_sub" class="btn_sub" onclick="saveSchedulingTimeAll();"/>
    <input id="alltime" name="alltime" type="hidden" value="">
    <input id="id" name="id" type="hidden" value="${id}">
    <table id="SchedulingTimeTable" style="width: 100%;" align="center" cellpadding="0" cellspacing="1"
           class="formtable">
        <thead>
        <tr>
            <th colspan="1" style="width: 20px">
                <label class="Validform_label">专家姓名</label>
            </th>
            <th colspan="4" style="width: 40px">
                <label class="Validform_label">日期</label>
            </th>
            <th colspan="1" style="width: 40px">
                <label class="Validform_label">日历</label>
            </th>
        </tr>
        </thead>
        <tbody id="weektd">
        <tr id="MonTr">
            <td style="text-align: center" rowspan="7">
                ${expertName}
            </td>
            <td style="text-align: center">周一&nbsp;</td>
            <td>
                <input type="text" id="Mstart" name="Mstart" list="MstartList" style="width:70px;height:15px;">
                <datalist id="MstartList">
                    <select name="Mstart"></select>
                </datalist>
            </td>
            <td>
                <input type="text" id="Mend" name="Mend" list="MendList" style="width:70px;height:15px;">
                <datalist id="MendList">
                    <select name="Mend"></select>
                </datalist>
            </td>
            <td>
                [<a href="#" onclick="saveSchedulingTime('1')">添加</a>]
            </td>
            <td id="Mon">
                <input type="hidden" id="MonI" value=""/>
                <c:forEach items="${list}" var="item">
                    <c:if test="${item.weekDay == '1'}">
                        <div id="${item.id}" style="display: inline-block;white-space: nowrap;float: left">
                                ${item.beginTime}|${item.endTime}[<a href='#'
                                                                     onclick="deleteCalendar(${item.id})">删除</a>],
                        </div>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">周二&nbsp;</td>
            <td>
                <input type="text" id="Tustart" name="Tustart" list="TustartList" style="width:70px;height:15px;">
                <datalist id="TustartList">
                    <select name="Tustart"></select>
                </datalist>
            </td>
            <td>
                <input type="text" id="Tuend" name="Tuend" list="TuendList" style="width:70px;height:15px;">
                <datalist id="TuendList">
                    <select name="Tuend"></select>
                </datalist>
            </td>
            <td>
                [<a href="#" onclick="saveSchedulingTime('2')">添加</a>]
            </td>
            <td id="Tues">
                <input type="hidden" id="TuesI" value=""/>
                <c:forEach items="${list}" var="item">
                    <c:if test="${item.weekDay == '2'}">
                        <div id="${item.id}" style="display: inline-block;white-space: nowrap;float: left">
                                ${item.beginTime}|${item.endTime}[<a href='#'
                                                                     onclick="deleteCalendar(${item.id})">删除</a>],
                        </div>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">周三&nbsp;</td>
            <td>
                <input type="text" id="Westart" name="Westart" list="WestartList" style="width:70px;height:15px;">
                <datalist id="WestartList">
                    <select name="Westart"></select>
                </datalist>
            </td>
            <td>
                <input type="text" id="Weend" name="Weend" list="WeendList" style="width:70px;height:15px;">
                <datalist id="WeendList">
                    <select name="Weend"></select>
                </datalist>
            </td>
            <td>
                [<a href="#" onclick="saveSchedulingTime('3')">添加</a>]
            </td>
            <td id="Wednes">
                <input type="hidden" id="WednesI" value=""/>
                <c:forEach items="${list}" var="item">
                    <c:if test="${item.weekDay == '3'}">
                        <div id="${item.id}" style="display: inline-block;white-space: nowrap;float: left">
                                ${item.beginTime}|${item.endTime}[<a href='#'
                                                                     onclick="deleteCalendar(${item.id})">删除</a>],
                        </div>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">周四&nbsp;</td>
            <td>
                <input type="text" id="Thstart" name="Thstart" list="ThstartList" style="width:70px;height:15px;">
                <datalist id="ThstartList">
                    <select name="Thstart"></select>
                </datalist>
            </td>
            <td>
                <input type="text" id="Thend" name="Thend" list="ThendList" style="width:70px;height:15px;">
                <datalist id="ThendList">
                    <select name="Thend"></select>
                </datalist>
            </td>
            <td>
                [<a href="#" onclick="saveSchedulingTime('4')">添加</a>]
            </td>
            <td id="Thurs">
                <input type="hidden" id="ThursI" value=""/>
                <c:forEach items="${list}" var="item">
                    <c:if test="${item.weekDay == '4'}">
                        <div id="${item.id}" style="display: inline-block;white-space: nowrap;float: left">
                                ${item.beginTime}|${item.endTime}[<a href='#'
                                                                     onclick="deleteCalendar(${item.id})">删除</a>],
                        </div>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">周五&nbsp;</td>
            <td>
                <input type="text" id="Frstart" name="Frstart" list="FrstartList" style="width:70px;height:15px;">
                <datalist id="FrstartList">
                    <select name="Frstart"></select>
                </datalist>
            </td>
            <td>
                <input type="text" id="Frend" name="Frend" list="FrendList" style="width:70px;height:15px;">
                <datalist id="FrendList">
                    <select name="Frend"></select>
                </datalist>
            </td>
            <td>
                [<a href="#" onclick="saveSchedulingTime('5')">添加</a>]
            </td>
            <td id="Fri">
                <input type="hidden" id="FriI" value=""/>
                <c:forEach items="${list}" var="item">
                    <c:if test="${item.weekDay == '5'}">
                        <div id="${item.id}" style="display: inline-block;white-space: nowrap;float: left">
                                ${item.beginTime}|${item.endTime}[<a href='#'
                                                                     onclick="deleteCalendar(${item.id})">删除</a>],
                        </div>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">周六&nbsp;</td>
            <td>
                <input type="text" id="Sastart" name="MeSastart" list="SastartList" style="width:70px;height:15px;">
                <datalist id="SastartList">
                    <select name="Sastart"></select>
                </datalist>
            </td>
            <td>
                <input type="text" id="Saend" name="Saend" list="SaendList" style="width:70px;height:15px;">
                <datalist id="SaendList">
                    <select name="Saend"></select>
                </datalist>
            </td>
            <td>
                [<a href="#" onclick="saveSchedulingTime('6')">添加</a>]
            </td>
            <td id="Satur">
                <input type="hidden" id="SaturI" value=""/>
                <c:forEach items="${list}" var="item">
                    <c:if test="${item.weekDay == '6'}">
                        <div id="${item.id}" style="display: inline-block;white-space: nowrap;float: left">
                                ${item.beginTime}|${item.endTime}[<a href='#'
                                                                     onclick="deleteCalendar(${item.id})">删除</a>],
                        </div>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">周日&nbsp;</td>
            <td>
                <input type="text" id="Sustart" name="MeSustartd" list="SustartList" style="width:70px;height:15px;">
                <datalist id="SustartList">
                    <select name="Sustart"></select>
                </datalist>
            </td>
            <td>
                <input type="text" id="Suend" name="Suend" list="SuendList" style="width:70px;height:15px;">
                <datalist id="SuendList">
                    <select name="Suend"></select>
                </datalist>
            </td>
            <td>
                [<a href="#" onclick="saveSchedulingTime('7')">添加</a>]
            </td>
            <td id="Sun">
                <input type="hidden" id="SunI" value=""/>
                <c:forEach items="${list}" var="item">
                    <c:if test="${item.weekDay == '7'}">
                        <div id="${item.id}" style="display: inline-block;white-space: nowrap;float: left">
                                ${item.beginTime}|${item.endTime}[<a href='#'
                                                                     onclick="deleteCalendar(${item.id})">删除</a>],
                        </div>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
        </tbody>
    </table>
    <table style="width: 100%;" align="center" cellpadding="0" cellspacing="1" class="formtable">
        <tr style="text-align: center">
            <td>
                <div>
                    <button type="button" onclick="saveSchedulingTimeAll()">保存</button>
                </div>
            </td>
        </tr>
    </table>
</form>
<style type="text/css">
    table {
        table-layout: fixed;
        word-break: break-all;
    }
</style>
<script type="text/javascript">

    $(function () {
        var weektd = document.getElementById("weektd");
        var selectAll = weektd.getElementsByTagName("select");
        for (var i = 0; i < selectAll.length; i++) {
            addOptions(selectAll[i]);
        }
    })

    //给时间段下拉框选项赋值
    function addOptions(selectId) {
        selectId.options.add(new Option("9:00", "9:00"));
        selectId.options.add(new Option("9:30", "9:30"));
        selectId.options.add(new Option("10:00", "10:00"));
        selectId.options.add(new Option("10:30", "10:30"));
        selectId.options.add(new Option("11:00", "11:00"));
        selectId.options.add(new Option("11:30", "11:30"));
        selectId.options.add(new Option("12:00", "12:00"));
        selectId.options.add(new Option("12:30", "12:30"));
        selectId.options.add(new Option("13:00", "13:00"));
        selectId.options.add(new Option("13:30", "13:30"));
        selectId.options.add(new Option("14:00", "14:00"));
        selectId.options.add(new Option("14:30", "14:30"));
        selectId.options.add(new Option("15:00", "15:00"));
        selectId.options.add(new Option("15:30", "15:30"));
        selectId.options.add(new Option("16:00", "16:00"));
        selectId.options.add(new Option("16:30", "16:30"));
        selectId.options.add(new Option("17:00", "17:00"));
        selectId.options.add(new Option("17:30", "17:30"));
        selectId.options.add(new Option("18:00", "18:00"));
        selectId.options.add(new Option("18:30", "18:30"));
        selectId.options.add(new Option("19:00", "19:00"));
        selectId.options.add(new Option("19:30", "19:30"));
        selectId.options.add(new Option("20:00", "20:00"));
        selectId.options.add(new Option("20:30", "20:30"));
        selectId.options.add(new Option("21:00", "21:00"));
        selectId.options.add(new Option("21:30", "21:30"));
        selectId.options.add(new Option("22:00", "22:00"));
    }

    //全局变量-专家id
    var id = ${id};
    //所有时间段
    var alltime = "";

    //时间段构造函数
    function SchedulingTimeTable(startTime, endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //保存时间段数据并实时回显
    function saveSchedulingTime(flag) {
        var startTime;
        var endTime;
        var td;
        if (flag == "1") {
            td = document.getElementById("Mon");
            startTime = $("#Mstart").val();
            endTime = $("#Mend").val();
        } else if (flag == "2") {
            td = document.getElementById("Tues");
            startTime = $("#Tustart").val();
            endTime = $("#Tuend").val();
        } else if (flag == "3") {
            td = document.getElementById("Wednes");
            startTime = $("#Westart").val();
            endTime = $("#Weend").val();
        } else if (flag == "4") {
            td = document.getElementById("Thurs");
            startTime = $("#Thstart").val();
            endTime = $("#Thend").val();
        } else if (flag == "5") {
            td = document.getElementById("Fri");
            startTime = $("#Frstart").val();
            endTime = $("#Frend").val();
        } else if (flag == "6") {
            td = document.getElementById("Satur");
            startTime = $("#Sastart").val();
            endTime = $("#Saend").val();
        } else if (flag == "7") {
            td = document.getElementById("Sun");
            startTime = $("#Sustart").val();
            endTime = $("#Suend").val();
        } else {
            return false;
        }
        var div = document.createElement('div');
        div.style = "display: inline;";
        div.innerHTML = startTime + "|" + endTime + "|" + "[<a href= 'javascript:;'>删除</a>]" + "，";
        td.appendChild(div);
        var temp = alltime;//时间段中间变量
        alltime += flag + "," + startTime + "," + endTime + "-";
        div.onclick = function () {
            alltime = temp;
            div.innerHTML = "";
        }
    }

    //保存所有时间段数据
    function saveSchedulingTimeAll() {
        $("#alltime").val(alltime);
        /*$("#addForm").form('submit',{
        //保存日历信息
        var url = "reviewExpertController.do?saveCalendarInfo";
        var param = {
            "id": id,
            "alltime": alltime
        };
        $.post(url, param, function (data) {
            var d = $.parseJSON(data);
            if (d.success) {
                alert("保存成功！");
            } else {
                alert("保存失败！");
            }
        }
        });*/
        $("#addForm").form('submit',{
            onSubmit : function() {
            },
            success : function(data) {
                debugger;
                var d = $.parseJSON(data);
                var win = frameElement.api.opener;
                window.top.$.messager.progress('close');
                if (d.success == true) {
                    frameElement.api.close();
                    win.tip(d.msg);
                } else {
                    if (d.responseText == ''
                        || d.responseText == undefined)
                        $("#addForm").html(d.msg);
                    else
                        $("#addForm").html(d.responseText);
                    return false;
                }
                win.reloadTable();
            }
        });
    }
    //删除单个时间点日历
    function deleteCalendar(id) {
        var Div = document.getElementById(id);
        //删除数据
        var url = "reviewExpertController.do?deleteCalendarInfo";
        var param = {
            "id": id
        };
        $.post(url, param, function (data) {
            var d = $.parseJSON(data);
            if (d.success) {
                Div.innerHTML = "";
            }
        });
    }
</script>
</body>