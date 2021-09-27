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
	$("#userInfo").empty();
	$("#div1_c").empty();
	$("#div1_c").html("  ……结果加载中，请稍候…… ");
	initResult();
});

function initResult() {
	var classId = $("#classId").val();

	$.ajax({
		url : "<%=path%>/reviewFront.do?complete",
		async : false,
		data : {classId:classId},
		type : 'POST',
		dataType : "json",

		success : function(data) {
			//alert(data);
			//$("#userInfo").empty();
			//$("#div1_c").empty();

			//$("#userInfo").html("<p>" + data.username + "，您好</p>");
			//$("#div1_c").html("<p>" + data.examResult + "</p>");

			$("#downloadDiv").empty();
			$("#downloadDiv").html("<p><a href='<%=path %>/reviewFront.do?viewResult&classId=" + $("#classId").val() + "' title='查看结果'  target='_blank'><img src='<%=path %>/plug-in/review/images/showresult_btn.jpg' alt=''/></a></p>");
		},
		Error : function(xhr, error, exception) {
			// handle the error.    
			alert(exception.toString());
		}
	});
	
}
</script>
</head>
<body>
<div class="w_centerwrapbox">
	<input type="hidden" id="classId" name="classId" value="${question.classId }" />
	<div id="logincontent">
		<h6>
			<img src="<%=path%>/plug-in/review/images/test_result_text.jpg" alt="" />
		</h6>
		<div class="testendwrap">
			<div id="downloadDiv">
	
			</div>
			<p><a href="<%=path%>/reviewFront.do?toQuestionStore" title=""><img src="<%=path%>/plug-in/review/images/returndatabase.jpg" alt="" /></a></p>
			<p><a href="<%=path%>/reviewFront.do?toGuide&classId=${question.classId}" title=""><img src="<%=path%>/plug-in/review/images/endtest_bg.jpg" alt="" /></a></p>		
		</div>
	</div>
</div>	
</body>
</html>
