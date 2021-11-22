<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="reviewSubjectList" title="测评主题" actionUrl="reviewSubject.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
   <t:dgCol title="专题名称" field="subjectName" width="50"></t:dgCol>
   <t:dgCol title="专题描述" field="subjectDesc" width="80"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" width="50"></t:dgCol>
   <t:dgCol title="更新时间" field="updateTime" formatter="yyyy-MM-dd hh:mm:ss" width="50"></t:dgCol>
   <t:dgCol title="操作者" field="operator" width="30"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
   <t:dgDelOpt title="删除" url="reviewSubject.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="reviewSubject.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="reviewSubject.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="reviewSubject.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>