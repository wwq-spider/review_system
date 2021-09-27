<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
<title>添加项目</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

</head>
 <body style="overflow-y: auto;overflow-x:hidden" scroll="no">
 	<form id="addForm" action= "reviewProject.do?addorupdate" method="post">
 		<input type="hidden" id="btn_sub" class="btn_sub"/>
 		<input id="id" name="id" type="hidden" value="${project.id}">
 		<table style="width: 100%" cellpadding="0" align="center" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						项目名称: </label></td>
				<td class="value"><input datatype="*" id="projectName" name="projectName" value="${project.projectName }"
					type="text" style="width: 380px" class="inputxt"><span class="Validform_checktip"></span>
					 <label class="Validform_label" style="display: none;">项目名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						项目描述: </label></td>
				<td class="value">
					<textarea id="projectDesc" name="projectDesc" cols="60" rows="3" datatype="*">${project.projectDesc }</textarea>
				</td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label">
					测评试卷</label></td>
				<td class="value">
					<select  id="classIds" name="classIds" multiple style="width: 380px; height: 300px;" datatype="*">
						<c:forEach items="${classList }" var="reviewType">
							<c:set var="iscontain" value="false" />
							<c:forEach items="${project.reviewProjectClassList}" var="map">
								<c:if test="${map.classId eq reviewType.classId}">
									<c:set var="iscontain" value="true" />
								</c:if>
							</c:forEach>
							<option <c:if test="${iscontain}">selected="selected"</c:if> value="${reviewType.classId }">${reviewType.title }</option>
						</c:forEach>
					</select>
					<span class="Validform_checktip"  id="classIdsTip"></span>
				</td>
			</tr>
 		</table>
		</div>
		<link rel="stylesheet" href="plug-in/Validform/css/style.css"
			type="text/css" />
		<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css"
			type="text/css" />
		<script type="text/javascript"
			src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
		<script type="text/javascript"
			src="plug-in/Validform/js/Validform_Datatype.js"></script>
		<script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>
		
		<SCRIPT type="text/javascript"
			src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></SCRIPT>
 		<script type="text/javascript">
 	
 		var windowapi = frameElement.api, W = windowapi.opener;
 		$(function(){
 			$('#importBtn').linkbutton({   
 	 		    iconCls: 'icon-add'  
 	 		});
 	 		$('#addBtn').linkbutton({   
 	 		    iconCls: 'icon-add'  
 	 		});  
 	 		$('#delBtn').linkbutton({   
 	 		    iconCls: 'icon-remove'  
 	 		});
 		})
 		
		 // 刷新
	    function reloadTable(){
	    	$('#questionList').datagrid('reload');
	    }
 		
 		function callbackAddRow() {
 			iframe = this.iframe.contentWindow;
 			$('#btn_sub', iframe.document).click();
 			return false;
 			
 		}

		$(function() {
			$("#addForm").Validform({
						tiptype : 3,
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
							let classidArr = $("#classIds").val();
							for (let i=0;i < classidArr.length; i++) {
								let tmpInput=$("<input type='hidden' name='reviewProjectClassList["+ i +"].classId'/>");
								tmpInput.attr("value", classidArr[i]);
								$(curForm).append(tmpInput)
							}
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
			});
		</script>
 		
 	</form>
 	
 </body>		