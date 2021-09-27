<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>测试页面-北京心悦健康心理测评系统</title>
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" />
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/jqtransform.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery-1.8.0.min.js"></script>
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
	
	$(window).bind("popstate", function() {
		  
	});
	
});

	var qrId;
	var userId;
	var multiple;

	function nextQuestion() {		
		var answerStr = "";
		var questionNum = $("#questionNum").val();
		var isLastQuestion = $("#isLastQuestion").val();
		answerStr = $("input[name='answerCheck']:checked").val();
		if(answerStr == "" || typeof(answerStr) == "undefined") {
			alert("请选择您的答案后再继续答题");
			return;
		}
		
		$("#selectGrade").val(answerStr);
		$("#questionNum").val(parseInt(questionNum)+1);
		$("#myformwrap").empty();
		if(isLastQuestion == 'Y') {//the exam has completed
			alert("您已经回答完全部问题，在您点击确定后系统会为你开始计算最后结果，感谢您的使用！");
			$("#myformwrap").html("  ……最终结果加载中…… ");
		} else {
			$("#myformwrap").html("  ……加载中…… ");
		}
		$("#completeForm").submit();
	}
</script>

</head>
<body>
<div class="w_centerwrapbox">
		<div id="maincontent_testpage">
			<form action="<%=path %>/reviewFront.do?nextQuestion" id="completeForm" name="completeForm" method="post">
				<div id="testpage_c">
					<div id="zhidaoyu_c_bg">
						<div id="myformwrap">
							<h3>${question.questionNum + 1}&nbsp;.&nbsp;${question.content}&nbsp;
							<c:if test="${question.isAttach=='Y' }">
							<img alt="图片" id="previewc" width="70px" height="70px" src="<%=path%>/review.do?previewImg&questionId=${question.questionId}"></img>
							</c:if>
							</h3>
							<div class="test_checkitemwrap" <c:if test="${question.isAttach=='Y' }">style="height:280px;width:500px;overflow-y:auto"</c:if>>
								<c:forEach items="${question.selectList}" var="select">
									<p><input type="radio" value="${select.selectGrade }" name="answerCheck" id="answerCheck">${select.selCode}.&nbsp;${select.selectContent }</p>
									<c:if test="${select.isAttach=='Y' }">
											<p style="background:url() no-repeat 0 0"><img alt="图片" id="preview" width="70px" height="70px" src="<%=path%>/review.do?previewImg&answerId=${select.selectId}"></img></p>
										</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>	
				<p id="mybtndiv">
					<a a href="javascript:void(0);" onclick="javascript:nextQuestion()" title="">
						<img src="<%=path%>/plug-in/review/images/nextbtn.jpg" />
					</a>
				</p>

				<input type="hidden" id="questionId" name="questionId" value="${question.questionId }" />
				<input type="hidden" id="classId" name="classId" value="${question.classId }" />
				<input type="hidden" id="variateId" name="variateId" value="${question.variateId }"/>

				<input type="hidden" id="exam" name="exam" value=""/>
				<input type="hidden" id="questionNum" name="questionNum" value="${question.questionNum }"/>
				<input type="hidden" id="selectGrade" name="selectGrade" />
				<input type="hidden" id="number" name="number" value=""/>
				<input type="hidden" id="questionnaireDesc" name="questionnaireDesc" value=""/>
				<input type="hidden" id="totalQuestion" name="totalQuestion" value=""/>
				<input type="hidden" id="has2rdPart" name="has2rdPart" value=""/>
				<input type="hidden" id="isLastQuestion" name="isLastQuestion" value="${question.isLastQuestion }"/>
				</form>	
		</div>
<script type="text/javascript">
$(function(){

	$(".test_checkitemwrap p").live("click",function(){
		if($(this).find("input").prop("checked") == true){
			$(this).find("input").prop("checked",false);
		}
		else{
			$(this).find("input").prop("checked",true);
		}
	});
});
</script>
</div>
</body>
</html>