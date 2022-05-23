<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="reviewReportTemplateList" title="测评量表报告模板" actionUrl="reviewReportTemplate.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="量表id" field="classId" ></t:dgCol>
   <t:dgCol title="标题" field="title" ></t:dgCol>
   <t:dgCol title="描述" field="explanation" ></t:dgCol>
   <t:dgCol title="排序号码" field="orderNum" ></t:dgCol>
   <t:dgCol title="操作时间" field="operateTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="操作人" field="operator" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="reviewReportTemplate.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="reviewReportTemplate.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="reviewReportTemplate.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="reviewReportTemplate.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>