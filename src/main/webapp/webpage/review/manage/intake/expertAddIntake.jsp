<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <meta charset='utf-8'/>
    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
    <style type="text/css">
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
        #query {
            background-color: #87CEEB;
            margin-bottom: 20px;
            margin-top: 5px;
        }
        #result {
            background-color: #87CEEB;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<div id="result">
    <label style="font-size: 16px;margin-left: 10px" class="Validform_label">咨询师反馈内容</label>
    <form id="addIntakeForm" method="post" action="intakeController.do?saveIntakeInfo" enctype="multipart/form-data">
        <table style="width: 100%;"  align="left" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td align="right">
                    <label class="Validform_label">服务日期:&nbsp&nbsp&nbsp&nbsp</label>
                </td>
                <td class="value">
                    <input type="text" id="serviceDate" name="serviceDate"/>
                </td>
                <td align="right">
                    <label class="Validform_label">开始时间:&nbsp&nbsp&nbsp&nbsp</label>
                </td>
                <td class="value">
                    <input id="startTime" name="startTime"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">持续分钟:&nbsp&nbsp&nbsp&nbsp</label>
                </td>
                <td class="value">
                    <input id="durationMinutes" name="durationMinutes"></input>
                </td>
                <td align="right">
                    <label class="Validform_label">联系类型:&nbsp&nbsp&nbsp&nbsp</label>
                </td>
                <td class="value">
                    <select id="contactType" name="callTypeId">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${callTypeList }" var="callType">
                            <option>${callType.typename }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    评估主要问题:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td class="value">
                    <select id="callTypeId" name="callTypeId">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${callTypeList }" var="callType">
                            <option>${callType.typename }</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    评估次要问题:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td class="value">
                    <select id="keyCases" name="keyCases">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    服务类型:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td class="value">
                    <select id="companyId" name="companyId">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${companyList }" var="company">
                            <option value="${company.typecode }">${company.typename }</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    个案状态:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td class="value">
                    <select id="dangerousSituation" name="dangerousSituation">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    咨客主诉:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td>
                    <textarea style="width: 660px" id="clientComplaint" name="clientComplaint"></textarea>
                </td>
                <td align="right"><label class="Validform_label">
                    咨客目标:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td>
                    <textarea style="width: 660px" id="clientGoals" name="clientGoals"></textarea>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    解决过程:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td>
                    <textarea style="width: 660px" id="resolutionProcess" name="resolutionProcess"></textarea>
                </td>
                <td align="right"><label class="Validform_label">
                    咨客行动计划:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td>
                    <textarea style="width: 660px" id="clientActionPlan" name="clientActionPlan"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="1" align="right"><label class="Validform_label">
                    咨客反馈:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td colspan="3">
                    <textarea style="width: 660px" id="clientFeedback" name="clientFeedback"></textarea>
                </td>
            </tr>

            <tr>
                <td align="right"><label class="Validform_label">
                    危机评估:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td class="value">
                    <select id="crisisAssess" name="crisisAssess">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${companyList }" var="company">
                            <option value="${company.typecode }">${company.typename }</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    危机程度:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td class="value">
                    <select id="crisisLevel" name="crisisLevel">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td align="right">
                    <label class="Validform_label">紧急联系人:&nbsp&nbsp&nbsp&nbsp</label>
                </td>
                <td class="value">
                    <input id="emergencyContact" name="emergencyContact"></input>
                </td>
                <td align="right">
                    <label class="Validform_label">电话:&nbsp&nbsp&nbsp&nbsp</label>
                </td>
                <td class="value">
                    <input id="emergencyContactPhone" name="emergencyContactPhone"></input>
                </td>
            </tr>

            <tr>
                <td colspan="1" align="right"><label class="Validform_label">
                    与咨客关系:&nbsp&nbsp&nbsp&nbsp</label></td>
                <td colspan="3" class="value">
                    <select id="clientRelation" name="clientRelation">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${companyList }" var="company">
                            <option value="${company.typecode }">${company.typename }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>

        </table>
    </form>
</div>
<div style="text-align: center"><button id="submit">保存</button></div>
</body>
<script type="text/javascript">
    //初始化时间插件
    $(function () {
        $("#serviceDate").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
        $("#startTime").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
    })
    //保存intake信息
    $("#submit").click(function (){
        var callRecordNumber = $("#callRecordNumber").val();
        alert(callRecordNumber);
        $("#addIntakeForm").form('submit', {
            onSubmit: function () {
            },
            success: function (data) {
                //var jsonData = $.parseJSON(data);
                var jsonData = data;
                if (jsonData.success == true){
                    alert("保存成功");
                }
            }
        });
    });
</script>
</html>
