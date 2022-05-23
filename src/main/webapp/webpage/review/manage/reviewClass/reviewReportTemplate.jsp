<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>测评量表报告模板</title>
	<link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css" />
	<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css" />
	<link href="plug-in/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="plug-in/tools/css/common.css" type="text/css" />
	<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="plug-in/tools/dataformat.js"></script>
	<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
	<script type="text/javascript" src="plug-in/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="plug-in/tools/syUtil.js"></script>
	<script type="text/javascript" src="plug-in/easyui/extends/datagrid-scrollview.js"></script>
	<script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
	<script type="text/javascript" src="plug-in/tools/curdtools.js"></script>
	<script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script>
	<script type="text/javascript" src="plug-in/jquery-plugs/hftable/jquery-hftable.js"></script>
	<style>
		#addBtn {
			background-color: #008CBA;
			border: none;
			color: white;
			padding: 5px 10px;
			text-align: center;
			text-decoration: none;
			display: inline-block;
			font-size: 10px;
			margin: 4px 2px;
			cursor: pointer;
			border-radius: 2px;
			border: none;
		}
		#delBtn {
			background-color: #787a7e;
			border: none;
			color: white;
			padding: 5px 10px;
			text-align: center;
			text-decoration: none;
			display: inline-block;
			font-size: 10px;
			margin: 4px 2px;
			cursor: pointer;
			border-radius: 2px;
			border: none;
		}
		.inputTxt {
			border: 1px solid #a5aeb6;
			width: 400px;
			padding: 3px 2px;
			margin: 5px 5px;
		}
	</style>
	<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
	<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
	<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
	<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype.js"></script>
	<script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>
	<script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script>
	<script type="text/javascript">
		function addRow() {
			let tr = $("#titleTable tr");
			let trLength = (tr.length - 1) / 2;
			let thtml = "<tr><td align=\"center\" style='width: 10%'>"+
					"<label class=\"Validform_label\"> 标题" + (trLength+1) + ": </label> </td>"+
					"<td align=\"center\"> <input type=\"text\" placeholder='请输入标题' maxlength='50' name=\"reportTemplateList["+trLength+"].title\" id=\"reportTemplateList["+trLength+"].title\" value=\"\" dataType=\"*\" type=\"text\" class=\"inputTxt\"></input>"
					+"<span class=\"Validform_checktip\"></span><label class=\"Validform_label\" style=\"display: none;\">标题" + (trLength+1) + "</label></td></tr>" +
					"<tr><td align=\"center\"  style='width: 10%'>"+
					"<label class=\"Validform_label\"> 说明" + (trLength+1) + ": </label> </td>"+
					"<td align=\"center\"> <textarea  placeholder='请输入说明'  cols=\"60\" rows=\"3\" maxlength='500' name=\"reportTemplateList["+trLength+"].explanation\" id=\"reportTemplateList["+trLength+"].explanation\" dataType=\"*\"  value=\"\" type=\"text\" class=\"inputTxt\"></textarea>"
					+"<span class=\"Validform_checktip\"></span><label class=\"Validform_label\" style=\"display: none;\">说明" + (trLength+1) + "</label></td></tr>"
			$("#titleTable").append(thtml);
		}

		function del() {
			let tr = $("#titleTable tr");
			if (tr.length <= 3) {
				$.messager.alert("提示","不能全部删除")
				return
			}
			tr[tr.length - 1].remove();
			tr[tr.length - 2].remove();
		}
		let tSize = "${reportTemplateList.size()}"
		$(function() {
			if (!tSize || tSize <= 0) {
				addRow()
			}
			$("#formobj").Validform({
				tiptype: 4,
				btnSubmit: "#btn_sub",
				btnReset: "#btn_reset",
				ajaxPost: true,
				usePlugin: {
					passwordstrength: {
						minLen: 6,
						maxLen: 18,
						trigger: function(obj, error) {
							if (error) {
								obj.parent().next().find(".Validform_checktip").show();
								obj.find(".passwordStrength").hide();
							} else {
								$(".passwordStrength").show();
								obj.parent().next().find(".Validform_checktip").hide();
							}
						}
					}
				},
				callback: function(data) {
					var win = frameElement.api.opener;
					if (data.success == true) {
						frameElement.api.close();
						win.tip(data.msg);
					} else {
						if (data.responseText == '' || data.responseText == undefined) {
							$.messager.alert('错误', data.msg);
							$.Hidemsg();
						} else {
							try {
								var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
								$.messager.alert('错误', emsg);
								$.Hidemsg();
							} catch(ex) {
								$.messager.alert('错误', data.responseText + "");
								$.Hidemsg();
							}
						}
						return false;
					}
					win.reloadTable();
				}
			});
		});
	</script>
</head>
<body style="overflow-y: auto;overflow-x:hidden" scroll="no">
<form id="formobj" action="reviewReportTemplate.do?save" name="formobj" method="post" enctype="multipart/form-data">
	<input type="hidden" id="btn_sub" class="btn_sub" />
	<input id="classId" name="classId" type="hidden" value="${classId}" />
	<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
		<tbody id="titleTable">
		<tr>
			<td align="center"  style='width: 10%'><label class="Validform_label"> 提示语: </label> </td>
			<td align="center">
				<textarea id="reportTips"  placeholder='请输入提示语' maxlength='500' name="reportTips" value="" dataType="*" cols="60" rows="3" class="inputTxt">${reportTips}</textarea>
				<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">提示语</label>
			</td>
		</tr>
		<c:forEach items="${reportTemplateList }" var="reportTemplate" varStatus="i">
			<input type="hidden" name="reportTemplateList[${i.index }].id" id="reportTemplateList[${i.index }].id" value=""></input>
			<tr>
				<td align="center"  style='width: 10%'><label class="Validform_label"> 标题${i.index + 1 }: </label> </td>
				<td align="center">
					<input id="reportTemplateList[${i.index }].title"  placeholder='请输入标题'  name="reportTemplateList[${i.index }].title" value="${reportTemplate.title}" dataType="*" maxlength="50" type="text" class="inputTxt">
					<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">标题${i.index + 1 }</label>
				</td>
			</tr>
			<tr>
				<td align="center"  style='width: 10%'> <label class="Validform_label"> 说明${i.index + 1 }: </label> </td>
				<td align="center">
					<textarea id="reportTemplateList[${i.index }].explanation"  placeholder='请输入说明' maxlength='500'  name="reportTemplateList[${i.index }].explanation" value="" dataType="*" cols="60" rows="3" class="inputTxt">${reportTemplate.explanation}</textarea>
					<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">说明${i.index + 1 }</label>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table>
		<tr align="center">
			<td align="center">
				<input type="button" id="addBtn"  name="addBtn" onclick="addRow();" value="添加"></input>
				<input type="button" id="delBtn" name="delBtn"  onclick="del();" value="删除"></input>
			</td>
		</tr>
	</table>
</form>
</body>
</html>