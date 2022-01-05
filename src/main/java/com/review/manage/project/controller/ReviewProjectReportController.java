package com.review.manage.project.controller;

import com.review.manage.project.entity.ReviewProjectReportEntity;
import com.review.manage.project.service.ReviewProjectReportServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**   
 * @Title: Controller
 * @Description: 测评项目报告
 * @author zhangdaihao
 * @date 2022-01-02 19:50:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewProjectReport")
public class ReviewProjectReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewProjectReportController.class);

	@Autowired
	private ReviewProjectReportServiceI reviewProjectReportService;
	@Autowired
	private SystemService systemService;


	/**
	 * 测评项目报告列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "reviewProjectReport")
	public ModelAndView reviewProjectReport(HttpServletRequest request) {
		return new ModelAndView("review/manage/review.manage/reviewProjectReportList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(ReviewProjectReportEntity reviewProjectReport, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewProjectReportEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewProjectReport, request.getParameterMap());
		this.reviewProjectReportService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除测评项目报告
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewProjectReportEntity reviewProjectReport, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "测评项目报告删除成功";
		reviewProjectReportService.deleteEntityById(ReviewProjectReportEntity.class, reviewProjectReport.getId());
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加测评项目报告
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewProjectReportEntity reviewProjectReport, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "";
		if (StringUtil.isNotEmpty(reviewProjectReport.getId())) {
			message = "测评项目报告更新成功";
			ReviewProjectReportEntity t = reviewProjectReportService.get(ReviewProjectReportEntity.class, reviewProjectReport.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(reviewProjectReport, t);
				reviewProjectReportService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error("save error, ", e);
				message = "测评项目报告更新失败";
			}
		} else {
			message = "测评项目报告添加成功";
			reviewProjectReportService.save(reviewProjectReport);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 测评项目报告列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewProjectReportEntity reviewProjectReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewProjectReport.getId())) {
			reviewProjectReport = reviewProjectReportService.getEntity(ReviewProjectReportEntity.class, reviewProjectReport.getId());
			req.setAttribute("reviewProjectReport", reviewProjectReport);
		}
		return new ModelAndView("review/manage/review.manage/reviewProjectReport");
	}
}
