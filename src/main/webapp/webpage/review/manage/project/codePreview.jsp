<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%request.setAttribute("contextPath", request.getContextPath()); %>
<!DOCTYPE html>
<html>
<head>
	<title>二维码预览</title>
	<t:base type="jquery,easyui,tools"></t:base>

</head>
<body style="overflow-y: auto;overflow-x:hidden" scroll="no">
<form method="post">
	<div><span>双击更新二维码</span></div>
<img alt="图片" id="previewc" width="95%" height="95%" src="" ondblclick="refreshQrCode()"/>
</form>

<script type="text/javascript">

	let projectId = '${projectId}'
	let qrcodeLink = '${contextPath}${codelink}'
	$(function() {
		if (qrcodeLink && qrcodeLink != "") {
			$("#previewc").attr("src", qrcodeLink)
		} else {
			geneOrUpdQrCode(projectId, true)
		}
	})

	function refreshQrCode() {
		geneOrUpdQrCode(projectId, true)
	}

	function geneOrUpdQrCode(projectId, refresh) {
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			contentType : 'application/json',
			dataType:"json",
			url : "reviewProject.do?generateAppletsQrCode&projectId="+projectId+"&refresh="+refresh,// 请求的action路径
			error : function() {// 请求失败处理函数
				alert("出错了");
				frameElement.api.close();
			},
			success : function(data) {
				if (data.code == 200) {
					$("#previewc").attr("src", data.result)
				}else{
					alert("二维码刷新失败")
				}
			}
		});
	}

	function getQueryString(name){
		let reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		let r = window.location.search.substr(1).match(reg);
		if(r!=null)return  unescape(r[2]); return null;
	}
</script>
</body>