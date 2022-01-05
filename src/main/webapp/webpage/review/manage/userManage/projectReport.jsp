<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/jquery/jquery.jqprint.js"></script>
<style media="print">
	@page{mso-page-border-surround-header:no;
		mso-page-border-surround-footer:no;
	}
	@page Section0{
		margin-top:72.0000pt;
		margin-bottom:72.0000pt;
		margin-left:90.0000pt;
		margin-right:90.0000pt;
		size:595.3000pt 841.9000pt;
		layout-grid:15.6000pt;
	}
	div.Section0{page:Section0;}

	@page {
		size: auto;  /* auto is the initial value */
		margin-top: 0mm; /* this affects the margin in the printer settings */
		margin-bottom: 0mm; /* this affects the margin in the printer settings */
	}
</style>
<script>

	function executePrint(){
		// console.log("执行打印")
		// var oldHtml = $("body", iframe.document).html();
		// var printbox = $("#printBody", iframe.document).html();
		// $("body", iframe.document).innerHTML = printbox;
		// iframe.print();
		// $("body", iframe.document).innerHTML = oldHtml;
		$("#printBody").jqprint()
	}
</script>
<%--<div class="easyui-layout" fit="true" style="background-color: #fff">--%>
	<div region="center" style="padding: 1px;" style="background-color: #fff" id="printBody">
<%--		<div class="Section0" style="layout-grid:15.6000pt;">--%>
<%--			--%>
<%--		</div>--%>
	<c:forEach items="projectResultList" var="projectResult">
		<table class="MsoTableGrid" border="1" cellspacing="0" style="border-collapse:collapse; width: 100%;">
			<tbody>
			<tr>
				<td colspan="3"><h3 style="text-align: center;">军事职业适应能力评估报告</h3></td>
			</tr>
			<tr>
				<td colspan="3"><p>姓名：${reviewResult.realName}</p></td>
			</tr>
			<tr>
				<td colspan="3"><p>身份证号：${reviewResult.idCard}</p></td>
			</tr>
			<tr>
				<td colspan="3"><p>军事职业适应能力总分：${reviewResult.levelGrade}</p></td>
			</tr>
			<tr>
				<td colspan="3"><p>分项说明（包括职业兴趣类型、数字搜索测验、空间认知能力测验）</p></td>
			</tr>
			<tr>
				<td style="text-align: center; width: 20%;">项目</td>
				<td style="text-align: center; width: 30%;">评估结果</td>
				<td style="text-align: center; width: 50%;">结果说明</td>
			</tr>
			<c:forEach items="projectResult.resultList" var="reviewResult">
				<tr>
					<td>${reviewResult.className}</td>
					<td>${reviewResult.reportResult}</td>
					<td>${reviewResult.combineVarResult}</td>
				</tr>
			</c:forEach>
			<%--<tr>
				<td>数字搜索测验</td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>图形辨别测验</td>
				<td></td>
				<td></td>
			</tr>--%>
			<tr>
				<td colspan="3"><p>总结论：${projectResult.allCompletion}</p></td>
			</tr>
			<tr>
				<td colspan="3"><p>建议：${projectResult.suggestion}</p></td>
			</tr>
			</tbody>
		</table>
		<div style="page-break-after:always;"></div>
	</c:forEach>
	</div>
	<style>
		table tr td div {
			text-align: center !important;
		}
	</style>
</div>
<%--</div>--%>
