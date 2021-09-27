<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>年龄男女-北京心悦健康心理测评系统</title>
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" />
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/jqtransform.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/review/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=path%>/plug-in/review/js/jquery.jqtransform.js" ></script>
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
		$(function(){
			$('form').jqTransform({imgPath:'images/'});
		});
		function check() {
			var username = $("#username").val();
			var age = $("#age").val();

			//alert("id=" + userIdNumber+",length=" + userIdNumber.length+",re=" + userIdNumberRepeat + ",length=" + userIdNumberRepeat.length);
			
			if(username == "" || username == null || username.length == 0 || username=="请输入您的姓名") {
				alert("请输入您的姓名");
				return false;
			}
			if(age == "" || age == null || age.length == 0 || age == "请输入您的年龄" || isNaN(age)) {
				alert("请输入您的年龄");
				return false;
			}

			return true;
		}
	</script>
</head>
<body>
<div class="w_centerwrapbox">
	<div id="logincontent">
		<h1><img src="<%=path%>/plug-in/review/images/welcome_text.jpg" alt="欢迎进入北京心悦健康心理测评系统" /></h1>
	 <form action="<%=path%>/reviewFront.do?register" method="post" id="myloginform" onsubmit="return check();">
	 	  <input type="hidden" id="userIdNumber" name="userIdNumber" value="<%=request.getParameter("userIdNumber") %>"/>
	 	  <input type="hidden" id="source" name="source" value="beijing"/>
	 	  <input type="hidden" id="idCard" name="idCard" value="-1"/>
          <p class="usenamenew">
			<input type="text" name="username" id="username" value="请输入您的姓名" onfocus="if (value =='请输入您的姓名'){value =''}" onblur="if (value ==''){value='请输入您的姓名'}" class="inpclasssex" />
          </p>
          <p class="passwordnew">
			<input type="text" name="age" id="age" value="请输入您的年龄" onfocus="if (value =='请输入您的年龄'){value =''}" onblur="if (value ==''){value='请输入您的年龄'}" class="inpclasssex" />			
          </p>
		  <p class="sexp">
			<label>性别 ：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="radio" id="sexual" name="sexual" value="M" checked="checked" /><label>&nbsp;&nbsp;男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<input type="radio" id="sexual" name="sexual" value="F" /><label>&nbsp;&nbsp;女&nbsp;&nbsp;&nbsp;&nbsp; </label>
		  </p>
		  
          <p class="submitbtn">
			<input type="image" name="submitbtn" id="submitbtn" src="<%=path%>/plug-in/review/images/submit.jpg" />
          </p>
	 </form>
	 <div class="clear"></div>
	</div>
</div>

</body>
</html>
