package com.review.manage.reviewClass.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.review.common.CommonUtils;
import com.review.manage.question.service.ReviewService;
import com.review.manage.report.service.ReportService;
import com.review.manage.report.vo.ReportVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.service.VariateService;
import com.review.manage.variate.vo.VariateVO;

@Controller
@RequestMapping("/reviewClass")
public class ReviewClassController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReviewClassService reviewClassService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private VariateService variateService;
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * 跳到分类列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toReveiwClassList")
	public ModelAndView toReveiwClassList(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("review/manage/reviewClass/reviewClassList");
		return model;
	}
	
	/**
	 * datagrid分类数据
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params="datagrid")
	public void datagrid(HttpServletResponse response, DataGrid dataGrid, ReviewClassVO reviewClass) {
		
		//查询列表数据
		List<Map<String, Object>> list = reviewClassService.getReviewClassList(reviewClass, dataGrid);
		//查询数量
		Long count = reviewClassService.getReviewClassCount(reviewClass);
		JSONObject json = new JSONObject();
		json.put("rows", list);
		json.put("total", count);
		CommonUtils.responseDatagrid(response, json);
	}
	
	/**
	 * 跳到添加或修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params="toAdd")
	public ModelAndView toAdd(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("review/manage/reviewClass/reviewClassAdd");
		String classId = request.getParameter("classId");
		
		if(!"".equals(StringUtils.trimToEmpty(classId))) {
			ReviewClassEntity reviewClass = reviewClassService.get(ReviewClassEntity.class, classId);
			model.addObject("reviewClass", reviewClass);
		}
		return model;
	}

	/**
	 * 添加或修改分类信息
	 * @param reviewClass
	 */
	@RequestMapping(params="addorupdate")
	@ResponseBody
	public AjaxJson addorupdate(ReviewClassVO reviewClass) {
		AjaxJson ajax =  new AjaxJson();
		try {
			if(reviewClass.getClassId() != null && !"".equals(reviewClass.getClassId())) {
				reviewClassService.updateReviewClass(reviewClass);
				ajax.setMsg("修改成功");
			} else {
				reviewClassService.addReviewClass(reviewClass);
				ajax.setMsg("添加成功");
			}
		} catch (Exception e) {
			ajax.setMsg("操作失败");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 删除分类
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="del")
	@ResponseBody
	public AjaxJson delQuestioin(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson ajax =  new AjaxJson();
		String classId = request.getParameter("classId");
		
		//分类下的题目数量
		Long questionCount = reviewService.getCountForJdbcParam("select COUNT(id) from review_question_class where class_id=?",
				new Object[]{classId});
		
		//分类下的报告数量
		Long reportCount = reportService.getCountForJdbcParam("select COUNT(report_id) FROM review_report where class_id=?", 
				new Object[]{classId});
		
		//分类下的因子数量
		Long variateCount = variateService.getCountForJdbcParam("select COUNT(variate_id) FROM review_variate where class_id=?", 
				new Object[]{classId});
		try {
			if(variateCount > 0) {
				ajax.setMsg("请先删除该分类下的因子!");
			} else if(reportCount > 0) {
				ajax.setMsg("请先删除该分类下的维度!");
			} else if(questionCount > 0) {
				ajax.setMsg("请先删除该分类下的题目!");
			} else {
				reviewClassService.delReviewClass(classId);
				ajax.setMsg("删除成功!");
			}
		} catch (Exception e) {
			ajax.setMsg("删除失败!");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 发布\停止分类
	 * @param request
	 */
	@RequestMapping(params="publish")
	@ResponseBody
	public AjaxJson publish(HttpServletRequest request) {
		AjaxJson ajax =  new AjaxJson();
		String classId = request.getParameter("classId");
		String pubType = request.getParameter("pubType");
		try {
			reviewClassService.publishClass(classId, pubType);
			if("0".equals(pubType)) {
				ajax.setMsg("停止成功!");
			} else if("1".equals(pubType)) {
				ajax.setMsg("发布成功!");
			}
		} catch (Exception e) {
			ajax.setMsg("操作失败!");
			e.printStackTrace();
		}
		return ajax;
	}

	/**
	 * 置为热门
	 * @param classId
	 * @param opt
	 * @return
	 */
	@RequestMapping(params="setUpHot")
	@ResponseBody
	public AjaxJson setUpHot(String classId, int opt) {
		AjaxJson ajax =  new AjaxJson();
		try {
			reviewClassService.setUpHot(classId, opt);
			ajax.setSuccess(true);
			if(opt == 2) {
				ajax.setMsg("设置热门成功!");
			} else if(opt == 1) {
				ajax.setMsg("取消热门成功!");
			} else {
				ajax.setSuccess(false);
				ajax.setMsg("非法操作!");
			}
		} catch (Exception e) {
			ajax.setMsg("操作失败!");
			logger.error("setUpHot error, ", e);
		}
		return ajax;
	}
	
	/**
	 * 跳到计分设置页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toScore")
	public ModelAndView toScore(HttpServletRequest request,
			HttpServletResponse response) {
		String classId = request.getParameter("classId");
		ModelAndView model = new ModelAndView("review/manage/variate/scoreSet");
		List<VariateVO> list = variateService.getVariateVOList(classId);
		model.addObject("variateList", list);
		model.addObject("classId", classId);
		return model;
	}
	
	/**
	 * 跳到报告设置页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toReportSet")
	public ModelAndView toReportSet(HttpServletRequest request,
			HttpServletResponse response) {
		String classId = request.getParameter("classId");
		ModelAndView model = new ModelAndView("review/manage/report/reportSet");
		List<ReviewVariateEntity> variateList = variateService.getVariateList(0, 8);
		List<ReportVO> reportList = reportService.getReportVOList(classId);
		model.addObject("variateList", variateList);
		model.addObject("reportList", reportList);
		model.addObject("classId", classId);
		return model;
	}
}
