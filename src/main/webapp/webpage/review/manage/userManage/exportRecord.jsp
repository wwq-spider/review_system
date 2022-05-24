<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>导出答题记录和分数</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#startTime").attr("class","Wdate").attr("style","height:20px;width:300px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
			$("#endTime").attr("class","Wdate").attr("style","height:20px;width:300px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
			$("input").css("height", "24px");
		});

		function exportData() {
			let groupId = $("#groupId").val();
			let projectId = $("#projectId").val();
			if ((!groupId || groupId == "") && (!projectId || projectId == "")) {
				$.messager.alert("提示","项目和用户组不能都为空","info");
				return
			}
			// if (!projectId || projectId == "") {
			// 	$.messager.alert("提示","请您测评项目","info");
			// 	return
			// }

			let startTime = $("#startTime").val()
			if (!startTime || startTime == "" ) {
				$.messager.alert("提示","请选择开始时间","info");
				return
			}
			let endTime = $("#endTime").val()
			if (endTime && endTime != "" && endTime < startTime) {
				$.messager.alert("提示","结束时间不能小于开始时间","info");
				return
			}

			window.location.href = "${realPath}/reviewUser.do?exportReviewResult&groupId=" + groupId + "&startTime=" + startTime
				+ "&projectId=" + projectId + "&endTime=" + $("#endTime").val()
		}
	</script>
</head>
<body style="overflow-y: hidden" scroll="no">
	<input type="hidden" id="btn_sub" class="btn_sub" onclick="exportData();"/>
	<div class="form" style="height: 30px; margin-left: 10px;">
		<label class="Validform_label"> 用户组: </label>
		<select  id="groupId" name="groupId" style="width: 300px; height: 30px">
			<option value="" selected="selected">--请选择--</option>
			<option value="1">默认用户组</option>
			<c:forEach items="${groupList }" var="userGroup">
				<option  value="${userGroup.id }">${userGroup.departname }</option>
			</c:forEach>
		</select>
	</div>
	<div class="form" style="height: 30px; margin-top: 10px;">
		<label class="Validform_label"> 测评项目: </label>
		<select  id="projectId" name="projectId" style="width: 300px; height: 30px">
			<option value="" selected="selected">--请选择--</option>
			<c:forEach items="${projectList }" var="project">
				<option  value="${project.id }">${project.projectName }</option>
			</c:forEach>
		</select>
	</div>
	<div class="form" style="margin-top: 10px">
		<label class="Validform_label"> 开始时间: </label>
		<input type="text" name="startTime" id="startTime" style="width: 300px; height: 24px;"></span>
	</div>
	<div class="form" style="margin-top: 10px">
		<label class="Validform_label"> 结束时间: </label>
		<input type="text" name="startTime" id="endTime" style="width: 300px; height: 24px;"></span>
	</div>
</body>
</html>
