<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开始测评</title>
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/style.css" type="text/css" />
<link rel="stylesheet" rev="stylesheet" href="<%=path%>/plug-in/review/css/jqtransform.css" type="text/css" />
<script type="text/javascript" src="<%=path%>/plug-in/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/plug-in/review/js/jquery.jqtransform.js" ></script>
<script language="javascript">

	let rootPath = "<%=path%>"

	let randomArr = []
	let lackValMap = {}
	let randomTimer
	let tmpTimeout1
	let tmpTimeout2
	let end = false
	/**
	 * 初始化信息
	 **/
	$(function(){
		$(window).resize(function(){
			$('.w_centerwrapbox').css({
				position:'absolute',
				left: ($(document).width())/2,
				top: ($(document).height())/2
			});
		});
		$(window).resize();
		$(window).bind("popstate", function() {});
		$("#dynamicNumType").hide()

		let questionType = $("#questionType").val()
		if(questionType == "3") { //动态数字题型
			//开始倒计时
			tmpTimeout1 = secondsDown(106, "secondNum1")
			setRandom()
			tmpTimeout2 = secondsDown(15, "reviewBtn")
			$("#singleSelType").hide()
			$("#dynamicNumType").show()
		} else if(questionType == "1") {

			let isLastQuestion = $("#isLastQuestion").val();
			if (isLastQuestion == 'Y') { //提交按钮
				$("#btnImg").attr("src", rootPath + "/plug-in/review/images/submit.jpg");
			} else {
				$("#btnImg").attr("src",  rootPath + "/plug-in/review/images/nextbtn.jpg");
			}
			$("#singleSelType").show()
		}
	});

	/**
	 * 设置随机数
	 * */
	function setRandom() {
		let random = geneRandom()
		randomArr.push(random)
		$("#dynamicNum").html(random.nums)
	}

	/**
	 * 设置15秒自动切换定时器
	 * */
	function setRandomInterval() {
		randomTimer = setInterval(function (){
			let curNums = $("#dynamicNum").text()
			if (!lackValMap[curNums]) {
				lackValMap[curNums] = 0
			}
			setRandom()
		}, 15000)
	}

	/**
	 * 倒计时
	 ***/
	function secondsDown(t, domid){
		if(t > 0){
			if (domid == "reviewBtn") {
				$("#" + domid).text("下一组(" + t + ")")
			} else {
				$("#" + domid).html("倒计时：" + t)
			}
			t = t - 1
			return setTimeout(function(){
				if (domid == "reviewBtn") {
					tmpTimeout2 = secondsDown(t, domid)
				} else {
					tmpTimeout1 = secondsDown(t, domid)
				}
			},1000)
		} else {
			if (domid == "reviewBtn") {
				$("#" + domid).text("下一题")
				clearTimeout(tmpTimeout2)
				let curNums = $("#dynamicNum").text()
				if (!lackValMap[curNums]) {
					lackValMap[curNums] = 0
				}
				setRandom()
				tmpTimeout2 = secondsDown(15, domid)
			} else {
				$("#" + domid).html("倒计时结束!")
				end = true
				//清除题目倒计时定时器
				clearTimeout(tmpTimeout1)
				clearTimeout(tmpTimeout2)
				//清除随机计时器
				clearInterval(randomTimer)
				console.log(JSON.stringify(lackValMap))
				let grade =  0
				for (let i=0; i < randomArr.length; i++) {
					if(lackValMap[randomArr[i].nums] > 0) {
						grade++
					}
				}
				$("#selectGrade").val(grade)
				//提交数据
				submitData()
			}
		}
	}

	/**
	 * 进入下一题
	 */
	function nextQuestion() {
		let questionType = $("#questionType").val()
		if(questionType == "1") { //单选
			singleSelectNext()
		} else if(questionType == "3") { //动态数字
			let rightNum = $("#rightNum").val()
			if(!rightNum || rightNum == "") {
				alert("请输入缺失数字")
				return;
			}
			//清空数字
			$("#rightNum").val("")
			let curNums = $("#dynamicNum").text()
			lackValMap[curNums] = parseInt(rightNum)
			//清除题目倒计时定时器
			clearTimeout(tmpTimeout2)
			//清除随机计时器
			clearInterval(randomTimer)
			if (end) {
				$("#questionContent").html("测评完成，提交中......");
			} else {
				//更新数字
				setRandom()
				//15秒倒计时开始
				secondsDown(15, "reviewBtn")
			}
		}
	}

	/**
	 * 单选下一题处理
	 * */
	function singleSelectNext() {
		let answerSelect = $("input[name='answerSelect']:checked").val();
		if(answerSelect == "" || typeof(answerSelect) == "undefined") {
			alert("请选择您的答案后再继续答题");
			return;
		}
		$("#selectGrade").val(answerSelect)
		let isLastQuestion = $("#isLastQuestion").val();
		$("#questionContent").empty();

		if(isLastQuestion == 'Y') {//the exam has completed
			//提交数据
			submitData()
		} else {
			$("#questionContent").html("  ……加载中…… ");
			$("#completeForm").submit();
		}
	}

	/**
	 * 提交数据
	 **/
	function submitData() {
		let questionObj = getFormObj()
		$("#questionContent").html("  ……提交中…… ")
		$.ajax({
			url : rootPath + "/reviewFront.do?complete",
			async : false,
			data : questionObj,
			type : 'POST',
			dataType : "json",
			success : function(data) {
				if (data) {
					if(data.result == 1) { //提交成功 进入下一个测评量表
						let nextClassId = data.nextClassId
						if (nextClassId && nextClassId != "") {
							alert("提交成功，即将进入下一个测评量表")
							window.location.href = rootPath + "/reviewFront.do?toGuide&classId=" + nextClassId
						} else {
							alert("您已完成全部测评项目，即将返回首页");
							window.location.href = rootPath + "/reviewFront.do?toQuestionStore"
						}
					} else {
						alert(data.msg)
					}
				} else {
					alert("提交失败")
				}
			},
			Error : function(xhr, error, exception) {
				// handle the error.
				alert(exception.toString())
			}
		})
	}

	/**
	 *获取form表单数据
	 * */
	function getFormObj() {
		let questionObj = {}
		let value = $('#completeForm').serializeArray()
		$.each(value, function (index, item) {
			questionObj[item.name] = item.value
		})
		return questionObj
	}

	/**
	 * 生成8位随机数
	 */
	function geneRandom() {
		let result = {}
		let numbers = [1,2,3,4,5,6,7,8,9]
		let nums
		for (let i=0; i < 8; i++) {
			let r = Math.floor(Math.random() * numbers.length)
			if (nums) {
				nums += " " + numbers[r]
			} else {
				nums = numbers[r]
			}
			numbers.splice(r, 1)
		}
		result["nums"] = nums
		result["lackNum"] = numbers[0]
		return result
	}
