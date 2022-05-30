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
            <th colspan="2" rowspan="7">
                <label class="Validform_label">专家姓名</label>
            </th>
            <th colspan="4">
                <label class="Validform_label">日期</label>
            </th>
            <th colspan="4">
                <label class="Validform_label">日历</label>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="2" rowspan="7" style="text-align: center">
                ${expertName}
            </td>
            <td colspan="4" id="weektd">
                <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                    <tr>
                        <td id="Mon" >
                            <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                                <label class="Validform_label">
                                    <tr>
                                        <td colspan="2">周一&nbsp;</td>
                                        <td colspan="3">
                                            <input type="text" id="Mstart" name="Mstart" list="MstartList" style="width:70px;height:15px;">
                                            <datalist id="MstartList">
                                                <select  name="Mstart" onclick="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="Mend" name="Mend" list="MendList" style="width:70px;height:15px;">
                                            <datalist id="MendList">
                                                <select name="Mend" onclick="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="2">
                                            [<a href="#" onclick="saveSchedulingTime('周一')">添加</a>]
                                        </td>
                                    </tr>
                                </label>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="Tues" >
                            <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                                <label class="Validform_label">
                                    <tr>
                                        <td colspan="2">周二&nbsp;</td>
                                        <td colspan="3">
                                            <input type="text" id="Tustart" name="Tustart" list="TustartList" style="width:70px;height:15px;">
                                            <datalist id="TustartList">
                                                <select  name="Tustart" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="Tuend" name="Tuend" list="TuendList" style="width:70px;height:15px;">
                                            <datalist id="TuendList">
                                                <select name="Tuend" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="2">
                                            [<a href="#" onclick="saveSchedulingTime('周二')">添加</a>]
                                        </td>
                                    </tr>
                                </label>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="Wednes" >
                            <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                                <label class="Validform_label">
                                    <tr>
                                        <td colspan="2">周三&nbsp;</td>
                                        <td colspan="3">
                                            <input type="text" id="Westart" name="Westart" list="WestartList" style="width:70px;height:15px;">
                                            <datalist id="WestartList">
                                                <select  name="Westart" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="Weend" name="Weend" list="WeendList" style="width:70px;height:15px;">
                                            <datalist id="WeendList">
                                                <select name="Weend" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="2">
                                            [<a href="#" onclick="saveSchedulingTime('周三')">添加</a>]
                                        </td>
                                    </tr>
                                </label>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="Thurs" >
                            <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                                <label class="Validform_label">
                                    <tr>
                                        <td colspan="2">周四&nbsp;</td>
                                        <td colspan="3">
                                            <input type="text" id="Thstart" name="Thstart" list="ThstartList" style="width:70px;height:15px;">
                                            <datalist id="ThstartList">
                                                <select name="Thstart" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="Thend" name="Thend" list="ThendList" style="width:70px;height:15px;">
                                            <datalist id="ThendList">
                                                <select  name="Thend" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="2">
                                            [<a href="#" onclick="saveSchedulingTime('周四')">添加</a>]
                                        </td>
                                    </tr>
                                </label>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="Fri" >
                            <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                                <label class="Validform_label">
                                    <tr>
                                        <td colspan="2">周五&nbsp;</td>
                                        <td colspan="3">
                                            <input type="text" id="Frstart" name="Frstart" list="FrstartList" style="width:70px;height:15px;">
                                            <datalist id="FrstartList">
                                                <select  name="Frstart" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="Frend" name="Frend" list="FrendList" style="width:70px;height:15px;">
                                            <datalist id="FrendList">
                                                <select  name="Frend" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="2">
                                            [<a href="#" onclick="saveSchedulingTime('周五')">添加</a>]
                                        </td>
                                    </tr>
                                </label>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="Satur" >
                            <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                                <label class="Validform_label">
                                    <tr>
                                        <td colspan="2">周六&nbsp;</td>
                                        <td colspan="3">
                                            <input type="text" id="Sastart" name="MeSastart" list="SastartList" style="width:70px;height:15px;">
                                            <datalist id="SastartList">
                                                <select  name="Sastart" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="Saend" name="Saend" list="SaendList" style="width:70px;height:15px;">
                                            <datalist id="SaendList">
                                                <select  name="Saend" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="2">
                                            [<a href="#" onclick="saveSchedulingTime('周六')">添加</a>]
                                        </td>
                                    </tr>
                                </label>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td id="Sun" >
                            <table style="width: 100%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
                                <label class="Validform_label">
                                    <tr>
                                        <td colspan="2">周日&nbsp;</td>
                                        <td colspan="3">
                                            <input type="text" id="Sustart" name="MeSustartd" list="SustartList" style="width:70px;height:15px;">
                                            <datalist id="SustartList">
                                                <select name="Sustart" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="3">
                                            <input type="text" id="Suend" name="Suend" list="SuendList" style="width:70px;height:15px;">
                                            <datalist id="SuendList">
                                                <select name="Suend" onchange="selectTimePeriod();"></select>
                                            </datalist>
                                        </td>
                                        <td colspan="2">
                                            [<a href="#" onclick="saveSchedulingTime('周日')">添加</a>]
                                        </td>
                                    </tr>
                                </label>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
            <td colspan="4">
                <table id="SchedulingTimeTable" style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
                    <tr style="width: 100px;height: 23.5px">
                        <td id="mondata"><div id="rr"></div></td>
                    </tr>
                    <tr style="width: 100px;height: 23.5px">
                        <td id="mm"><div id="rr2"></div></td>
                    </tr>
                    <tr style="width: 100px;height: 23.5px">
                        <td id="ss"><div id="rr3"></div></td>
                    </tr>
                    <tr style="width: 100px;height: 23.5px">
                        <td id="vv"><div id="rr4"></div></td>
                    </tr>
                    <tr style="width: 100px;height: 23.5px">
                        <td id="ww"><div id="rr5"></div></td>
                    </tr>
                    <tr style="width: 100px;height: 23.5px">
                        <td id="zz"><div id="rr6"></div></td>
                    </tr>
                    <tr style="width: 100px;height: 23.5px">
                        <td id="wwq"><div id="rr7"></div></td>
                    </tr>
                </table>

                <%--<div id="SchedulingTime" style="margin: 8px;word-wrap: break-word;height:auto;">
                    <table id="SchedulingTimeTable" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
                        <label class="Validform_label">
                            <tr id="mondata"></tr>
                            <tr id="mondata2"></tr>
                            <tr id="mondata3"></tr>
                            <tr id="mondata4"></tr>
                            <tr id="mondata5"></tr>
                            <tr id="mondata6"></tr>
                            <tr id="mondata7"></tr>
                        </label>
                        &lt;%&ndash;<c:if test="${list != null}">
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
                        </c:if>&ndash;%&gt;
                    </table>
                </div>--%>
            </td>
        </tr>
        </tbody>
    </table>
