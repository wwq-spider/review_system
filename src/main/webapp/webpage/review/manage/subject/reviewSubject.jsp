<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>测评主题</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="reviewSubject.do?save">
			<input id="id" name="id" type="hidden" value="${reviewSubject.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							专题名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="subjectName" name="subjectName" ignore="ignore"
							   value="${reviewSubject.subjectName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
						专题描述: </label></td>
					<td class="value">
						<textarea id="subjectDesc" name="subjectDesc" cols="60" rows="3" datatype="*">${reviewSubject.subjectDesc }</textarea>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
						测评量表: </label></td>
					<td class="value">
						<select  id="classIds" name="classIds" multiple style="width: 280px; height: 280px;" datatype="*">
							<c:forEach items="${classList }" var="reviewType">
								<c:set var="iscontain" value="false" />
								<c:forEach items="${reviewSubject.subjectClassList}" var="map">
									<c:if test="${map.classId eq reviewType.classId}">
										<c:set var="iscontain" value="true" />
									</c:if>
								</c:forEach>
								<option <c:if test="${iscontain}">selected="selected"</c:if> value="${reviewType.classId }">${reviewType.title }</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip"  id="classIdsTip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>