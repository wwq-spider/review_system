<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	function userListImportXls() {
		openuploadwin('用户导入', 'reviewUser.do?toUserImport', "reviewUserList");
	}
	
	function batchImport() {
		openuploadwin1('批量导入', 'reviewUser.do?toBatchImport', "reviewUserList");
	}
	
	function viewRecord(userId) {
		createdetailwindow('查看测评记录','reviewUser.do?toReviewRecord&userId='+userId,'reviewUserList','100%','100%');
	}

	function exportRecord() {
		createwindow('导出答题记录', 'reviewUser.do?toExportRecord', 300, 300)
	}
</script>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="reviewUserList" title="用户管理" actionUrl="reviewUser.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
	<t:dgCol title="用户组" sortable="false" field="groupId" query="true" replace="${departsReplace}"></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgFunOpt funname="viewRecord(id)" title="查看测评记录"></t:dgFunOpt>
	<t:dgDelOpt title="删除" url="reviewUser.do?del&userId={id}" />
	<t:dgToolBar title="用户录入" icon="icon-add" url="reviewUser.do?toAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="用户编辑" icon="icon-edit" url="reviewUser.do?toAdd" funname="updateReviewUser"></t:dgToolBar>
	<t:dgToolBar title="导入用户" icon="icon-search" onclick="userListImportXls()"></t:dgToolBar>
	<t:dgToolBar title="批量导入" icon="icon-search" onclick="batchImport()"></t:dgToolBar>
	<t:dgToolBar title="导出答题记录" icon="icon-add" onclick="exportRecord()"></t:dgToolBar>
</t:datagrid>