</form>
<style type="text/css">
    table {
        table-layout:fixed;word-break:break-all;
    }
</style>
<script type="text/javascript">

    $(function (){
        var weektd = document.getElementById("weektd");
        var selectAll = weektd.getElementsByTagName("select");
        for (var i = 0; i < selectAll.length; i++) {
            addOptions(selectAll[i]);
        }
    })
    function addOptions(selectId){
        selectId.options.add(new Option("9:00","9:00"));
        selectId.options.add(new Option("9:30","9:30"));
        selectId.options.add(new Option("10:00","10:00"));
        selectId.options.add(new Option("10:30","10:30"));
        selectId.options.add(new Option("11:00","11:00"));
        selectId.options.add(new Option("11:30","11:30"));
        selectId.options.add(new Option("12:00","12:00"));
        selectId.options.add(new Option("12:30","12:30"));
        selectId.options.add(new Option("13:00","13:00"));
        selectId.options.add(new Option("13:30","13:30"));
        selectId.options.add(new Option("14:00","14:00"));
        selectId.options.add(new Option("14:30","14:30"));
        selectId.options.add(new Option("15:00","15:00"));
        selectId.options.add(new Option("15:30","15:30"));
        selectId.options.add(new Option("16:00","16:00"));
        selectId.options.add(new Option("16:30","16:30"));
        selectId.options.add(new Option("17:00","17:00"));
        selectId.options.add(new Option("17:30","17:30"));
        selectId.options.add(new Option("18:00","18:00"));
        selectId.options.add(new Option("18:30","18:30"));
        selectId.options.add(new Option("19:00","19:00"));
        selectId.options.add(new Option("19:30","19:30"));
        selectId.options.add(new Option("20:00","20:00"));
        selectId.options.add(new Option("20:30","20:30"));
        selectId.options.add(new Option("21:00","21:00"));
        selectId.options.add(new Option("21:30","21:30"));
        selectId.options.add(new Option("22:00","22:00"));
    }
    function selectTimePeriod(){
        alert(1);
    }
    //全局变量-专家id
    var id = ${id};
    //时间段构造函数
    function SchedulingTimeTable(startTime,endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }
    //保存时间段数据并实时回显
    function saveSchedulingTime(flag){
        /*var table = document.getElementById("SchedulingTimeTable");
        //创建行
        var tr = document.createElement('tr');
        table.appendChild(tr);
        var startTime = $("#Mstart").val();
        var endTime = $("#Mend").val();
        //获取输入的时间段
        var timeSlotAll = new SchedulingTimeTable(startTime,endTime);
        var ss = "";
        var td = document.createElement('td');
        //拼接周几和输入的时间段并放入tr
        for (var k in timeSlotAll){
            ss += timeSlotAll[k]+"|";
        }
        td.innerHTML = ss + "[<a href= 'javascript:;'>删除</a>]";
        tr.appendChild(td);
        //删除功能
        td.onclick = function (){
            table.removeChild(this.parentElement);
        }*/
        var table = document.getElementById("SchedulingTimeTable");
        $("#mondata").append("第一行");
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