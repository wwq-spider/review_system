<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>banner轮播图</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<form id="addForm" action="reviewBanner.do?save" name="formobj" method="post" enctype="multipart/form-data">
	<input type="hidden" id="btn_sub" class="btn_sub" />
	<input id="id" name="id" type="hidden" value="${reviewBanner.id}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					标题:
				</label>
			</td>
			<td class="value">
				<input class="inputxt" id="title" name="title" ignore="ignore" value="${reviewBanner.title}">
				<span class="Validform_checktip" id="titleTip">
				</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					图片:
				</label>
			</td>
			<td class="value">
				<input type="file" name="contentImg" id="contentImg" onchange="previewImage(this,'content')"
					   style="display: none" />
				<div id="previewcontent">
					<c:if test="${reviewBanner.imgUrl != null && reviewBanner.imgUrl != '' }">
						<img alt="图片预览" id="preview" width="100px" height="100px" src="${aliyunOssHost}${reviewBanner.imgUrl}"/>
					</c:if>
				</div>
				<input type="button" name="uploadBtn" onclick="uploadImg('contentImg');" value="上传头像" />
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					目标跳转路径:
				</label>
			</td>
			<td class="value">
				<input class="inputxt" id="targetUrl" name="targetUrl" ignore="ignore"
					   value="${reviewBanner.targetUrl}">
				<span class="Validform_checktip"  id="targetUrlTip">
				</span>
			</td>
		</tr>
	</table>
	<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
	<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
	<script type="text/javascript">
		//提交form表单
		$("#btn_sub").click(function(event) {
			$("#addForm").form(
					'submit',
					{
						onSubmit : function() {
							let title = $("#title").val();
							if($.trim(title) == "") {
								$("#titleTip").text("请填写标题");
								$("#titleTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#titleTip").text("通过信息验证");
								$("#titleTip").attr("class","Validform_checktip Validform_right");
							}
							let targetUrl = $("#targetUrl").val();
							if($.trim(targetUrl) == "") {
								$("#targetUrlTip").text("请填写目标跳转路径");
								$("#targetUrlTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#targetUrlTip").text("通过信息验证");
								$("#targetUrlTip").attr("class","Validform_checktip Validform_right");
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
		});
	</script>
</form>
</body>