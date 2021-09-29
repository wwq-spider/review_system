<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>二维码预览</title>
	<t:base type="jquery,easyui,tools"></t:base>
	<script type="text/javascript">

        let projectId = '${projectId}'
        let qrcodeLink = '${aliyunOssHost}${codelink}'
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
                        $("#previewc").attr("src", "${aliyunOssHost}" + data.result)
                    }else{
                        alert("二维码刷新失败")
                    }
                }
            });
        }
	</script>
</head>
<body>
	<div><img style="position: absolute; top: 10%; left: 47%; margin-left: -120px;" alt="图片" id="previewc" src=""/>
		<div  style="position: fixed;bottom: 10%;left: 44%;"><button onclick="refreshQrCode()">更新二维码</button></div>
	</div>

</body>