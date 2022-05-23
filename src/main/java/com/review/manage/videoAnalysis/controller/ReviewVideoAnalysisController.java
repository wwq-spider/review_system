package com.review.manage.videoAnalysis.controller;

import com.review.manage.videoAnalysis.entity.ReviewVideoAnalysisEntity;
import com.review.manage.videoAnalysis.service.ReviewVideoAnalysisServiceI;
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
 * @Description: 视频分析记录
 * @author zhangdaihao
 * @date 2022-02-10 16:56:25
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewVideoAnalysis")
public class ReviewVideoAnalysisController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewVideoAnalysisController.class);

	@Autowired
	private ReviewVideoAnalysisServiceI reviewVideoAnalysisService;
	@Autowired
	private SystemService systemService;


	/**
	 * 视频分析记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "reviewVideoAnalysis")
	public ModelAndView reviewVideoAnalysis(HttpServletRequest request) {
		return new ModelAndView("review/manage/review.manage/reviewVideoAnalysisList");
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
	public void datagrid(ReviewVideoAnalysisEntity reviewVideoAnalysis, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewVideoAnalysisEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewVideoAnalysis, request.getParameterMap());
		this.reviewVideoAnalysisService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除视频分析记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewVideoAnalysisEntity reviewVideoAnalysis, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "视频分析记录删除成功";
		reviewVideoAnalysisService.deleteEntityById(ReviewVideoAnalysisEntity.class, reviewVideoAnalysis.getId());
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加视频分析记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewVideoAnalysisEntity reviewVideoAnalysis, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "";
		if (StringUtil.isNotEmpty(reviewVideoAnalysis.getId())) {
			message = "视频分析记录更新成功";
			ReviewVideoAnalysisEntity t = reviewVideoAnalysisService.get(ReviewVideoAnalysisEntity.class, reviewVideoAnalysis.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(reviewVideoAnalysis, t);
				reviewVideoAnalysisService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error("save error, ", e);
				message = "视频分析记录更新失败";
			}
		} else {
			message = "视频分析记录添加成功";
			reviewVideoAnalysisService.save(reviewVideoAnalysis);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 视频分析记录列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewVideoAnalysisEntity reviewVideoAnalysis, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewVideoAnalysis.getId())) {
			reviewVideoAnalysis = reviewVideoAnalysisService.getEntity(ReviewVideoAnalysisEntity.class, reviewVideoAnalysis.getId());
			req.setAttribute("reviewVideoAnalysis", reviewVideoAnalysis);
		}
		return new ModelAndView("review/manage/review.manage/reviewVideoAnalysis");
	}
}
