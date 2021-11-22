<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="reviewOrderList" title="公告" actionUrl="reviewOrder.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="用户id" field="userId" ></t:dgCol>
   <t:dgCol title="测评量表id" field="classId" ></t:dgCol>
   <t:dgCol title="量表名称" field="className" ></t:dgCol>
   <t:dgCol title="订单实付金额" field="orderAmount" ></t:dgCol>
   <t:dgCol title="原始价格" field="orgAmount" ></t:dgCol>
   <t:dgCol title="订单号" field="orderNo" ></t:dgCol>
   <t:dgCol title="订单状态" field="status" ></t:dgCol>
   <t:dgCol title="外部支付id" field="payId" ></t:dgCol>
   <t:dgCol title="支付方式" field="payType" ></t:dgCol>
   <t:dgCol title="项目id" field="projectId" ></t:dgCol>
   <t:dgCol title="测评专题id" field="subjectId" ></t:dgCol>
   <t:dgCol title="用户组id" field="groupId" ></t:dgCol>
   <t:dgCol title="支付时间" field="payTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="操作时间" field="operateTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="操作人" field="operator" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="reviewOrder.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="reviewOrder.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="reviewOrder.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="reviewOrder.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>