<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>添加分类</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>

</head>
<body style="overflow-y: auto;overflow-x:hidden" scroll="no">
<form id="addForm" action= "reviewClass.do?addorupdate" method="post" enctype="multipart/form-data">
	<input type="hidden" id="btn_sub" class="btn_sub"/>
	<input id="classId" name="classId" type="hidden" value="${reviewClass.classId}">
	<table style="width: 100%;" cellpadding="0" align="center" cellspacing="1" class="formtable" id="selectTable">
		<tr>
			<td align="right"><label class="Validform_label">
				排序ID: </label></td>
			<td class="value"><input datatype="n" id="sortId" name="sortId" value="${reviewClass.sortId }"
									 type="text" style="width: 300px" class="inputxt"><span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">排序ID</label></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">
				分类名称: </label></td>
			<td class="value"><input datatype="*" id="title" name="title" value="${reviewClass.title }"
									 type="text" style="width: 300px" class="inputxt"><span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">分类名称</label></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">
				引导语: </label></td>
			<td class="value">
				<textarea rows="3" cols="80" id="guide" name="guide" datatype="*">${reviewClass.guide }</textarea>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">封面图片: </label></td>
			<td class="value">
				<input type="file" name="contentImg" id="contentImg" onchange="previewImage(this,'content')" style="display: none"></input>
				<div id="previewcontent">
					<c:if test="${reviewClass.bannerImg != null && reviewClass.bannerImg != '' }">
						<img alt="图片预览" id="preview" width="50px" height="50px" src="https://review-images.oss-cn-beijing.aliyuncs.com/${reviewClass.bannerImg}"></img>
					</c:if>
				</div>
				<input type="button"  name="uploadBtn" onclick="uploadImg('contentImg');" value="上传封面"></input>
			</td>
		</tr>
	</table>
	<div id="questionDiv" style="display: none;border-style: solid;border: 1px;border-color:#E6E6E6; width: 100%;padding-top:5px">
		<table  cellpadding="0"  id="questionList">
			<thead>
			<tr bgcolor="#E6E6E6">
				<th field="questionId"  hidden="hidden" align="center"  width="40">题目编号</th>
				<th  field="content" width="70" align="center" bgcolor="#EEEEEE">题目标题</td>
				<th field="opt" width="40" align="center" bgcolor="#EEEEEE">操作</td>
			</tr>
			</thead>
		</table>
		<div style="padding: 5px; height: auto;" id="reviewClassTb" class="datagrid-toolbar">
			<div class="datagrid-toolbar" style="height:30px;">
				<span style="float:left;">
					<a id="importBtn" href="#" class="easyui-linkbutton" plain="true" icon="icon-add">导入题库</a></span>
				<span style="float:left;">
					<a id="addBtn" href="#" class="easyui-linkbutton" plain="true" icon="icon-add">新增题目</a></span>

			</div>
		</div>
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

			$("#importBtn").bind('click', function() {
				openuploadwin('题目导入', 'review.do?toQuestionImport&classId=${reviewClass.classId}', "questionList");
			});

			$('#addBtn').bind('click', function(){
				var title = "${reviewClass.title}";
				title = encodeURI(encodeURI(title));
				createUpWindow('添加题目','review.do?toAdd&classId=${reviewClass.classId}&title='+title,910,500)

			});

		})

		function createUpWindow(title,addurl,width,height) {
			if (typeof (windowapi) == 'undefined') {
				$.dialog({
					content : 'url:'+addurl,
					zIndex : 2100,
					title : title,
					lock : true,
					width : width,
					height : height,
					left : '55%',
					top : '55%',
					opacity : 0.4,
					button : [ {
						name : '确认',
						callback : callbackAddRow,
						focus : true
					}, {
						name : '取消',
						callback : function() {
						}
					} ]
				});
			} else {
				$.dialog({
					content : 'url:'+addurl,
					zIndex : 2100,
					title : title,
					lock : false,
					parent:windowapi,
					width : width,
					height : height,
					left : '55%',
					top : '55%',
					opacity : 0.4,
					button : [ {
						name : '确认',
						callback : callbackAddRow,
						focus : true
					}, {
						name : '取消',
						callback : function() {
						}
					} ]
				});
			}
		}
		// 编辑初始化数据
		function getData(data) {
			$('.datagrid-header-inner .datagrid-cell ').css("text-align","center");
			var rows = [];
			var total = data.total;
			var title = "${reviewClass.title}";
			title = encodeURI(encodeURI(title));

			for ( var i = 0; i < data.rows.length; i++) {
				rows.push({
					questionId : data.rows[i].questionId,
					content : data.rows[i].content,
					reviewType : "${reviewClass.title}",
					createTime : data.rows[i].createTime,
					createBy : data.rows[i].createBy,
					opt : "[<a href=\"#\" onclick=\"createUpWindow('编辑题目','review.do?toAdd&classId=${reviewClass.classId}&title="+title+"&questionId="
							+ data.rows[i].questionId
							+ "',910,500)\">编辑</a>]&nbsp;&nbsp;"
							+ "[<a href=\"#\" onclick=\"delObj('review.do?del&questionId="
							+ data.rows[i].questionId
							+ "','questionList')\">删除</a>]"
				});
			}
			var newData = {
				"total" : total,
				"rows" : rows
			};
			$('.datagrid-cell-c1-name').css("text-align", "center");
			return newData;
		}

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
			if($.trim('${reviewClass.classId}') != "") {
				$("#questionDiv").show();
				// 设置datagrid属性
				$('#questionList').datagrid({
					title: '问题列表',
					idField: 'id',
					width:1000,
					//height:'100%',
					loadMsg: '数据加载中...',
					pageSize: 20,
					pagination : true,
					sortOrder:'asc',
					rownumbers:true,
					singleSelect:true,
					fitColumns:true,
					showFooter:true,
					url:'review.do?datagrid&classId=${reviewClass.classId}',
					toolbar: '#reviewClassTb',//显示查询条件部分
					loadFilter: function(data){
						return getData(data);
					}
				});

				//设置分页控件
				$('#questionList').datagrid('getPager').pagination({
					pageSize: 20,
					pageList: [10,20,30],
					beforePageText: '',
					afterPageText: '/{pages}',
					displayMsg: '{from}-{to}共{total}条',
					showPageList:true,
					showRefresh:true,
					onBeforeRefresh:function(pageNumber, pageSize){
						$(this).pagination('loading');
						$(this).pagination('loaded');
					}
				});
			}

			//提交form表单
			$("#btn_sub").click(function(event) {
				$("#addForm").form(
						'submit',
						{
							onSubmit : function() {
								let sortId = $("#sortId").val();
								if($.trim(sortId) == "") {
									$("#contentTip").text("请填写排序编号");
									$("#contentTip").attr("class","Validform_checktip Validform_wrong");
									return false;
								} else {
									$("#contentTip").text("通过信息验证");
									$("#contentTip").attr("class","Validform_checktip Validform_right");
								}

								let title = $("#title").val();
								if($.trim(title) == "") {
									$("#contentTip").text("请填写分类名称");
									$("#contentTip").attr("class","Validform_checktip Validform_wrong");
									return false;
								} else {
									$("#contentTip").text("通过信息验证");
									$("#contentTip").attr("class","Validform_checktip Validform_right");
								}

								let guide = $("#guide").val();
								if($.trim(guide) == "") {
									$("#contentTip").text("请填写向导语");
									$("#contentTip").attr("class","Validform_checktip Validform_wrong");
									return false;
								} else {
									$("#contentTip").text("通过信息验证");
									$("#contentTip").attr("class", "Validform_checktip Validform_right");
								}
								return true;
							},
							success : function(data) {
								var d = $.parseJSON(data);
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

			// $("#addForm").Validform({
			// 	tiptype : 3,
			// 	btnSubmit : "#btn_sub",
			// 	btnReset : "#btn_reset",
			// 	ajaxPost : true,
			// 	usePlugin : {
			// 		passwordstrength : {
			// 			minLen : 6,
			// 			maxLen : 18,
			// 			trigger : function(obj, error) {
			// 				if (error) {
			// 					obj.parent().next().find(
			// 							".Validform_checktip")
			// 							.show();
			// 					obj.find(".passwordStrength")
			// 							.hide();
			// 				} else {
			// 					$(".passwordStrength").show();
			// 					obj.parent().next().find(
			// 							".Validform_checktip")
			// 							.hide();
			// 				}
			// 			}
			// 		}
			// 	},
			// 	beforeSubmit:function(curForm){
			// 		//alert($(curForm).attr("enctype"));
			// 	},
			// 	callback : function(data) {
			// 		var win = frameElement.api.opener;
			// 		if (data.success == true) {
			// 			frameElement.api.close();
			// 			win.tip(data.msg);
			// 		} else {
			// 			if (data.responseText == ''
			// 					|| data.responseText == undefined)
			// 				$("#addForm").html(data.msg);
			// 			else
			// 				$("#addForm").html(data.responseText);
			// 			return false;
			// 		}
			// 		win.reloadTable();
			// 	}
			// });
		});
	</script>

</form>

</body>