<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>配置专家</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <meta charset='utf-8' />
    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
    <script src="plug-in/jquery/jquery-1.8.3.js"/>
    <script src='plug-in/fullCalendar/lib/jquery.min.js'></script>
    <script src='plug-in/fullCalendar/lib/moment.min.js'></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>
    <script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
</head>
<body style="overflow-y: hidden" scroll="no">
<form id="addForm" method="post"  action="reviewExpertController.do?save" enctype="multipart/form-data">
    <table style="width: 100%;"  align="center" cellpadding="0" cellspacing="1" class="formtable">
        <thead>
        <tr>
            <th colspan="2">
                <label class="Validform_label">专家姓名</label>
            </th>
            <th colspan="3">
                <label class="Validform_label">时间</label>
            </th>
            <th colspan="3">
                <label class="Validform_label">保存的时间</label>
            </th>
            <th colspan="2">
                <label class="Validform_label">编辑</label>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="2" style="text-align: center">
                ${expertName}
            </td>
            <td colspan="3" id="weektd">
                <table style="width: 100%;text-align: center">
                    <tr>
                        <td id="Mon" >
                            <label class="Validform_label">周一&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="Mstart" size="5" />
                                <input id="Mend" size="5" />
                                [<a href="#" onclick="saveSchedulingTime('周一')">添加</a>]
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td id="Tues" >
                            <label class="Validform_label">周二&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="Tustart" size="5" />
                                <input id="Tuend" size="5" />
                                [<a href="#" onclick="saveSchedulingTime('周二')">添加</a>]
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td id="Wednes" >
                            <label class="Validform_label">周三&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="Westart" size="5" />
                                <input id="Weend" size="5" />
                                [<a href="#" onclick="saveSchedulingTime('周三')">添加</a>]
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td id="Thurs" >
                            <label class="Validform_label">周四&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="Thstart" size="5" />
                                <input id="Thend" size="5" />
                                [<a href="#" onclick="saveSchedulingTime('周四')">添加</a>]
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td id="Fri" >
                            <label class="Validform_label">周五&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="Frstart" size="5" />
                                <input id="Frend" size="5" />
                                [<a href="#" onclick="saveSchedulingTime('周五')">添加</a>]
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td id="Satur" >
                            <label class="Validform_label">周六&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="Sastart" size="5" />
                                <input id="Saend" size="5" />
                                [<a href="#" onclick="saveSchedulingTime('周六')">添加</a>]
                            </label>
                        </td>
                    </tr>
                    <tr>
                        <td id="Sun" >
                            <label class="Validform_label">周日&nbsp;&nbsp;&nbsp;&nbsp;
                                <input id="Sustart" size="5" />
                                <input id="Suend" size="5" />
                                [<a href="#" onclick="saveSchedulingTime('周日')">添加</a>]
                            </label>
                        </td>
                    </tr>
                </table>
            </td>
            <td colspan="3" style="text-align: left">
                <div id="SchedulingTime" style="margin: 8px;word-wrap: break-word;height:auto;">
                    <table id="SchedulingTimeTable">
                        <c:if test="${list != null}">
                            <c:forEach var="item" items="${list}">
                                <tr>
                                    <td id="${item.id}">
                                        <c:out value="${item.calendar}"/>
                                    </td>
                                    <td id="del${item.id}">
                                        [<a href="#" onclick="deleteExitSchedulingTime(${item.id})">删除</a>]
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                </div>
            </td>

            <td colspan="2" style="text-align: center">
                <a style="cursor: pointer" onclick="saveSchedulingTimeAll()">保存</a>
            </td>
        </tr>
        </tbody>
    </table>
</form>
<script type="text/javascript">
    //全局变量-专家id
    var id = ${id};
    //时间段构造函数
    function SchedulingTimeTable(week,startTime,endTime){
        this.week = week;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    //保存时间段数据并实时回显
    function saveSchedulingTime(flag){
        /*var startTime = $("#Mstart").val();
        var endTime = $("#Mend").val();
        var tempTime = "周一："+startTime+"-"+endTime;
        var divhtml = tempTime+"[<a href='#' onclick='saveSchedulingTimeAll()'>删除</a>]"+"\n";
        var br = document.createElement("br");
        $("#SchedulingTime").append(divhtml);
        $("#SchedulingTime").append(br);*/
        var timeSlot1 = flag;
        var table = document.getElementById("SchedulingTimeTable");
        //创建行
        var tr = document.createElement('tr');
        table.appendChild(tr);
        var startTime = $("#Mstart").val();
        var endTime = $("#Mend").val();
        //获取输入的时间段
        var timeSlotAll = new SchedulingTimeTable(timeSlot1,startTime,endTime);
        var ss = "";
        var td = document.createElement('td');
        //拼接周几和输入的时间段并放入tr
        for (var k in timeSlotAll){
            ss += timeSlotAll[k]+"|";
        }
        td.innerHTML = ss;
        tr.appendChild(td);
        //删除功能
        var td1 = document.createElement('td');
        td1.innerHTML = "[<a href= 'javascript:;'>删除</a>]";
        tr.appendChild(td1);
        td1.onclick = function (){
            table.removeChild(this.parentElement);
        }
    }
    //保存所有时间段数据
    function saveSchedulingTimeAll(){
        var table = document.getElementById("SchedulingTimeTable");
        var child = table.getElementsByTagName("tr")[0];
        var text = child.firstChild.innerHTML;
        alert("专家id："+id+":"+text);
        alert("保存成功！");
    }
    function deleteExitSchedulingTime(id){
        var td = document.getElementById(id);
        var deltd = document.getElementById("del"+id);
        //移除时间段td
        td.remove();
        deltd.remove();
        //删除数据
        alert("已删除:"+id);
    }
</script>
</body>