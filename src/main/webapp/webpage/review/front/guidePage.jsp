<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>指导语-北京心悦健康心理测评系统</title>
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

function begin(classId, nextClassId) {
	window.location.href = "<%=path%>/reviewFront.do?nextQuestion&classId="+ classId + "&nextClassId=" + nextClassId + "&questionNum=0"
}
</script>
<style type="text/css">
	#reviewBtn {
		background-color: #008CBA; /* 蓝色 */
		border: none;
		color: white;
		padding: 15px 36px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 16px;
		margin: 4px 2px;
		cursor: pointer;
		border-radius: 10px;
		border: none;
		box-shadow: 5px 5px 10px grey;
	}
</style>
</head>
<body>
<div class="w_centerwrapbox">
		<div id="zhidaomaincontent">
			<h1 class="tikutitle"><img src="<%=path%>/plug-in/review/images/zhidao_text.jpg" alt="指导语" /></h1>
			<div id="zhidaoyu_c">
				<div id="zhidaoyu_c_bg">
					<p style="font-size: 20px; text-align: center;">${reviewClass.title }</p>
					<p>${reviewClass.guide }</p>		
				</div>
			</div>
			<div style="text-align: center;">
				<button type="submit" onclick="begin('${classId}', '${nextClassId}')" id="reviewBtn">开始测评</button>
			</div>
		</div>
</div>
</body>
</html>
