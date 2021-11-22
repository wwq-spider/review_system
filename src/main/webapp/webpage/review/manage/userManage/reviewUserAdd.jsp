<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>添加用户</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="reviewUser.do?addorupdate">
	<input id="userId" name="userId" type="hidden" value="${user.userId }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="15%" nowrap><label class="Validform_label"> 用户名: </label></td>
			<td class="value" width="85%"><c:if test="${user.userId!=null }">
     ${user.userName }
     </c:if> <c:if test="${user.userId==null }">
				<input id="userName" class="inputxt" name="userName" validType="review_user,user_name,user_id" value="${user.userName }" datatype="s2-10" />
				<span class="Validform_checktip">用户名范围在2~10位字符</span>
			</c:if></td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					性别:
				</label>
			</td>
			<td class="value">
				<input  name="sex" type="radio"
					   <c:if test="${user.sex == '1'}">checked="checked"</c:if>
					   value="1">男</input>
				<input name="sex" type="radio"
					   <c:if test="${user.sex == '2'}">checked="checked"</c:if>
					   value="2">女</input>
				<span class="Validform_checktip" id="sexTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> 用户组: </label></td>
			<td class="value" width="10%">
			<select  id="groupId" name="groupId" multiple="true" style="height: 200px;">
				<c:forEach items="${groupList }" var="userGroup">
					<option <c:if test="${fn:contains(user.groupId, userGroup.id)}"> selected</c:if>  value="${userGroup.id }">${userGroup.departname }</option>
				</c:forEach>
			</select>
			<span class="Validform_checktip" id="groupIdTip"></span>
			</td>
		</tr>

		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> 真实姓名: </label></td>
			<td class="value" width="10%"><input id="realName" class="inputxt" name="realName" value="${user.realName }" datatype="s2-10"> <span class="Validform_checktip">填写个人真实姓名</span></td>
		</tr>
		<c:if test="${user.userId==null }">
			<tr>
				<td align="right"><label class="Validform_label"> 密码: </label></td>
				<td class="value"><input type="password" class="inputxt" value="${user.password}" name="password" plugin="passwordStrength" datatype="*6-18" errormsg="" /> <span class="passwordStrength"
					style="display: none;"><span>弱</span><span>中</span><span class="last">强</span> </span> <span class="Validform_checktip">密码至少6个字符,最多18个字符</span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 重复密码: </label></td>
				<td class="value"><input id="repassword" class="inputxt" type="password" value="${user.password}" recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！"> <span
					class="Validform_checktip">重复个人密码</span></td>
			</tr>
		</c:if>
		<tr>
			<td align="right" nowrap><label class="Validform_label"> 手机号码: </label></td>
			<td class="value"><input class="inputxt" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确!" ignore="ignore"> <span class="Validform_checktip"></span></td>
		</tr>
	</table>
</t:formvalid>
</body>