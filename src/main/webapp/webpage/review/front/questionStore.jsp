<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>题库选择-北京心悦健康心理测评系统</title>
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
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
		<div id="maincontent">
			<h1 class="tikutitle"><img src="<%=path%>/plug-in/review/images/tiku_text.jpg" alt="题库选择" /></h1>
			<div class="tikuselectwrap">
				<c:forEach items="${resultMap}" var="item">
					<div>${item.key}</div>
					<c:forEach items="${item.value}" var="reviewType">
						<p style=" text-align: start">
							<a href="<%=path%>/reviewFront.do?toGuide&classId=${reviewType.classId}" title="${reviewType.title }">${reviewType.title }</a>
						</p>
					</c:forEach>
				</c:forEach>
			</div>
		</div>
</div>
</body>
</html>
