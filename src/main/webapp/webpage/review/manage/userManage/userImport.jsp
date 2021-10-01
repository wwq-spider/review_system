<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户导入</title>
	<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
	<fieldset class="step">
		<div class="form">
			<label class="Validform_label"> 用户组: </label>
			<select  id="groupId" name="groupId">
				<option value="" selected="selected">--请选择--</option>
				<c:forEach items="${groupList }" var="userGroup">
					<option  value="${userGroup.id }">${userGroup.departname }</option>
				</c:forEach>
			</select>
			<span class="Validform_checktip" id="groupIdTip"></span>
		</div>
		<div class="form">
			<t:upload name="fiels" buttonText="选择要导入的文件" uploader="reviewUser.do?importUser" extend="*.xls;*.xlsx" id="file_upload" formData="groupId"></t:upload>
		</div>
		<div class="form" id="filediv" style="height: 50px"></div>
	</fieldset>
</t:formvalid>
</body>
</html>
