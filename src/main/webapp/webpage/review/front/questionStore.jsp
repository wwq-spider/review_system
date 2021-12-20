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
	let finishCount = parseInt($("#finishCount").val())
	let totalCount = parseInt($("#totalCount").val())

	if(finishCount > 0) {
		$("#reviewBtn").text("继续测评")
	} else {
		$("#reviewBtn").text("开始测评")
	}
	if(finishCount == totalCount) {
		$("#finishSpan").show()
		$("#reviewBtn").attr("disabled", "true")
	}
});

let rootPath = "<%=path %>"

//开始测评
function beginTest() {
	let finishCount = parseInt($("#finishCount").val())
	let totalCount = parseInt($("#totalCount").val())
	if(finishCount >= totalCount) {
		alert("您已完成全部测评项目")
		return
	}
	$("#reviewBtn").attr("disabled", "true")
	$.ajax({
		url: rootPath + "/reviewFront/getCurReviewId.do",
		async : false,
		type : 'POST',
		dataType : "json",
		success : function(data) {
			$("#reviewBtn").removeAttr("disabled")
			if (data) {
				if(data.code == 200) {
					let reviewId = data.reviewId
					if (reviewId && reviewId != "") {
						window.location.href = rootPath + "/reviewFront.do?toGuide&classId=" + reviewId
					} else {
						alert("无待测评量表")
					}
				} else {
					alert(data.msg)
				}
			} else {
				alert("提交失败")
			}
		},
		Error : function(xhr, error, exception) {
			$("#reviewBtn").removeAttr("disable")
			// handle the error.
			alert(exception.toString());
		}
	})
}

//开始测评
function exit() {
	if(confirm("您确认退出登陆吗？")) {
		$.ajax({
			url: rootPath + "/reviewFront.do?loginOut",
			async : false,
			type : 'POST',
			dataType : "json",
			success : function(data) {
				if (data) {
					if(data.code == 200) {
						window.location.href = rootPath + "/reviewFront.do?toReviewLogin"
					} else {
						alert(data.msg)
					}
				} else {
					alert("退出失败")
				}
			},
			Error : function(xhr, error, exception) {
				// handle the error.
				alert(exception.toString());
			}
		})
	}
}
</script>
</head>
<body>
<div class="w_centerwrapbox">
		<div id="maincontent">
			<h1 class="tikutitle"><img src="<%=path%>/plug-in/review/images/tiku_text.jpg" alt="题库选择" /></h1>
			<div style="float: right;">
				欢迎${userName}进入测评，<a href="javascript:void(0);" onclick="javascript:exit()">退出</a>
			</div>
			<div class="tikuselectwrap">
				<c:forEach items="${resultMap}" var="item">
					<div>${fn:substringAfter(item.key, "_")}</div>
					<c:forEach items="${item.value}" var="reviewType">
						<p style=" text-align: start">
							<a href="<%=path%>/reviewFront.do?toGuide&classId=${reviewType.classId}" title="${reviewType.title }">${reviewType.title }</a>
						</p>
						<p>
							<c:choose>
								<c:when test="${reviewType.reviewTimes > 0}">已完成</c:when>
								<c:otherwise>待测评</c:otherwise>
							</c:choose>
						</p>
					</c:forEach>
				</c:forEach>
				<div>
					<button onclick="beginTest()" id="reviewBtn"></button>
					<span style="display: none" id="finishSpan">您已完成全部测评</span>
				</div>
			</div>
		</div>
	<input type="hidden" id="finishCount" name="finishCount" value="${finishCount }" />
	<input type="hidden" id="totalCount" name="totalCount" value="${totalCount }" />
</div>
</body>
</html>
