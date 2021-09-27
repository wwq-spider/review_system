<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加变量</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
 	<form id="addForm" action= "variate.do?addorupdate" method="post" enctype="multipart/form-data">
 		<input type="hidden" id="btn_sub" class="btn_sub" onclick="addVariate();"/>
 		<input type="hidden" id="gradeCount" class="gradeCount"/>
 		<input id="variateId" name="variateId" type="hidden" value="${variate.variateId }">
 		<table style="width: 700px;" cellpadding="0" align="center" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">所属分类 </label></td>
				<td class="value">
					<select id="classId" name="classId" datatype="*">
					   <option value="" selected="selected">--请选择--</option>
					   <c:forEach items="${classList }" var="reviewType">
					   		<option <c:if test="${reviewType.classId==variate.classId }">selected="selected"</c:if> value="${reviewType.classId }" >
					   			${reviewType.title }
					   		</option>
					   </c:forEach>
					</select>
					<span id="classIdTip" class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						因子名称 </label></td>
				<td class="value">
					<input datatype="*" id="variateName" name="variateName" value="${variate.variateName }"
					type="text" style="width: 300px" class="inputxt"><span id="variateNameTip" class="Validform_checktip"></span></td>
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
										<input type="radio" name="variateNum" id="variateNum" 
										 <c:if test="${item.variateGradeId != NULL && item.variateGradeId==variate.curGradeId }">checked="checked"</c:if> value="${i.index }"/>${i.index+1 }
									</td>
									<td>
										<input type="number" datatype="*" style="width:50px;" name="variateGradeList[${i.index}].gradeSmall"
										 id="variateGradeList[${i.index}].gradeSmall" value="${item.gradeSmall }"/>
										<span id="variateGradeList[${i.index}].gradeSmallTip" class="Validform_checktip"></span>
										——
										<input type="number" datatype="*" style="width:50px;" name="variateGradeList[${i.index}].gradeBig"
										 id="variateGradeList[${i.index}].gradeBig" value="${item.gradeBig }"/>
										<span id="variateGradeList[${i.index}].gradeBigTip" class="Validform_checktip"></span>
									</td>
									<td>
										<textarea cols="55" rows="2" datatype="*" name="variateGradeList[${i.index}].resultExplain" 
										id="variateGradeList[${i.index}].resultExplain">${item.resultExplain }</textarea>
										<span id="variateGradeList[${i.index}].resultExplain" class="Validform_checktip"></span>
									</td>
									<td>
										<input type="file" name="variateGradeList[${i.index}].file" id="file_${i.index }" style="display: none" onchange="checkFile(this,'${i.index }');"/>
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
				var thtml = "<tr><td><input type=\"radio\" name=\"variateNum\" id=\"variateNum\" value=\""+tr.length+"\"/>"+(tr.length+1)+"</td>"+
					"<td><input type=\"number\" datatype=\"*\" style=\"width:50px;\" name=\"variateGradeList["+tr.length+"].gradeSmall\""+
				    "id=\"variateGradeList["+tr.length+"].gradeSmall\" value=\"\"/><span class=\"Validform_checktip\"></span>"+
				    "<label class=\"Validform_label\" style=\"display: none;\">分值范围</label>——&nbsp;&nbsp;<input type=\"number\" datatype=\"*\" style=\"width:50px;\""+
				    "name=\"variateGradeList["+tr.length+"].gradeBig\" id=\"variateGradeList["+tr.length+"].gradeBig\" value=\"\"/>"+
					"<span class=\"Validform_checktip\"></span><label class=\"Validform_label\" style=\"display: none;\">分值范围</label></td>"+
					"<td><textarea cols=\"55\" rows=\"2\" datatype=\"*\" name=\"variateGradeList["+tr.length+"].resultExplain\""+ 
					"id=\"variateGradeList["+tr.length+"].resultExplain\"></textarea><span class=\"Validform_checktip\"></span>"+
					"<label class=\"Validform_label\" style=\"display: none;\">结果描述</label></td><td><input type=\"file\" name=\"variateGradeList["+tr.length+"].file\""+
					"id=\"file_"+tr.length+"\"" + "style=\"display: none\" onchange=\"checkFile(this);\"/><input type=\"button\""+ 
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
		    	//document.getElementById("variateGradeList["+index+"].resultExplain").value="";
		    	document.getElementById("variateGradeList["+index+"].resultExplain").value=file.value;
		    }
 		}
 		
 		//上传TXT
 		function uploadTxt(id) {
			$("#" + id).click();
		}
 		
 		 function addVariate() {
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
								
								var variateName = $("#variateName").val();
								if($.trim(variateName) == "") {
									$("#variateNameTip").text("请填写因子名称");
									$("#variateNameTip").attr("class","Validform_checktip Validform_wrong");
									return false;
								} else {
									$("#variateNameTip").text("通过信息验证");
									$("#variateNameTip").attr("class","Validform_checktip Validform_right");
								}
								
								for(var i=0;i<tr.length;i++) {
									var gradeSmall = document.getElementById("variateGradeList["+i+"].gradeSmall").value;
									var gradeBig = document.getElementById("variateGradeList["+i+"].gradeBig").value;
									
									var resultExplain = document.getElementById("variateGradeList["+i+"].resultExplain").value;
									
									if($.trim(gradeSmall) == "") {
										$.messager.alert("提示","分值范围不能为空","info");
										return false;
									} 
									
									if($.trim(gradeBig) == "") {
										$.messager.alert("提示","分值范围不能为空","info");
										return false;
									} 
									
									if($.trim(resultExplain) == "") {
										$.messager.alert("提示","结果描述不能为空","info");
										return false;
									} 
								}
								
								return true;
							},
							success : function(data) {
								var d = $.parseJSON(data);
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
		</script>	
 	</form>
 </body>		