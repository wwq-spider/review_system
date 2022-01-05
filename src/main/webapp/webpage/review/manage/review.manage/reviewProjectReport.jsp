<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>测评项目报告</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="reviewProjectReport.do?save">
			<input id="id" name="id" type="hidden" value="${reviewProjectReportPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="userId" name="userId" ignore="ignore"
							   value="${reviewProjectReport.userId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目组id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="groupId" name="groupId" ignore="ignore"
							   value="${reviewProjectReport.groupId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="projectId" name="projectId" ignore="ignore"
							   value="${reviewProjectReport.projectId}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							报告名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="reportName" name="reportName" ignore="ignore"
							   value="${reviewProjectReport.reportName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							报告文件路径:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="reportFilePath" name="reportFilePath" ignore="ignore"
							   value="${reviewProjectReport.reportFilePath}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							报告模板路径:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="templatePath" name="templatePath" ignore="ignore"
							   value="${reviewProjectReport.templatePath}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							创建时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="createTime" name="createTime" ignore="ignore"
							     value="<fmt:formatDate value='${reviewProjectReport.createTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							操作人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="operator" name="operator" ignore="ignore"
							   value="${reviewProjectReport.operator}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>