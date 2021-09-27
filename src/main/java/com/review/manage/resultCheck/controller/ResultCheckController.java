package com.review.manage.resultCheck.controller;

import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.review.manage.resultCheck.service.ReviewResultCheckService;

/**
 * 结果排查controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/check")
public class ResultCheckController extends BaseController{

	@Autowired
	private ReviewResultCheckService reviewResultCheckService;
}
