<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>专家排班</title>
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
                    <label class="Validform_label">周期</label>
                </th>
                <th colspan="1" style="width: 20px">
                    <label class="Validform_label">周几</label>
                </th>
                <th colspan="1" style="width: 35px">
                    <label class="Validform_label">时间段</label>
                </th>
            </tr>
        </thead>
    </table>

</form>
<style type="text/css">
    table {
        table-layout: fixed;
        word-break: break-all;
    }
</style>
<script type="text/javascript">

</script>
</body>