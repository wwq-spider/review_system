<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
		});
	});
	

	
	$(document).ready(
					function() {
						var Current = navShow();
						$(".nav_list li").eq(0).find(".nav_link,.nav_menu")
								.css("border", "0px");
						$(".menu_bg").width($(".nav").width());
						var timer = 0;
						$(".nav_li").mouseenter(
								function(event) {
									var objTarget = event.relatedTarget;
									var triangle = $(this).find(".triangle");
									if ($(".nav_list").find("*").index(objTarget) >= 0)
										triangle.css("display", "block");
										$(this).find(".nav_menu").addClass(
												"menu_hover");
										$(this).children("a").css("color",
												"#fcd603");
										$(this).find(".nav_menu li a").css("color",
												"#222222");
										$(".menu_bg,.nav_menu").stop(true, true);
										timer = setTimeout(function() {
											triangle.css("display", "block");
											$(".menu_bg,.nav_menu").animate({
												height : "315px"
											}, 300);
										}, 500);
								})
						$(".nav_li").mouseleave(
										function(event) {
											var objTarget = event.relatedTarget;
											clearTimeout(timer);
											$(this).find(".nav_menu")
													.removeClass("menu_hover");
											$(this).find(".nav_menu li a").css(
													"color", "#666666");
											if ($(".nav_li").index($(this)) != Current) {
												$(this).children("a").css(
														"color", "#fff");
												$(this).find(".triangle").css(
														"display", "none");
											}
											if ($(".nav_list").find("*").index(
													objTarget) >= 0)
												return;
											$(".menu_bg,.nav_menu").animate({
												height : "0px"
											}, 300);
										})
						$(".nav_menu li").mouseenter(function() {
							$(this).css("background", "#fbfbfb");
							$(this).find("a").css("color", "#fc6103");
						})
						$(".nav_menu li").mouseleave(function() {
							$(this).css("background", "none");
							$(this).find("a").css("color", "#5e5e5e");
						})
						$(window).resize(function() {
							$(".menu_bg").width($(".nav").width());
						})
						/****褰撳墠鑿滃崟瀵艰埅楂樹寒鏄剧ず****/
						function navShow() {
							var currentUrl = document.location.href;
							var nav = $(".nav_li");
							var aHref = [];
							for ( var i = 0; i < nav.length; i++) {
								aHref = nav.eq(i).find("a");
								for ( var j = 0; j < aHref.length; j++) {
									if (currentUrl.indexOf(aHref.eq(j).attr(
											"href")) != -1) {

										nav.eq(i).find(".triangle").css( 
												"display", "block");
										nav.eq(i).children("a").css("color",
												"#ffffff");
										return i;
									}
								}
							}
						}
					});
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveDepart">
	<input name="id" type="hidden" value="${depart.id }">
	<fieldset class="step">
	<div class="form"><label class="Validform_label"> 部门名称: </label> <input name="departname" class="inputxt" value="${depart.departname }" validType="t_s_depart,departname,id" datatype="s3-10">
	<span class="Validform_checktip">部门名称在3~10位字符</span></div>
	<div class="form"><label class="Validform_label"> 职能描述: </label> <input name="description" class="inputxt" value="${depart.description }"></div>
	<div class="form"><label class="Validform_label"> 上级部门: </label> <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.departname}"></div>
	</fieldset>
</t:formvalid>
</body>
</html>
