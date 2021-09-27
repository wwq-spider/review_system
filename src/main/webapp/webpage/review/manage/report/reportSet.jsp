<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>维度设置</title>
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
  <%
  	List<Integer> list = new ArrayList<Integer>();
  	for(int i=1; i<9;i++) {
  		list.add(i);
  	}
  	request.setAttribute("list", list);
  %>
 </head>
 <body style="overflow-y: auto;overflow-x: hidden;" >
 	<form id="addForm" action= "report.do?addReportSet" method="post" enctype="multipart/form-data">
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
						选项&nbsp;&nbsp; </label></td>
				<td class="value" id="selectTd">
					<table style="width: 100%;" >
						<thead>
							<tr>
								<th align="center" style="width: 10%">维度项目</th>
								<th align="center" style="width: 70%">因子项</th>
								<th align="center" style="width: 15%">操作</th>
							</tr>
						<thead>
						<tbody style="width: 100%;">
								<tr>
									<td align="center">
										<input type="hidden" name="reportId" id="reportId" value="${reportId }"/>
										<span>${reportName }</span>
									</td>
									</td>
									<td align="center">
										<table style="width: 100%;" >
											<tbody id="selectTable" style="width: 100%;">
											<tr>
												<c:forEach items="${reportVariateList}" var="reportVariate" varStatus="a">
												<c:if test="${a.index < 4 }">
													<td>
													<select class="select" name="reportVariateList[${a.index}].variateId" id="reportVariateList[${a.index}].variateId" >
														<option value="" selected="selected">--请选择--</option>
														<c:forEach items="${variateList }" var="variate">
															<option <c:if test="${variate.variateId==reportVariate.variateId}">selected="selected"</c:if> value="${variate.variateId }">${variate.variateName}</option>
														</c:forEach>
													</select>
														<select style="width:40px;" name="reportVariateList[${a.index}].calSymbol"
															 id="reportVariateList[${a.index}].calSymbol" dataType="*">
														<option value="">--请选择--</option>
														<option value="+" <c:if test="${reportVariate.calSymbol=='+' || reportVariate.calSymbol==null || reportVariate.calSymbol=='' }">selected="selected"</c:if>>+</option>
														<option value="-" <c:if test="${reportVariate.calSymbol=='-'}">selected="selected"</c:if>>-</option>
														<option value="*" <c:if test="${reportVariate.calSymbol=='*'}">selected="selected"</c:if>>*</option>
														<option value="/" <c:if test="${reportVariate.calSymbol=='/'}">selected="selected"</c:if>>/</option>
													</select>
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">计算符号</label>
												
													
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">因子项</label>
													</td>
													</c:if>
												</c:forEach>
											</tr>
											<tr>
												<c:forEach items="${reportVariateList}" var="reportVariate" varStatus="a">
													<c:if test="${a.index >= 4 }">
													<td>
													<select class="select" name="reportVariateList[${a.index}].variateId" id="reportVariateList[${a.index}].variateId" >
														<option value="" selected="selected">--请选择--</option>
														<c:forEach items="${variateList }" var="variate">
															<option <c:if test="${variate.variateId==reportVariate.variateId}">selected="selected"</c:if> value="${variate.variateId }">${variate.variateName}</option>
														</c:forEach>
													</select>
														<select style="width:40px;" name="reportVariateList[${a.index}].calSymbol"
															 id="reportVariateList[${a.index}].calSymbol" dataType="*">
														<option value="">--请选择--</option>
														<option value="+" <c:if test="${reportVariate.calSymbol=='+' || reportVariate.calSymbol==null || reportVariate.calSymbol=='' }">selected="selected"</c:if>>+</option>
														<option value="-" <c:if test="${reportVariate.calSymbol=='-'}">selected="selected"</c:if>>-</option>
														<option value="*" <c:if test="${reportVariate.calSymbol=='*'}">selected="selected"</c:if>>*</option>
														<option value="/" <c:if test="${reportVariate.calSymbol=='/'}">selected="selected"</c:if>>/</option>
													</select>
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">计算符号</label>
											
													
													<span class="Validform_checktip"></span>
													<label class="Validform_label" style="display: none;">因子项</label>
													</td>
													</c:if>
												</c:forEach>
												
											</tr>
										</table>
									</td>
									<td align="center">
										<input type="button"  onclick="addQ();" value="增加"></input>&nbsp;&nbsp;
										<input type="button"  onclick="delQ();" value="减少"></input>&nbsp;&nbsp;
									<input type="button"  onclick="clearAll();" value="清空"></input>
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
				$(".select").val("");
			}
		//添加一个计分项
		function addQ() {
			var tr = $("#selectTable tr");
			var variateList = "${variateListJSON}";
			var variateListArr = eval("("+variateList+")");
			var rowNum = 0;
			if(tr) {
				for(var k=0; k<tr.length;k++) {
					rowNum += parseInt($(tr[k]).children("td").length);
				}
			}
			
			var thtml = "<td>";
			
			//if(rowNum % 4 != 0) {
			thtml+="<select class=\"select\" name=\"reportVariateList["+rowNum+"].variateId\" id=\"reportVariateList["+rowNum+
		      	   "].variateId\" ><option value=\"\" selected=\"selected\">--请选择--</option>";
		      	   
      	    for(var i=0; i<variateListArr.length; i++) {
				thtml+="<option  value=\""+variateListArr[i].variateId+"\">"+variateListArr[i].variateName+"</option>";
			}
			thtml +="</select><span class=\"Validform_checktip\"></span>"+
					"<label class=\"Validform_label\" style=\"display: none;\">题目编号</label>&nbsp;";
					"<select style=\"width:40px;\" name=\"reportVariateList\["+rowNum+"\].calSymbol\" id=\"reportVariateList\["+rowNum+"\].calSymbol\">"+
					"<option>--请选择--</option>"+
					"<option value=\"+\" selected=\"selected\">+</option>"+
					"<option value=\"-\">-</option>"+
					"<option value=\"*\">*</option>"+
					"<option value=\"/\">/</option>"+
					"</select><span class=\"Validform_checktip\"></span>"+
					"<label class=\"Validform_label\" style=\"display: none;\">计算符号</label></td>";
			//}
			
			
			
			
			if(tr) {
				if(rowNum % 4 != 0) {
					
					$(tr[tr.length-1]).append(thtml);
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