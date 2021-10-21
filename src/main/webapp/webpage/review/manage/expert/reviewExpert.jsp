<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>测评专家模块</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: auto;overflow-x:hidden" scroll="no">
<form id="addForm" method="post"  action="reviewExpertController.do?save" enctype="multipart/form-data">
	<input type="hidden" id="btn_sub" class="btn_sub"/>
	<input id="id" name="id" type="hidden" value="${reviewExpertPage.id }">
	<table style="width: 100%;"  align="center" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					专家姓名:
				</label>
			</td>
			<td class="value">
				<input class="inputxt" id="expertName" name="expertName" ignore="ignore"
					   value="${reviewExpertPage.expertName}">
				<span class="Validform_checktip" id="expertNameTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					性别:
				</label>
			</td>
			<td class="value">
				<input id="sex" name="sex" type="radio"
					   <c:if test="${reviewExpertPage.sex == 1}">checked="checked"</c:if>
					   value="1">男</input>
				<input id="sex" name="sex" type="radio"
					   <c:if test="${reviewExpertPage.sex == 2}">checked="checked"</c:if>
					   value="2">女</input>
				<span class="Validform_checktip" id="sexTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					年龄:
				</label>
			</td>
			<td class="value">
				<input class="inputxt" id="age" name="age" ignore="ignore"
					   value="${reviewExpertPage.age}" datatype="n">
				<span class="Validform_checktip"  id="ageTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					头像:
				</label>
			</td>
			<td class="value">
				<input type="file" name="contentImg" id="contentImg" onchange="previewImage(this,'content')" style="display: none"/>
				<div id="previewcontent">
					<c:if test="${reviewExpertPage.avatar != null && reviewExpertPage.avatar != '' }">
						<img alt="图片预览" id="preview" width="100px" height="100px" src="${aliyunOssHost}${reviewExpertPage.avatar}"/>
					</c:if>
				</div>
				<input type="button"  name="uploadBtn" onclick="uploadImg('contentImg');" value="上传头像"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					专家手机号:
				</label>
			</td>
			<td class="value">
				<input class="inputxt" id="mobilePhone" name="mobilePhone" ignore="ignore"
					   value="${reviewExpertPage.mobilePhone}" placeholder="请输入11位手机号">
				<span class="Validform_checktip" id="mobilePhoneTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					工作机构名称:
				</label>
			</td>
			<td class="value">
				<input class="inputxt" id="workOrgName" name="workOrgName" ignore="ignore"
					   value="${reviewExpertPage.workOrgName}">
				<span class="Validform_checktip" id="workOrgNameTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					职称:
				</label>
			</td>
			<td class="value">
				<input class="inputxt" id="jobTitle" name="jobTitle" ignore="ignore"
					   value="${reviewExpertPage.jobTitle}">
				<span class="Validform_checktip" id="jobTitleTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					专家介绍:
				</label>
			</td>
			<td class="value">
				<textarea id="introduction" name="introduction" ignore="ignore"
						  rows="3" cols="60" placeholder="专家介绍最长不超过2000个字符">${reviewExpertPage.introduction}</textarea>
				<span class="Validform_checktip" id="introductionTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					专家标签:
				</label>
			</td>
			<td class="value">
				<textarea id="label" name="label" ignore="ignore" rows="3" cols="60" placeholder="多个标签以逗号隔开">${reviewExpertPage.label}</textarea>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
	</table>
</form>
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
<script type="text/javascript">
	//提交form表单
	$("#btn_sub").click(function(event) {
		$("#addForm").form(
				'submit',
				{
					onSubmit : function() {
						let expertName = $("#expertName").val();
						if($.trim(expertName) == "") {
							$("#expertNameTip").text("请填写专家名称");
							$("#expertNameTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else {
							$("#expertNameTip").text("通过信息验证");
							$("#expertNameTip").attr("class","Validform_checktip Validform_right");
						}
						let sex = $("#sex:checked").val();
						if($.trim(sex) == "") {
							$("#sexTip").text("请选择专家性别");
							$("#sexTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else {
							$("#sexTip").text("通过信息验证");
							$("#sexTip").attr("class","Validform_checktip Validform_right");
						}

						let age = $("#age").val();
						if($.trim(age) == "") {
							$("#ageTip").text("请输入专家年龄");
							$("#ageTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else {
							$("#ageTip").text("通过信息验证");
							$("#ageTip").attr("class","Validform_checktip Validform_right");
						}

						let mobilePhone = $("#mobilePhone").val();
						if($.trim(mobilePhone) == "") {
							$("#mobilePhoneTip").text("请输入专家手机号");
							$("#mobilePhoneTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else if($.trim(mobilePhone).length != 11) {
							$("#mobilePhoneTip").text("专家手机号格式错误");
							$("#mobilePhoneTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else {
							$("#mobilePhoneTip").text("通过信息验证");
							$("#mobilePhoneTip").attr("class","Validform_checktip Validform_right");
						}

						let workOrgName = $("#workOrgName").val();
						if($.trim(workOrgName) == "") {
							$("#workOrgNameTip").text("请输入专家工作机构");
							$("#workOrgNameTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else {
							$("#workOrgNameTip").text("通过信息验证");
							$("#workOrgNameTip").attr("class","Validform_checktip Validform_right");
						}

						let jobTitle = $("#jobTitle").val();
						if($.trim(jobTitle) == "") {
							$("#jobTitleTip").text("请输入专家职称");
							$("#jobTitleTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else {
							$("#jobTitleTip").text("通过信息验证");
							$("#jobTitleTip").attr("class", "Validform_checktip Validform_right");
						}

						let introduction = $("#introduction").val();
						if($.trim(introduction) == "") {
							$("#introductionTip").text("请输入专家介绍");
							$("#introductionTip").attr("class","Validform_checktip Validform_wrong");
							return false;
						} else {
							$("#introductionTip").text("通过信息验证");
							$("#introductionTip").attr("class", "Validform_checktip Validform_right");
						}

						return true;
					},
					success : function(data) {
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
	})
</script>
</body>