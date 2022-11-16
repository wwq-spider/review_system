<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加维度</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
 	<form id="addForm" action= "report.do?addorupdate" method="post" enctype="multipart/form-data">
 		<input type="hidden" id="btn_sub" class="btn_sub" onclick="addReport();"/>
 		<input type="hidden" id="gradeCount" class="gradeCount"/>
 		<input id="reportId" name="reportId" type="hidden" value="${report.reportId }">
 		<table style="width: 700px;" cellpadding="0" align="center" cellspacing="1" class="formtable">
 			<tr>
				<td align="right"><label class="Validform_label">
						所属分类 </label></td>
				<td class="value">
					<select  id="classId" name="classId">
					   <option value="" selected="selected">--请选择--</option>
					   <c:forEach items="${classList }" var="reviewType">
					   		<option <c:if test="${report.classId==reviewType.classId }">selected="selected"</c:if> value="${reviewType.classId }">${reviewType.title }</option>
					   </c:forEach>
					</select>
					<span class="Validform_checktip"  id="classIdTip"></span>
					 </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						维度名称 </label></td>
				<td class="value">
					<input datatype="*" id="reportName" name="reportName" value="${report.reportName }"
					type="text" style="width: 300px" class="inputxt"><span class="Validform_checktip" id="reportNameTip"></span>
					</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						分值设置 </label></td>
				<td class="value">
					<table style="width: 100%">
						<thead>
							<th align="center" field="num">序号</th>
							<th align="center" field="range">分值范围</th>
							<th align="center" field="resultExplain">结果描述</th>
							<th align="center" field="opt">操作</th>
						</thead>
						<tbody id="gradeTable">
							<c:forEach items="${list }" var="item" varStatus="i">
								<tr>
									<td>
										<input type="radio" name="reportNum" id="reportNum"
										<c:if test="${item.reportGradeId!= NULL && item.reportGradeId==report.curGradeId }">checked="checked"</c:if> value="${i.index }"/>${i.index+1 }
									</td>
									<td>
										<input type="number" datatype="*" style="width:50px;" name="reportGradeList[${i.index}].gradeSmall"
										 id="reportGradeList[${i.index}].gradeSmall" value="${item.gradeSmall }"/>
										<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">分值范围</label>
										——
										<input type="number" datatype="*" style="width:50px;" name="reportGradeList[${i.index}].gradeBig"
										 id="reportGradeList[${i.index}].gradeBig" value="${item.gradeBig }"/>
										<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">分值范围</label>
									</td>
									<td>
										<textarea cols="55" rows="2" datatype="*" name="reportGradeList[${i.index}].resultExplain"
										 id="reportGradeList[${i.index}].resultExplain">${item.resultExplain }</textarea>
										<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">结果描述</label>
									</td>
									<td>
										<input type="file" name="reportGradeList[${i.index}].file" id="file_${i.index }" style="display: none" onchange="checkFile(this,'${i.index }');"/>
										<input type="button" onclick="uploadTxt('file_${i.index }');" value="上传TXT"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<table>
						<tr align="center">
								<td rowspan="4" align="center">
									<input type="button"  name="addBtn" onclick="addRow();" value="添加"></input>
									<input type="button" name="delBtn"  onclick="del();" value="删除"></input>
								</td>
							</tr>
					</table>
					</td>
			</tr>
 		</table>
 		<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
		<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
		<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
		<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype.js"></script>
		<script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>
		<SCRIPT type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></SCRIPT>
 		<script type="text/javascript">
 		
 		function addRow() {
			var tr = $("#gradeTable tr");
				var thtml = "<tr><td><input type=\"radio\" name=\"reportNum\" id=\"reportNum\" value=\""+tr.length+"\"/>"+(tr.length+1)+"</td>"+
					"<td><input type=\"number\" datatype=\"*\" style=\"width:50px;\" name=\"reportGradeList["+tr.length+"].gradeSmall\""+
				    "id=\"reportGradeList["+tr.length+"].gradeSmall\" value=\"\"/><span class=\"Validform_checktip\"></span>"+
				    "<label class=\"Validform_label\" style=\"display: none;\">分值范围</label>——&nbsp;&nbsp;<input type=\"number\" datatype=\"*\" style=\"width:50px;\""+
				    "name=\"reportGradeList["+tr.length+"].gradeBig\" id=\"reportGradeList["+tr.length+"].gradeBig\" value=\"\"/>"+
					"<span class=\"Validform_checktip\"></span><label class=\"Validform_label\" style=\"display: none;\">分值范围</label></td>"+
					"<td><textarea cols=\"55\" rows=\"2\" datatype=\"*\" name=\"reportGradeList["+tr.length+"].resultExplain\""+ 
					"id=\"reportGradeList["+tr.length+"].resultExplain\"></textarea><span class=\"Validform_checktip\"></span>"+
					"<label class=\"Validform_label\" style=\"display: none;\">结果描述</label></td><td><input type=\"file\" name=\"reportGradeList["+tr.length+"].file\""+
					"id=\"file_"+tr.length+"\"" + "style=\"display: none\" onchange=\"checkFile(this,'"+tr.length+"');\"/><input type=\"button\""+ 
					"onclick=\"uploadTxt('file_'"+tr.length+");\" value=\"上传TXT\"/></td></tr>";
					
			$("#gradeTable").append(thtml);
				
		}
 		
 		function del() {
			var tr = $("#gradeTable tr");
			tr[tr.length - 1].remove();
		}
 		
 		//校验文档格式
 		function checkFile(file,index){
 			var name = file.value;
			name=name.substring(name.lastIndexOf(".")+1,name.length);
		    if(name.toLowerCase().indexOf("txt") <-1) {
		    	$.messager.alert("提示","仅限上传TXT文档!","info");
		    	file.outerHTML = file.outerHTML.replace(/(value=\").+\"/i, "$1\""); 
		    } else {
		    	 document.getElementById("reportGradeList["+index+"].resultExplain").value=file.value;
		    }
 		}
 		
 		//上传TXT
 		function uploadTxt(id) {
			$("#" + id).click();
		}
 		
 		function addReport(){
			 $("#addForm").form('submit',{
						onSubmit : function() {
							var tr = $("#gradeTable tr");
							var classId = $("#classId").val();
							if($.trim(classId) == "") {
								$("#classIdTip").text("请选择分类");
								$("#classIdTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#classIdTip").text("通过信息验证");
								$("#classIdTip").attr("class","Validform_checktip Validform_right");
							}
							
							var reportName = $("#reportName").val();
							if($.trim(reportName) == "") {
								$("#reportNameTip").text("请填写维度名称");
								$("#reportNameTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#reportNameTip").text("通过信息验证");
								$("#reportNameTip").attr("class","Validform_checktip Validform_right");
							}
							
							for(var i=0;i<tr.length;i++) {
								var gradeSmall = document.getElementById("reportGradeList["+i+"].gradeSmall").value;
								var gradeBig = document.getElementById("reportGradeList["+i+"].gradeBig").value;
								
								var resultExplain = document.getElementById("reportGradeList["+i+"].resultExplain").value;
								
								if($.trim(gradeSmall) == "") {
									//document.getElementById("reportGradeList["+i+"].gradeSmall").focus();
									$.messager.alert("提示","分值范围不能为空","info");
									
									return false;
								} 
								
								if($.trim(gradeBig) == "") {
									//document.getElementById("reportGradeList["+i+"].gradeBig").focus();
									$.messager.alert("提示","分值范围不能为空","info");
									return false;
								} 
								
								if($.trim(resultExplain) == "") {
									//document.getElementById("reportGradeList["+i+"].resultExplain").focus();
									$.messager.alert("提示","结果描述不能为空","info");
									return false;
								} 
							}
							return true;
						},
						success : function(data) {
							//var d = $.parseJSON(data);
							var d = data;
							var win = frameElement.api.opener;
							window.top.$.messager.progress('close');
							if (d.success == true) {
								frameElement.api.close();
								//$("#questionList",win.document).datagrid("load");
								win.tip(d.msg);
								
							} else {
								if (d.responseText == ''
										|| d.responseText == undefined)
									$("#addForm").html(d.msg);
								else
									$("#addForm").html(d.responseText);
								return false;
							}
							win.reloadTable();
						}
					}); 
					
		}
 		
		/* $(function() {
			$("#addForm").Validform(
					{
						tiptype : 1,
						btnSubmit : "#btn_sub",
						btnReset : "#btn_reset",
						ajaxPost : true,
						usePlugin : {
							passwordstrength : {
								minLen : 6,
								maxLen : 18,
								trigger : function(obj, error) {
									if (error) {
										obj.parent().next().find(
												".Validform_checktip")
												.show();
										obj.find(".passwordStrength")
												.hide();
									} else {
										$(".passwordStrength").show();
										obj.parent().next().find(
												".Validform_checktip")
												.hide();
									}
								}
							}
						},
						beforeSubmit:function(curForm){
							var tr = $("#gradeTable tr");
							$("#gradeCount").val(tr.length);
							var reportNum = $("input[name='reportNum']:checked").val();

							if($.trim(reportNum) == "") {
								$.messager.alert("提示","请选择一个分值范围!","info");
								return false;
							}
							return true;
						},
						callback : function(data) {
							var win = frameElement.api.opener;
							if (data.success == true) {
								frameElement.api.close();
								win.tip(data.msg);
							} else {
								if (data.responseText == ''
										|| data.responseText == undefined)
									$("#addForm").html(data.msg);
								else
									$("#addForm").html(data.responseText);
								return false;
							}
							win.reloadTable();
						}
					});
		}); */
		</script>	
 	</form>
 </body>		