<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加项目</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>

</head>
<body style="overflow-y: auto;overflow-x:hidden" scroll="no">
<form id="addForm" action= "reviewProject.do?addorupdate" method="post"  enctype="multipart/form-data">
	<input type="hidden" id="btn_sub" class="btn_sub"/>
	<input id="id" name="id" type="hidden" value="${project.id}">
	<table style="width: 100%" cellpadding="0" align="center" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label">
				项目名称: </label></td>
			<td class="value"><input datatype="*" id="projectName" name="projectName" value="${project.projectName }"
									 type="text" style="width: 380px" class="inputxt"><span class="Validform_checktip" id="projectNameTip"></span>
				<label class="Validform_label" style="display: none;">项目名称</label></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">
				项目描述: </label></td>
			<td class="value">
				<textarea id="projectDesc" name="projectDesc" cols="60" rows="3" datatype="*">${project.projectDesc }</textarea>
				<span class="Validform_checktip" id="projectDescTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">封面图片: </label></td>
			<td class="value">
				<input type="file" name="contentImg" id="contentImg" onchange="previewImage(this,'content')" style="display: none"/>
				<div id="previewcontent">
					<c:if test="${project.cover != null && project.cover != '' }">
						<img alt="图片预览" id="preview" width="100px" height="100px" src="${aliyunOssHost}${project.cover}"/>
					</c:if>
				</div>
				<input type="button"  name="uploadBtn" onclick="uploadImg('contentImg');" value="上传封面"/>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">关联用户组: </label></td>
			<td class="value">
				<c:forEach items="${groupList }" var="userGroup">
					<c:set var="iscontain" value="false" />
					<c:if test="${project.groupId eq userGroup.id}">
						<c:set var="iscontain" value="true" />
					</c:if>
					<input type="radio" name="groupId" id="groupId" datatype="*" <c:if test="${iscontain}">checked="checked"</c:if> value="${userGroup.id }">${userGroup.departname }</input>
				</c:forEach>
				<span class="Validform_checktip"  id="groupIdTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">
				测评量表: </label></td>
			<td class="value">
				<select  id="classIds" name="classIds" multiple style="width: 380px; height: 380px;" datatype="*">
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
		<tr>
			<td align="right"><label class="Validform_label">是否显示报告: </label></td>
			<td class="value">
				<input type="radio" name="showReport" id="showReport" datatype="*" <c:if test="${project.showReport == 2}">checked="checked"</c:if> value="2">是</input>
				<input type="radio" name="showReport" id="showReport" datatype="*" <c:if test="${project == null || project.showReport == 1}">checked="checked"</c:if> value="1">否</input>
				<span class="Validform_checktip"  id="showReportTip"></span>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">是否开放: </label></td>
			<td class="value">
				<input type="radio" name="isOpen" id="isOpen" datatype="*" <c:if test="${project.isOpen == 2}">checked="checked"</c:if> value="2">是</input>
				<input type="radio" name="isOpen" id="isOpen" datatype="*" <c:if test="${project == null || project.isOpen == 1}">checked="checked"</c:if> value="1">否</input>
				<span class="Validform_checktip"  id="isOpenTip"></span>
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

		let width = window.top.document.body.offsetWidth * 0.65;
		var MAXWIDTH  = 100;
		var MAXHEIGHT = 100;
		//图片上传预览    IE是用了滤镜。
		function previewImage(file,code) {
			var div = document.getElementById("preview"+code);
			if (file.files && file.files[0]){
				div.innerHTML ='<img alt="图片预览" src="" id="preview'+code+'Img"/>';
				var img = document.getElementById("preview"+code+"Img");
				img.onload = function(){
					var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
					img.width  =  rect.width;
					img.height =  rect.height;
//                 img.style.marginLeft = rect.left+'px';
					img.style.marginTop = rect.top+'px';
				}
				var reader = new FileReader();
				reader.onload = function(evt){img.src = evt.target.result;}
				reader.readAsDataURL(file.files[0]);
			} else { //兼容IE
				var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
				file.select();
				var src = document.selection.createRange().text;
				div.innerHTML = '<img id="preview"+code+"Img">';
				var img = document.getElementById("preview"+code+"Img");
				img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
				var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
				status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
				div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
			}
		}

		//设置图片大小
		function clacImgZoomParam( maxWidth, maxHeight, width, height ){
			var param = {
				top : 0,
				left : 0,
				width : width,
				height : height
			};
			if (width > maxWidth || height > maxHeight) {
				rateWidth = width / maxWidth;
				rateHeight = height / maxHeight;

				if (rateWidth > rateHeight) {
					param.width = maxWidth;
					param.height = Math.round(height / rateWidth);
				} else {
					param.width = Math.round(width / rateHeight);
					param.height = maxHeight;
				}
			}

			param.left = Math.round((maxWidth - param.width) / 2);
			param.top = Math.round((maxHeight - param.height) / 2);
			return param;
		}

		function uploadImg(id) {
			var d = document.getElementById(id);
			d.click();
		}

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

		//提交form表单
		$("#btn_sub").click(function(event) {
			$("#addForm").form(
					'submit',
					{
						onSubmit : function() {
							let projectName = $("#projectName").val();
							if($.trim(projectName) == "") {
								$("#projectNameTip").text("请填写项目名称");
								$("#projectNameTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#projectNameTip").text("通过信息验证");
								$("#projectNameTip").attr("class","Validform_checktip Validform_right");
							}

							let projectDesc = $("#projectDesc").val();
							if($.trim(projectDesc) == "") {
								$("#projectDescTip").text("请填写项目描述");
								$("#projectDescTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#projectDescTip").text("通过信息验证");
								$("#projectDescTip").attr("class","Validform_checktip Validform_right");
							}

							let groupId = $("#groupId:checked").val();
							if($.trim(groupId) == "") {
								$("#groupIdTip").text("请选择用户组");
								$("#groupIdTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#groupIdTip").text("通过信息验证");
								$("#groupIdTip").attr("class","Validform_checktip Validform_right");
							}

							let showReport = $("#showReport:checked").val();
							if($.trim(showReport) == "") {
								$("#showReportTip").text("请选择是否显示报告");
								$("#showReportTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#showReportTip").text("通过信息验证");
								$("#showReportTip").attr("class","Validform_checktip Validform_right");
							}

							let isOpen = $("#isOpen:checked").val();
							if($.trim(isOpen) == "") {
								$("#isOpenTip").text("请选择是否开放项目");
								$("#isOpenTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#isOpenTip").text("通过信息验证");
								$("#isOpenTip").attr("class","Validform_checktip Validform_right");
							}

							let classidArr = $("#classIds").val();
							if(!classidArr || classidArr.length == 0) {
								$("#classIdsTip").text("请选择量表");
								$("#classIdsTip").attr("class","Validform_checktip Validform_wrong");
								return false;
							} else {
								$("#classIdsTip").text("通过信息验证");
								$("#classIdsTip").attr("class", "Validform_checktip Validform_right");
							}

							for (let i=0;i < classidArr.length; i++) {
								let tmpInput=$("<input type='hidden' name='reviewProjectClassList["+ i +"].classId'/>");
								tmpInput.attr("value", classidArr[i]);
								$("#addForm").append(tmpInput)
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
		})
	</script>

</form>

</body>