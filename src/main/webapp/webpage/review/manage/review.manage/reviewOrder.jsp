<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>公告</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="reviewOrder.do?save">
			<input id="id" name="id" type="hidden" value="${reviewOrderPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="userId" name="userId" ignore="ignore"
							   value="${reviewOrder.userId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							测评量表id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="classId" name="classId" ignore="ignore"
							   value="${reviewOrder.classId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							量表名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="className" name="className" ignore="ignore"
							   value="${reviewOrder.className}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单实付金额:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderAmount" name="orderAmount" ignore="ignore"
							   value="${reviewOrder.orderAmount}" datatype="d">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							原始价格:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orgAmount" name="orgAmount" ignore="ignore"
							   value="${reviewOrder.orgAmount}" datatype="d">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderNo" name="orderNo" ignore="ignore"
							   value="${reviewOrder.orderNo}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单状态:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="status" name="status" ignore="ignore"
							   value="${reviewOrder.status}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							外部支付id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="payId" name="payId" ignore="ignore"
							   value="${reviewOrder.payId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付方式:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="payType" name="payType" ignore="ignore"
							   value="${reviewOrder.payType}" datatype="n">
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
							   value="${reviewOrder.projectId}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							测评专题id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="subjectId" name="subjectId" ignore="ignore"
							   value="${reviewOrder.subjectId}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户组id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="groupId" name="groupId" ignore="ignore"
							   value="${reviewOrder.groupId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="payTime" name="payTime" ignore="ignore"
							     value="<fmt:formatDate value='${reviewOrder.payTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
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
							     value="<fmt:formatDate value='${reviewOrder.createTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							操作时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="operateTime" name="operateTime" ignore="ignore"
							     value="<fmt:formatDate value='${reviewOrder.operateTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
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
							   value="${reviewOrder.operator}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>