</script>

<style type="text/css">
	#reviewBtn {
		background-color: #008CBA; /* 蓝色 */
		border: none;
		color: white;
		padding: 10px 35px;
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
	<div id="maincontent_testpage">
		<div style="padding-left: 10px; padding-right: 10px; font-size: 20px; color: #1e4063; line-height: 28px;">
			${question.questionNum}&nbsp;.&nbsp;${question.content}&nbsp;
				<c:if test="${question.isAttach=='Y' }">
					<img alt="图片" id="previewc" width="70px" height="70px" src="<%=path%>/review.do?previewImg&questionId=${question.questionId}"/>
				</c:if>
		</div>
		<form action="<%=path %>/reviewFront.do?nextQuestion" id="completeForm" name="completeForm" method="post">
			<div id="testpage_c">
				<div id="zhidaoyu_c_bg">
					<div id="questionContent">
						<div id="singleSelType" class="test_checkitemwrap" <c:if test="${question.isAttach=='Y'}">style="height:280px;width:500px;overflow-y:auto"</c:if>>
							<c:forEach items="${question.selectList}" var="select">
								<p><input type="radio" value="${select.selectGrade }" name="answerSelect"> ${select.selCode}.&nbsp;${select.selectContent }</p>
								<c:if test="${select.pictureAttach != null && select.pictureAttach != ''}">
									<p style="background:url() no-repeat 0 0"><img alt="图片" id="preview" width="70px" height="70px" src="${aliyunOssHost}${select.pictureAttach}"/></p>
								</c:if>
							</c:forEach>
						</div>
						<div id="dynamicNumType" style="margin-top: 40px;">
							<span id="dynamicNum" style="font-size: 20px; color: #0F3A56;"></span>
							<div style="margin-top: 15px;">
								<input type="text" id="rightNum" autocomplete="off" name="rightNum" style="width: 260px; height: 30px; border-radius: 8px; border: none; box-shadow: 2px 2px 6px grey;" placeholder="请输入缺失数字"/>
							</div>
						</div>
					</div>
				</div>
				<div>
					<button type="button" onclick="nextQuestion()" id="reviewBtn">下一题</button>
					<div id="secondNum1" style="font-size: 20px; color: #e96666; margin-top: 20px;"></div>
				</div>
			</div>
			<input type="hidden" id="questionId" name="questionId" value="${question.questionId }" />
			<input type="hidden" id="questionType" name="questionType" value="${question.questionType }" />
			<input type="hidden" id="selectGrade" name="selectGrade" />
			<input type="hidden" id="classId" name="classId" value="${question.classId }" />
			<input type="hidden" id="nextClassId" name="nextClassId" value="${question.nextClassId }" />
			<input type="hidden" id="variateId" name="variateId" value="${question.variateId }"/>
			<input type="hidden" id="questionNum" name="questionNum" value="${question.questionNum }"/>
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