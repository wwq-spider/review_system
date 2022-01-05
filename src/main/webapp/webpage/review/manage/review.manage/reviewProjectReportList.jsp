<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="reviewProjectReportList" title="测评项目报告" actionUrl="reviewProjectReport.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="用户id" field="userId" ></t:dgCol>
   <t:dgCol title="项目组id" field="groupId" ></t:dgCol>
   <t:dgCol title="项目id" field="projectId" ></t:dgCol>
   <t:dgCol title="报告名称" field="reportName" ></t:dgCol>
   <t:dgCol title="报告文件路径" field="reportFilePath" ></t:dgCol>
   <t:dgCol title="报告模板路径" field="templatePath" ></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="操作人" field="operator" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="reviewProjectReport.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="reviewProjectReport.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="reviewProjectReport.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="reviewProjectReport.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>