<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>

	(function() {
		if (!/*@cc_on!@*/0)
			return;
		var e = "abbr,article,aside,audio,canvas,datalist,details,dialog,eventsource,figure,footer,header,hgroup,mark,menu,meter,nav,output,progress,section,time,video"
				.split(','), i = e.length;
		while (i--) {
			document.createElement(e[i])
		}
	})()
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登陆测试系统-筑心康5G+心理测评系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"> 
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" media="screen" />
<t:base type="jquery,easyui"></t:base>
<script type="text/javascript">
$(function(){

	 $("#submitbtn").on("click",function(){
			$("#myloginform").form(
					'submit',
					{
						onSubmit : function() {
							return check();
						},
						success : function(data) {
							var d = eval("("+data+")");
							if(d.result == "0") {
								alert("用户名或密码错误");
							} else if (d.result == "1") {
								window.location.href="<%=path%>/reviewFront.do?toQuestionStore";
							} else{
								alert("登录失败，请联系管理员");
							}
						}
					});
		})
});
function check() {
	var userName = $("#userName").val();
	var password = $("#password").val();

	//alert("id=" + userIdNumber+",length=" + userIdNumber.length+",re=" + userIdNumberRepeat + ",length=" + userIdNumberRepeat.length);
	
	if($.trim(userName) == "") {
		alert("用户名不能为空!");
		return false;
	}

	if($.trim(password) == "") {
		alert("密码不能为空!");
		return false;
	}
	return true;
}



</script>

</head>
<body>
<div class="w_centerwrapbox">
	<div id="logincontent">
<%--		<h1>--%>
<%--			<img src="<%=path%>/plug-in/review/images/logozxk.png" alt="" style="width: 160px; margin-left: 126%;"/>--%>
<%--		</h1>--%>
	 <form action="<%=path%>/reviewFront.do?login" method="post" id="myloginform" onsubmit="return check();">
          <p class="usename">
			<input type="text" name="userName" id="userName" placeholder="请输入您的用户名"  class="inpclass" />
          </p>
          <p class="password">
			<input type="password" name="password" id="password" placeholder="请输入密码" class="inpclass" />			
          </p>
          <p class="submitbtn">
			<input type="button" name="submitbtn" id="submitbtn" class="submitbtninput" value="登&nbsp;&nbsp;录" />
          </p>
	 </form>
	 <div class="clear"></div>

	</div>
</div>	
<script type="text/javascript">
$(function(){

	var funImage = function() {
		var screenType = window.getComputedStyle(document.body, ":after").getPropertyValue("content");
		if (screenTypeCache == "normalscreen") {
			var pic = document.getElementById("submitbtn");
			pic.src = "http://www.baidu.com/img/baidu.gif";
		}
	};
	if (window.getComputedStyle) {
		window.addEventListener("resize", funImage);	
		funImage();
	} else {
		oDemo.innerHTML = '<p>对不起，您的浏览器目前还不支持该功能！</p>';
	}
});

</script>
</body>
</html>
