<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>导出答题记录和分数</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#startTime").attr("class","Wdate").attr("style","height:20px;width:150px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
			$("input").css("height", "24px");
		});

		function exportData() {
			let groupId = $("#groupId").val();
			if (!groupId || groupId == "") {
				$.messager.alert("提示","请您用户组","info");
				return
			}
			window.location.href = "${webRoot}/reviewUser.do?exportQuestionAnswer&groupId=" + $("#groupId").val() + "&startTime=" + $("#startTime").val()
		}
	</script>
</head>
<body style="overflow-y: hidden" scroll="no">
	<input type="hidden" id="btn_sub" class="btn_sub" onclick="exportData();"/>
	<div class="form" style="height: 30px; margin-left: 10px;">
		<label class="Validform_label"> 用户组: </label>
		<select  id="groupId" name="groupId" style="height: 30px">
			<option value="" selected="selected">--请选择--</option>
			<c:forEach items="${groupList }" var="userGroup">
				<option  value="${userGroup.id }">${userGroup.departname }</option>
			</c:forEach>
		</select>
	</div>
	<div class="form" style="margin-top: 10px">
		<label class="Validform_label"> 开始时间: </label>
		<input type="text" name="startTime" id="startTime" style="width: 100px; height: 24px;"></span>
	</div>
</body>
</html>
