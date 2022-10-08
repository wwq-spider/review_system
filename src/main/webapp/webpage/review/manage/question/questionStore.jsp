<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>题库选择-筑心康5G+心理测评系统</title>
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/review/js/jquery-1.8.0.min.js"></script>
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
				<c:for
				<p><a href="<%=path%>/plug-in/review/zhidaoyupage.jsp?qrId=4&amp;userId=<%=request.getAttribute("userId") %>" title=""><img src="<%=path%>/plug-in/review/images/cpbtn_one.jpg" alt="心悦健康心理测评" /></a></p>
				<p><a href="<%=path%>/plug-in/review/zhidaoyupage.jsp?qrId=3&amp;userId=<%=request.getAttribute("userId") %>" title=""><img src="<%=path%>/plug-in/review/images/cpbtn_two.jpg" alt="司法系统心理测评" /></a></p>
				<p><a href="<%=path%>/plug-in/review/zhidaoyupage.jsp?qrId=1&amp;userId=<%=request.getAttribute("userId") %>" title=""><img src="<%=path%>/plug-in/review/images/cpbtn_three.jpg" alt="交警系统心理测评" /></a></p>
				<p><a href="<%=path%>/plug-in/review/zhidaoyupage.jsp?qrId=2&amp;userId=<%=request.getAttribute("userId") %>" title=""><img src="<%=path%>/plug-in/review/images/cpbtn_four.jpg" alt="交警系统心理测评" /></a></p>
			</div>
		</div>

</div>
</body>
</html>
