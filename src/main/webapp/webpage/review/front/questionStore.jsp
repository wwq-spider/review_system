<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>题库选择-筑心康5G+心理测评系统</title>
	<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" />
	<style type="text/css">
		.reviewBtn {
			background-color: #008CBA; /* 蓝色 */
			border: none;
			color: white;
			text-align: center;
			text-decoration: none;
			display: inline-block;
			font-size: 16px;
			margin: 4px 2px;
			cursor: pointer;
			/*border-radius: 10px;*/
			border: none;
			/*box-shadow: 5px 5px 10px grey;*/
			background-color:#1aa0d4;
			box-shadow: 0;
			border-radius:0;
			padding: 10px 60px;

		}

		.finished {

			width: 8px;

			height: 16px;

			border-color: #009933;

			border-style: solid;

			border-width: 0 3px 5px 0;

			transform: rotate(45deg);
		}
	</style>
	<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">

		$(function(){
			$(window).resize(function(){
				$('.w_centerwrapbox').css({
					// position:'absolute',
					height: $(document).width() - 200,
					//width: $(document).height() - 5
				});
			});
			$(window).resize();
			/*let finishCount = parseInt($("#finishCount").val())
			let totalCount = parseInt($("#totalCount").val())

			if(finishCount > 0) {
				$("#reviewBtn").text("继续测评")
			} else {
				$("#reviewBtn").text("开始测评")
			}
			if(finishCount == totalCount) {
				$("#finishSpan").show()
				$("#reviewBtn").attr("disabled", "true")
				$("#reviewBtn").hide()
			} else {
				$("#finishSpan").hide()
				$("#reviewBtn").removeAttr("disable")
				$("#reviewBtn").show()
			}*/
		});

		let rootPath = "<%=path %>"

		//开始测评
		function beginTest(projectId) {
			/*let finishCount = parseInt($("#finishCount").val())
			let totalCount = parseInt($("#totalCount").val())
			if(finishCount >= totalCount) {
				alert("您已完成全部测评项目")
				return
			}*/
			let curReviewClassId = window.localStorage.getItem("curReviewClassId")

			if (!curReviewClassId || curReviewClassId == "") {
				$("#reviewBtn_"+projectId).attr("disabled", "true")
				$.ajax({
					url: rootPath + "/reviewFront/getReviewClass.do",
					async : false,
					type : 'POST',
					dataType : "json",
					data: JSON.stringify({"projectId": parseInt(projectId)}),
					contentType : 'application/json',
					success : function(data) {
						$("#reviewBtn_"+projectId).removeAttr("disabled")
						if (data) {
							if(data.code == 200) {
								let rows = data.rows
								if (rows.length > 0) {
									window.location.href = rootPath + "/reviewFront.do?toGuide&classId=" + rows[0].classId + "&projectId=" + projectId
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
						$("#reviewBtn_"+projectId).removeAttr("disable")
						// handle the error.
						alert(exception.toString());
					}
				})
			} else {
				window.location.href = rootPath + "/reviewFront.do?toGuide&classId=" + curReviewClassId + "&projectId=" + projectId
			}
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
		<%--<h1 class="tikutitle"><img src="<%=path%>/plug-in/review/images/tiku_text.jpg" alt="题库选择" /></h1>--%>
		<div style="float: right; font-size: 20px; margin-right: 10px;">
			欢迎<span style="padding: 5px;">${userName}</span>进入测评，
			<a href="javascript:void(0);" onclick="javascript:exit()" style="color: #008CBA; text-decoration: underline;">退出</a>
		</div>
		<div class="tikuselectwrap">
			<c:forEach items="${resultMap}" var="item">
				<div style="font-size: 20px; color: #3a3c39">${fn:substringAfter(item.key, "_")}</div>
				<table cellpadding="3" cellspacing="0" style="/*width: 60%;*/ margin:auto; margin-top: 5%; margin-bottom: 4%;">
					<tbody>
				<c:forEach items="${item.value}" var="reviewType">
					<tr style="font-size: 17px;">
						<td style="text-align: start; padding: 10px; color: #1e4063;">
								${reviewType.title }
						</td>
						<td style="text-align: start; padding: 10px;">
							<c:choose>
								<c:when test="${reviewType.reviewTimes > 0}"><span style="color: #70c677">已完成${reviewType.reviewTimes}次</span></c:when>
								<c:otherwise><span style="color: #e96666">待测评</span></c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
					</tbody>
				</table>
				<div style="margin-bottom: 50px;">
					<button class="reviewBtn" onclick="beginTest(${fn:substringBefore(item.key, "_")})" id="reviewBtn_${fn:substringBefore(item.key, "_")}">开始测评</button>
					<%--<div id="finishSpan">
						<div class="finished" style="margin-left: 50%; margin-bottom: 20px;"></div>
						<div style="display: none; font-size: 20px; color: #1dc116; display: inline;">您已完成本次所有量表测评！</div>
					</div>--%>
				</div>
			</c:forEach>

		</div>
	</div>
	<input type="hidden" id="finishCount" name="finishCount" value="${finishCount }" />
	<input type="hidden" id="totalCount" name="totalCount" value="${totalCount }" />
</div>
</body>
</html>
