<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户导入</title>
	<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript">

		function uploadExcel() {
			var classId = $("#classId").val();

			if($.trim(classId) == "") {
				$("#classIdTip").text("请选择分类");
				$("#classIdTip").attr("class","Validform_checktip Validform_wrong");
				return false;
			} else {
				$("#classIdTip").text("通过信息验证");
				$("#classIdTip").attr("class","Validform_checktip Validform_right");
				($('#file_upload').uploadify('upload','*'));
				return false;
			}

			var groupId = $("#groupId").val();

			if($.trim(groupId) == "") {
				$("#groupIdTip").text("请选择用户组");
				$("#groupIdTip").attr("class","Validform_checktip Validform_wrong");
				return false;
			} else {
				$("#groupIdTip").text("通过信息验证");
				$("#groupIdTip").attr("class","Validform_checktip Validform_right");
				($('#file_upload').uploadify('upload','*'));
				return false;
			}
		}

		function uploadSucc(data) {
			var win = frameElement.api.opener;
			win.location.href="<%=path%>/reviewUser.do?excelExport&fileTempName="+data.params;
		}
	</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="uploadExcel">
	<fieldset class="step">
		<div class="form">
			<label class="Validform_label"> 用户组: </label>
			<select  id="groupId" name="groupId">
				<option value="无" selected="selected">--请选择--</option>
				<c:forEach items="${groupList }" var="userGroup">
					<option  value="${userGroup.id }">${userGroup.departname }</option>
				</c:forEach>
			</select>
			<span class="Validform_checktip" id="groupIdTip"></span>
		</div>
		<div class="form">
			<label class="Validform_label"> 测评量表: </label>
			<select  id="classId" name="classId">
				<option value="" selected="selected">--请选择--</option>
				<c:forEach items="${classList }" var="reviewType">
					<option  value="${reviewType.classId }">${reviewType.title }</option>
				</c:forEach>
			</select>
			<span class="Validform_checktip" id="classIdTip"></span>
		</div>
		<div class="form">
			<t:upload name="files" buttonText="选择要导入的文件" uploader="reviewUser.do?upload" onUploadSuccess="uploadSucc"  extend="*.xls;*.xlsx" id="file_upload" formData="classId,groupId"></t:upload>
		</div>
		<div class="form" id="filediv" style="height: 50px"></div>
	</fieldset>
</t:formvalid>
</body>
</html>
