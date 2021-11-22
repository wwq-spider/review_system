<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>公告</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="reviewNotice.do?save">
			<input id="id" name="id" type="hidden" value="${reviewNotice.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							标题:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="noticeName" name="noticeName" ignore="ignore" style="width: 300px" maxlength="100"
							   value="${reviewNotice.noticeName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
						内容: </label></td>
					<td class="value">
						<textarea id="noticeDesc" name="noticeDesc" cols="80" rows="20" datatype="*" maxlength="900">${reviewNotice.noticeDesc }</textarea>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>