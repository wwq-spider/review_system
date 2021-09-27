<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>添加分类</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
  <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
  <script type="text/javascript"
			src="plug-in/Validform/js/Validform_v5.3.1_min.js"></script>
		<script type="text/javascript"
			src="plug-in/Validform/js/Validform_Datatype.js"></script>
		<script type="text/javascript" src="plug-in/Validform/js/datatype.js"></script>
		
		<SCRIPT type="text/javascript"
			src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></SCRIPT>
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
 	<form id="addForm" action= "variate.do?addScoreSet" method="post" enctype="multipart/form-data">
 		<input type="hidden" id="btn_sub" class="btn_sub"/>
 		<input type="hidden" id="selectCount" name="selectCount"/>
 		<input id="classId" name="classId" type="hidden" value="${classId }">
 		<table style="width: 100%;" cellpadding="0" align="center" cellspacing="1" class="formtable">	
 			<tr>
				<td align="right"><label class="Validform_label">测评类型&nbsp;&nbsp; </label></td>
				<td class="value">
					<label>${className }</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						分值设置&nbsp;&nbsp; </label></td>
				<td class="value" id="selectTd">
					<table style="width: 100%;" >
						<thead>
							<tr>
								<th align="center" style="width: 10%">因子项</th>
								<th align="center" style="width: 62%">计分条目</th>
								<th align="center" style="width: 12%">操作</th>
								<th align="center" style="width: 16%">计分条目总数运算</th>
							</tr>
						<thead>
						<tbody style="width: 100%;">
								<tr>
									<td align="center">
										<input type="hidden" name="variateId" id="variateId" value="${variateId }"/>
										<span>${variateName }</span>
									</td>
									</td>
									<td align="center">
										<table style="width: 100%;">
										<tbody id="selectTable" style="width: 100%;">
											<tr>
												<c:forEach items="${variateQuestionList }" var="gradeRule" varStatus="a">
													<c:if test="${a.index<7 }">
													<td >
													<input  style="width:38px;" type="number" value="${gradeRule.questionId }" dataType="n" name="gradeRuleList[${a.index}].questionId"
													 id="gradeRuleList[${a.index}].questionId"  class="input"></input>
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">题目编号</label>
														<select style="width:38px;" name="gradeRuleList[${a.index}].calSymbol"
															 id="gradeRuleList[${a.index}].calSymbol">
														<option value="+" <c:if test="${gradeRule.calSymbol=='+' || gradeRule.calSymbol==null || gradeRule.calSymbol=='' }">selected="selected"</c:if>>+</option>
														<option value="-" <c:if test="${gradeRule.calSymbol=='-'}">selected="selected"</c:if>>-</option>
														<option value="*" <c:if test="${gradeRule.calSymbol=='*'}">selected="selected"</c:if>>*</option>
														<option value="/" <c:if test="${gradeRule.calSymbol=='/'}">selected="selected"</c:if>>/</option>
													</select>
													
													</td>
													</c:if>
												</c:forEach>
											</tr>
											<tr <c:if test="${fn:length(variateQuestionList)<=7 }"> style="display: none"</c:if>>
												<c:forEach items="${variateQuestionList }" var="gradeRule" varStatus="a">
												<c:if test="${a.index>=7 }">
													<td>
														<input  style="width:38px;" type="number" value="${gradeRule.questionId }" dataType="n" name="gradeRuleList[${a.index}].questionId"
													 id="gradeRuleList[${a.index}].questionId"  class="input"></input>
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">题目编号</label>
														<select style="width:38px;" name="gradeRuleList[${a.index}].calSymbol"
															 id="gradeRuleList[${a.index}].calSymbol">
														<option value="+" <c:if test="${gradeRule.calSymbol=='+' || gradeRule.calSymbol==null || gradeRule.calSymbol=='' }">selected="selected"</c:if>>+</option>
														<option value="-" <c:if test="${gradeRule.calSymbol=='-'}">selected="selected"</c:if>>-</option>
														<option value="*" <c:if test="${gradeRule.calSymbol=='*'}">selected="selected"</c:if>>*</option>
														<option value="/" <c:if test="${gradeRule.calSymbol=='/'}">selected="selected"</c:if>>/</option>
													</select>
													
												</td></c:if>
												</c:forEach>
											</tr>
											</tbody>
										</table>
									</td>
									<td align="center">
										<input type="button"  onclick="addQ();" value="增加"></input>&nbsp;&nbsp;
										<input type="button"  onclick="delQ();" value="减少"></input>&nbsp;&nbsp;
									<input type="button"  onclick="clearAll();" value="清空"></input>
									</td>
									<td align="center">

												<select style="width:38px;" name="calSymbol1">
														<option value="+" <c:if test="${calSymbol1=='+'}">selected="selected"</c:if>>+</option>
														<option value="-" <c:if test="${calSymbol1=='-'}">selected="selected"</c:if>>-</option>
														<option value="*" <c:if test="${calSymbol1=='*'}">selected="selected"</c:if>>*</option>
														<option value="/" <c:if test="${calSymbol1=='/' || calSymbol1==null || calSymbol1==''}">selected="selected"</c:if>>/</option>
												</select>
												<input  style="width:40px;" type="number" value="${calTotal1}" name="calTotal1"
															 id="calTotal"  class="input"></input>
												
												<select style="width:38px;" name="calSymbol">
														<option value="+" <c:if test="${calSymbol=='+' || calSymbol==null || calSymbol=='' }">selected="selected"</c:if>>+</option>
														<option value="-" <c:if test="${calSymbol=='-'}">selected="selected"</c:if>>-</option>
														<option value="*" <c:if test="${calSymbol=='*'}">selected="selected"</c:if>>*</option>
														<option value="/" <c:if test="${calSymbol=='/'}">selected="selected"</c:if>>/</option>
												</select>
												<input  style="width:38px;" type="number" value="${calTotal}" name="calTotal"/>
												
									</td>
								</tr>
						</tbody>
					</table>
					</td>
			</tr>
 		</table>

 		<script type="text/javascript">
			
 			//清空
 			function clearAll() {
 				$(".input").val("");
 			}
 		
 			//添加一个计分项
 			function addQ(rowNum) {
 				var tr = $("#selectTable tr");
 				var rowNum = 0;
 				if(tr) {
 					for(var k=0; k<tr.length;k++) {
 						rowNum += parseInt($(tr[k]).children("td").length);
 					}
 				}
 				
 				var thtml = "<td>";
 				//alert(rowNum);
 				//if(rowNum % 8 != 0) {
				thtml+= "<input type=\"number\" style=\"width:38px;\" dataType=\"n\" class=\"input\" id=\"gradeRuleList["+rowNum+"].questionId\" name=\"gradeRuleList["+rowNum+"].questionId\"></input>"+
						"<span class=\"Validform_checktip\"></span>"+
						"<label class=\"Validform_label\" style=\"display: none;\">题目编号</label>&nbsp;"+
						"<select style=\"width:38px;\" name=\"gradeRuleList\["+rowNum+"\].calSymbol\" id=\"gradeRuleList\["+rowNum+"\].calSymbol\">"+
						"<option value=\"+\" selected=\"selected\">+</option>"+
						"<option value=\"-\">-</option>"+
						"<option value=\"*\">*</option>"+
						"<option value=\"/\">/</option>"+
						"</select></td>";
 				//}
 				

 				if(tr) {
 					if(rowNum % 7 != 0) {
 						if(rowNum < 7) {
 							$(tr[0]).append(thtml);
 						} else {
 							$(tr[tr.length-1]).append(thtml);
 						}
 						
 					} else {
 						$("#selectTable").append("<tr>"+thtml+"</tr>");
 					}
 				} else {
 					$("#selectTable").append("<tr>"+thtml+"</tr>");
 				}
 			}

 			//删除一组计分项
 			function delQ() {
 				var tr = $("#selectTable tr");
 				var length = $(tr[tr.length-1]).children("td").length;
 				if(length <= 1) {
 					$(tr[tr.length-1]).remove();
 				}
 				if(length > 0) {
 					$(tr[tr.length-1]).children("td")[length-1].remove();
 				}
 			}
 			
			$(function() {
				$("#addForm").Validform({
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
			}); 

		</script>	
 	</form>
 </body>		