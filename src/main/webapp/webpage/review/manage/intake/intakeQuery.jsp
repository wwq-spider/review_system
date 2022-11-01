<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
    <meta charset='utf-8'/>
    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
    <style type="text/css">
        table {
            table-layout: fixed;
            word-break: break-all;
        }
        input {
            width: 153px;
            height: 20px;
            border-color: #46a0e1;
            border-width: 1px;
        }
        textarea {
            width: 300px;
            height: 50px;
            border-color: #46a0e1;
            border-width: 1px;
        }
    </style>
    <script type="text/javascript">
    </script>
</head>
<body style="overflow-y: hidden" scroll="no">
<div class="pageContent">
    <table style="width: 100%;"  align="center" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <th><label class="Validform_label">来电时间:</label></th>
            <td colspan="2">
                <input id="callStartTime"/> —— <input id="callEndTime"/>
            </td>
            <th><label class="Validform_label">分机:</label></th>
            <td colspan="2">
                <input id="extension"/>
                <button>获取来电</button>
            </td>
        </tr>
        <tr>
            <th><label class="Validform_label">公司名称:</label></th>
            <td>
                <select id="companyQueryId" name="companyQueryId">
                    <option value="" selected="selected">所有</option>
                    <c:forEach items="${companyList }" var="company">
                        <option>${company.companyName }</option>
                    </c:forEach>
                </select>
            </td>
            <th><label class="Validform_label">组织机构:</label></th>
            <td>
                <select id="branchQueryId" name="branchQueryId">
                    <option value="" selected="selected">所有</option>
                    <c:forEach items="${branchEntityList }" var="branch">
                        <option>${branch.branchName }</option>
                    </c:forEach>
                </select>
            </td>
            <th><label class="Validform_label">员工工号:</label></th>
            <td>
                <input id="employeeJobNumberQuery"/>
            </td>
            <th><label class="Validform_label">员工姓名:</label></th>
            <td colspan="2">
                <input id="employeeNameQuery"/>
                <button>验证</button>
            </td>
        </tr>
    </table>
</div>
<div id="result">
    <jsp:include page="addIntake.jsp"></jsp:include>
</div>
</body>
</html>
