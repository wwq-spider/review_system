<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加问题</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
  <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
  <style type="text/css">
  	  .select {
  	  	padding-bottom: 2px;
  	  }
  	  
  	  .selectSpan{
  	  	padding-left: 2px;
  	  }
  </style>
 </head>
 <body style="overflow-y: auto;overflow-x: hidden;" >
 	<form id="addForm" action= "review.do?add" method="post" enctype="multipart/form-data">
 		<input type="hidden" id="btn_sub" class="btn_sub" onclick="addQuestion();"/>
 		<input type="hidden" id="selectCount" name="selectCount"/>
 		<input id="questionNum" name="questionNum" type="hidden" value="${questionNum }">
 		<input id="classId" name="classId" type="hidden" value="${classId }">
 		<table style="width: 900px;" cellpadding="0" align="center" cellspacing="1" class="formtable">	
 			<tr>
				<td align="right"><label class="Validform_label">测评类型&nbsp;&nbsp; </label></td>
				<td class="value">
					<label>${reviewType }</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">题目内容&nbsp;&nbsp;</label></td>
				<td class="value">
					<table style="width: 100%;">
						<tr>
							<td>
							<textarea datatype="*" id="content" name="content" cols="60" rows="2">${question.content }</textarea>&nbsp;<span style="color: red">*</span>
							<span id="contentTip"></span>
							</td>
							<td>
							<input type="file" name="contentImg" id="contentImg" onchange="previewImage(this,'content')" style="display: none"></input>
							<div id="previewcontent"></div>
							<input type="button"  name="uploadBtn" onclick="uploadImg('contentImg');" value="上传图片"></input>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						选项&nbsp;&nbsp; </label></td>
				<td class="value" id="selectTd">
					<table style="width: 100%;" >
						<thead>
							<tr>
								<th align="center" style="width: 10%">答案序号</th>
								<th align="center" style="width: 50%">内容</th>
								<th align="center" style="width: 20%">分值</th>
								<th align="center" style="width: 15%">图片</th>
							</tr>
						<thead>
						<tbody id="selectTable">
							<c:forEach items="${answersList }" var="answer" varStatus="i">
							
							<tr>
								<td align="center">
									<input type="hidden" name="selectList[${i.index }].selectId" id="selectList[${i.index }].selectId" value=""></input>
									<input type="hidden" name="selectList[${i.index }].selCode" id="selectList[${i.index }].selCode" value="${answer.selCode }"></input>
									<input type="radio" name="rightAnswer" id="rightAnswer" value="${answer.selCode }" multiple="multiple"/>${answer.selCode }</td>
								<td align="center">
									<input id="selectList[${i.index }].selectContent" name="selectList[${i.index }].selectContent" value="" dataType="*" type="text" style="width: 300px;" class="inputxt">
									<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">内容</label></td>
								</td>
								<td align="center">
									<input  id="selectList[${i.index }].selectGrade" name="selectList[${i.index }].selectGrade" value="" dataType="n" type="number" style="width: 50px;" class="inputxt">
									<span class="Validform_checktip"></span><label class="Validform_label" style="display: none;">分值</label></td>
								</td>
								<td align="center">
									<input type="file" name="selectList[${i.index }].contentImg" id="selectList[${i.index }].contentImg" onchange="previewImage(this,'${i.index }')" style="display: none"></input>
									<div id="preview${i.index }">
									</div>
									<input type="button"  name="uploadBtn" onclick="uploadImg('selectList[${i.index }].contentImg');" value="上传图片"></input>
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

 		<script type="text/javascript">
 		
 		 var MAXWIDTH  = 50; 
         var MAXHEIGHT = 50;
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

			var arr = [ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' ];
			function addRow() {
				var tr = $("#selectTable tr");

				if(arr.length > tr.length) {
					var thtml = "<tr><td align=\"center\">"+
							"<input type=\"hidden\" name=\"selectList["+tr.length+"].selectId\" id=\"selectList["+tr.length+"].selectId\" value=\"\"></input>"+
							"<input type=\"hidden\" name=\"selectList["+tr.length+"].selCode\" id=\"selectList["+tr.length+"].selCode\" value=\""+arr[tr.length]+"\"></input>"
							+"<input type=\"radio\" name=\"rightAnswer\" value=\""+tr.length+"\" id=\"rightAnswer\"/>"+ arr[tr.length]
							+ "</td><td align=\"center\"><input id=\"selectList["+tr.length+"].selectContent\" name=\"selectList["+tr.length+"].selectContent\" value=\"\""+
	 						  " dataType=\"*\" type=\"text\" style=\"width: 300px;\" class=\"inputxt\"><span class=\"Validform_checktip\"></span>"
							+ "<label class=\"Validform_label\" style=\"display: none;\">内容</label></td></td><td align=\"center\">"
							+ "<input  id=\"selectList["+ tr.length+ "].selectGrade\" name=\"selectList["+ tr.length
							+ "].selectGrade\" value=\"\" dataType=\"n\" type=\"number\" style=\"width: 50px;\" class=\"inputxt\">"
							+ "<span class=\"Validform_checktip\"></span><label class=\"Validform_label\" style=\"display: none;\">"
							+ "分值</label></td></td><td align=\"center\"><input type=\"file\" name=\"selectList["+tr.length+"].contentImg\" id=\"selectList["+tr.length+
							"].contentImg\" onchange=\"previewImage(this,'"+tr.length+"')\" style=\"display: none\"></input><div id=\"preview"+tr.length
							+"\"></div><input type=\"button\"  name=\"uploadBtn\" onclick=\"uploadImg('selectList["+tr.length+
							"].contentImg');\" value=\"上传图片\"></input></td></tr>";
						$("#selectTable").append(thtml);
					
				}
			}

			function del() {
				var tr = $("#selectTable tr");
				tr[tr.length - 1].remove();
			}

			function uploadImg(id) {
				var d = document.getElementById(id);
				d.click();
			}

			function addQuestion() {
				var tr = $("#selectTable tr");
				$("#selectCount").val(tr.length);
				$("#addForm").form(
						'submit',
						{
							onSubmit : function() {
								var content = $("#content").val();
								if($.trim(content) == "") {
									$("#contentTip").text("请填写题目内容");
									$("#contentTip").attr("class","Validform_checktip Validform_wrong");
									return false;
								} else {
									$("#contentTip").text("通过信息验证");
									$("#contentTip").attr("class","Validform_checktip Validform_right");
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
			/* $(function() {
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

			//选项检测
			function checkSelect(selText, grade, selCode) {
				if ($.trim(selText) == "") {
					$("#" + selCode.toLowerCase() + "Tip").text(
							"请填写选项" + selCode);
					$("#" + selCode.toLowerCase() + "Tip").attr("class",
							"Validform_checktip Validform_wrong");
					return false;
				} else {
					//var reviewType = $("#reviewType").combobox("getValue");
					if ($.trim(grade) == "") {
						$("#" + selCode.toLowerCase() + "Tip").text(
								"请填写选项" + selCode + "得分");
						$("#" + selCode.toLowerCase() + "Tip").attr(
								"class",
								"Validform_checktip Validform_wrong");
						return false;
					} else {
						$("#" + selCode.toLowerCase() + "Tip").text(
								"通过信息验证!");
						$("#" + selCode.toLowerCase() + "Tip").attr(
								"class",
								"Validform_checktip Validform_right");
					}
				}
			}

			//检查选项内容
			function checkSelText(obj, selCode) {
				var selText = obj.value;
				if ($.trim(selText) == "") {
					$("#" + selCode.toLowerCase() + "Tip").text(
							"请填写选项" + selCode);
					$("#" + selCode.toLowerCase() + "Tip").attr("class",
							"Validform_checktip Validform_wrong");
					return false;
				} else {
					$("#" + selCode.toLowerCase() + "Tip").text("通过信息验证!");
					$("#" + selCode.toLowerCase() + "Tip").attr("class",
							"Validform_checktip Validform_right");
				}
			}

			//检查选项得分
			function checkSelGrade(obj, selCode) {
				var grade = obj.value;
				if ($.trim(grade) == "") {
					$("#" + selCode.toLowerCase() + "Tip").text(
							"请填写选项" + selCode + "得分");
					$("#" + selCode.toLowerCase() + "Tip").attr("class",
							"Validform_checktip Validform_wrong");
					return false;
				} else {
					$("#" + selCode.toLowerCase() + "Tip").text("通过信息验证!");
					$("#" + selCode.toLowerCase() + "Tip").attr("class",
							"Validform_checktip Validform_right");
				}
			}
		</script>
 		
 	</form>
 	
 </body>		