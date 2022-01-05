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
		createwindow('导出答题记录', 'reviewUser.do?toExportRecord', 500, 300)
	}

	function printReport() {
		printPreview('测评报告', 'reviewUser.do?toReportPreview', 'reviewUserList', '100%', '100%')
	}
</script>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="reviewUserList" title="用户管理" actionUrl="reviewUser.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group" checkbox="true">
	<t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true" width="10"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true" width="10"></t:dgCol>
	<t:dgCol title="性别" sortable="false" field="sex" query="false" width="5"></t:dgCol>
	<t:dgCol title="用户组" sortable="false" field="groupId" query="true" replace="${departsReplace}"  width="20"></t:dgCol>
	<t:dgCol title="手机号" sortable="false" field="mobilePhone" query="false"  width="10"></t:dgCol>
	<t:dgCol title="微信id" sortable="false" field="openid" query="false" width="15"></t:dgCol>
	<t:dgCol title="操作时间" field="updateTime" query="false" width="15"></t:dgCol>
	<t:dgCol title="操作" field="opt"  width="20"></t:dgCol>
	<t:dgFunOpt funname="viewRecord(id)" title="查看测评记录"></t:dgFunOpt>
	<t:dgDelOpt title="删除" url="reviewUser.do?del&userId={id}" />
	<t:dgToolBar title="用户录入" icon="icon-add" url="reviewUser.do?toAdd" funname="add" width="600" height="500"></t:dgToolBar>
	<t:dgToolBar title="用户编辑" icon="icon-edit" url="reviewUser.do?toAdd" funname="updateReviewUser"  width="600" height="500"></t:dgToolBar>
	<t:dgToolBar title="导入用户" icon="icon-search" onclick="userListImportXls()"></t:dgToolBar>
	<t:dgToolBar title="批量导入" icon="icon-search" onclick="batchImport()"></t:dgToolBar>
	<t:dgToolBar title="导出测评结果" icon="icon-add" onclick="exportRecord()"></t:dgToolBar>
	<t:dgToolBar title="打印测评报告" icon="icon-add" onclick="printReport()"></t:dgToolBar>
</t:datagrid>
