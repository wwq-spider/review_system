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
        td {
            width: 280px;
            height: 20px;
            border-color: #46a0e1;
            border-width: 1px;
        }
        textarea {
            width: 280px;
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
<div id="query">
    <label style="font-size: 16px;margin-left: 10px" class="Validform_label">验证</label>
    <table style="width: 100%;" align="left" cellpadding="0" cellspacing="1" class="formtable">
        <tr aria-colspan="4">
            <td align="right">
                <label class="Validform_label">公司名称:&nbsp&nbsp&nbsp</label>
            </td>
            <td class="value">
                <select id="companyQueryId" name="companyQueryId">
                    <option value="" selected="selected">所有</option>
                    <c:forEach items="${companyList }" var="company">
                        <option>${company.departname }</option>
                    </c:forEach>
                </select>
            </td>
            <td align="right">
                <label class="Validform_label">组织机构:&nbsp&nbsp&nbsp</label>
            </td>
            <td class="value">
                <select id="branchQueryId" name="branchQueryId">
                    <option value="" selected="selected">所有</option>
                    <c:forEach items="${branchEntityList }" var="branch">
                        <option>${branch.typename }</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr aria-colspan="5">
            <td align="right">
                <label class="Validform_label">员工手机:&nbsp&nbsp&nbsp</label>
            </td>
            <td class="value">
                <input id="employeePhoneQuery"/>
            </td>
            <td align="right">
                <label class="Validform_label">员工姓名:&nbsp&nbsp&nbsp</label>
            </td>
            <td class="value">
                <input id="employeeNameQuery"/>
                <button style="width: 60px" id="clientInfoVerificat">验证</button>
            </td>
        </tr>
    </table>
</div>
<div id="result">
    <label style="font-size: 16px;margin-left: 10px" class="Validform_label">初步信息记录</label>
    <form id="addIntakeForm" method="post" action="intakeController.do?saveIntakeInfo" enctype="multipart/form-data">
        <input type="hidden" id="btn_sub" class="btn_sub"/>
        <table style="width: 100%;"  align="left" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td align="right">
                    <label class="Validform_label">来电记录编号:</label>
                </td>
                <td class="value">
                    <input type="text" id="callRecordNumber" name="callRecordNumber"/>
                </td>
                <td align="right">
                    <label class="Validform_label">挂断时间:</label>
                </td>
                <td class="value">
                    <input class="Validform_label" id="hangUpTime" name="hangUpTime"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">呈现问题描述:</label>
                </td>
                <td class="value">
                    <textarea id="problemDescription" name="problemDescription"></textarea>
                </td>
                <td align="right">
                    <label class="Validform_label">备注:</label>
                </td>
                <td class="value">
                    <textarea id="note" name="note"></textarea>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    来电类型:</label></td>
                <td class="value">
                    <select id="callType" name="callType">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${callTypeList }" var="callType">
                            <option>${callType.typename }</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    是否重点个案:</label></td>
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
                    公司名称:</label></td>
                <td class="value">
                    <select id="companyId" name="companyId" datatype="*">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${companyList }" var="company">
                            <option <c:if test="${data.companyName==company.departname }">selected="selected"</c:if> value="${company.departname}">
                                    ${company.departname }
                            </option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    对自己或他人有危险:</label></td>
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
                    分支机构:</label></td>
                <td class="value">
                    <select id="branch" name="branch" datatype="*">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${branchEntityList }" var="branch">
                            <option>${branch.typename }</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    个案紧急程度:</label></td>
                <td class="value">
                    <select id="caseUrgency" name="caseUrgency">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1">高</option>
                        <option value="2">中</option>
                        <option value="3">低</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">是否使用过EAP:</label>
                </td>
                <td class="value">
                    <select id="usedEAP" name="usedEAP">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    对EAP认识:</label></td>
                <td class="value">
                    <select id="eapCognitionId" name="eapCognitionId">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${eapCognitionEntityList }" var="eapCognition">
                            <option>${eapCognition.typename }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">员工姓名:</label>
                </td>
                <td class="value"><input id="employeeName" name="employeeName" value="${data.employeeName }"/></td>
                <td align="right"><label class="Validform_label">
                    婚姻状态:</label></td>
                <td class="value">
                    <select id="maritalStatus" name="maritalStatus">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1">已婚</option>
                        <option value="0">未婚</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    员工生日:</label></td>
                <td class="value">
                    <input id="employeeBirthday" name="employeeBirthday"/>
                </td>
                <td align="right">
                    <label class="Validform_label">员工工号:</label>
                </td>
                <td class="value"><input id="employeeJobNumber" name="employeeJobNumber"/></td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    工作电话:</label></td>
                <td class="value">
                    <input id="JobPhone" name="JobPhone"/>
                </td>
                <td align="right"><label class="Validform_label">
                    员工职位:</label></td>
                <td class="value"><input id="employeePosition" name="employeePosition"/></td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    咨客性别:</label></td>
                <td class="value">
                    <select id="clientGender" name="clientGender">
                        <option value="" selected="selected">--请选择--</option>
                        <option value="1" <c:if test="${data.sex=='男' }"> selected="selected"</c:if> >男</option>
                        <option value="2" <c:if test="${data.sex=='女' }"> selected="selected"</c:if> >女</option>
                    </select>
                </td>

                <td align="right"><label class="Validform_label">
                    家庭电话:</label></td>
                <td class="value">
                    <input id="familyPhone" name="familyPhone"/>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    工作种类:</label></td>
                <td class="value">
                    <select id="workType" name="workType">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${workTypeEntityList }" var="workType">
                            <option>${workType.typename }</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    电子邮件:</label></td>
                <td class="value"><input id="email" name="email"/></td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    咨客地区:</label></td>
                <td class="value">
                    <input id="clientArea" name="clientArea" value="${data.clientArea }"/>
                </td>
                <td align="right"><label class="Validform_label">
                    岗位:</label></td>
                <td class="value"><input id="station" name="station"/></td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label">
                    转介来源:</label></td>
                <td class="value">
                    <select id="referralSource" name="referralSource">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${referralSourceEntityList }" var="referralSource">
                            <option>${referralSource.typename }</option>
                        </c:forEach>
                    </select>
                </td>
                <td align="right"><label class="Validform_label">
                    希望预约时间:</label></td>
                <td class="value"><input id="expectAppointmentTime" name="expectAppointmentTime"/></td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label" colspan="2">
                    呈现问题类型:</label></td>
                <td class="value" colspan="4">
                    <select id="problemType" name="problemType">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${problemTypeEntityList }" var="problemType">
                            <option>${problemType.typename }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right" colspan="1">
                    <label class="Validform_label">对咨询师的期待:</label>
                </td>
                <td class="value" colspan="3">
                    <textarea id="expectationsForConsultants" name="expectationsForConsultants"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
<script type="text/javascript">
    //初始化时间插件
    $(function () {
        $("#callStartTime").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
        $("#callEndTime").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
        $("#expectAppointmentTime").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
        $("#hangUpTime").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
    })

    $("#clientInfoVerificat").click(function (){
        var employeePhoneQuery = $("#employeePhoneQuery").val();
        var employeeNameQuery = $("#employeeNameQuery").val();
        if (employeePhoneQuery == "" && employeeNameQuery == ""){
            alertTip("请输入员工手机或姓名进行验证！","提示");
            return false;
        }
        frameElement.api.close();
        var url = 'intakeController.do?clientInfoVerificat&employeePhone=' + employeePhoneQuery + "&employeeName=" + employeeNameQuery;
        add('验证',url,'intakeList', 550, 400);
    });
    //提交form表单
    $("#btn_sub").click(function(event) {
        $("#addIntakeForm").form(
            'submit',
            {
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
            }
        );
    })
</script>
</html>
