<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>测试结果-测评系统</title>
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/review/js/jquery.jqtransform.js"></script>
<script language="javascript">
$(function(){
	$(window).resize(function(){
		  $('.w_centerwrapbox').css({
			position:'absolute',
			left: ($(document).width())/2,
			top: ($(document).height())/2
		  });
	 });
	$(window).resize();

});

</script>
</head>
<body>
<div class="w_centerwrapbox">
	<div id="logincontent">
		<h6>
			<img src="<%=path%>/plug-in/review/images/test_result_text.jpg" alt="" />
		</h6>
		<div class="test_checkitemwrap" style="height:310px;width:500px;overflow-y:auto">

				<table cellpadding="0" style="border-color:#e9ecf1" border="1" align="center" cellspacing="1">
				<thead><tr >
								<td align="center" style="width:20%">&nbsp;&nbsp;名称</td>
								<td align="center" style="width:80%">结果</td>
							</tr></thead>
							<tbody>
				<c:forEach items="${resultList }" var="result">
							<tr>
								<td align="center" style="width:20%">${result.reportName }</td>
								<td align="left" style="width:80%">${result.grade }分&nbsp;&nbsp;${result.explainResult }</td>
							</tr>
							<!--<span><p style="font-size: 20">${result.reportName }：${result.explainResult }</p></span>-->

				</c:forEach>
				</tbody>
				</table>
		</div>
	</div>
</div>	
</body>
</html>
