package com.review.manage.expert.controller;

import com.review.common.CommonUtils;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**   
 * @Title: Controller
 * @Description: 测评专家模块
 * @author zhangdaihao
 * @date 2021-10-21 11:38:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewExpertController")
public class ReviewExpertController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewExpertController.class);

	@Autowired
	private ReviewExpertServiceI reviewExpertService;
	@Autowired
	private SystemService systemService;


	/**
	 * 测评专家模块列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "reviewExpert")
	public ModelAndView reviewExpert(HttpServletRequest request) {
		return new ModelAndView("review/manage/expert/reviewExpertList");
	}

	/**
	 * easyui AJAX请求数据
	 * @param reviewExpert
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(ReviewExpertEntity reviewExpert, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewExpertEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewExpert, request.getParameterMap());
		this.reviewExpertService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除测评专家模块
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewExpertEntity reviewExpert, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		reviewExpert = systemService.getEntity(ReviewExpertEntity.class, reviewExpert.getId());
		String message = "测评专家模块删除成功";
		reviewExpertService.delete(reviewExpert);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加测评专家模块
	 *
	 * @param reviewExpert
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewExpertVO reviewExpert) {
		AjaxJson j = new AjaxJson();
		String message = "";
		if (StringUtil.isNotEmpty(reviewExpert.getId())) {
			message = "测评专家更新成功";
			try {
				reviewExpertService.updateExpert(reviewExpert);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error("save Expert error,", e);
				message = "测评专家更新失败";
			}
		} else {
			message = "测评专家添加成功";
			reviewExpert.setCreator(ContextHolderUtils.getLoginUserName());
			reviewExpertService.addExpert(reviewExpert);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 测评专家模块列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewExpertEntity reviewExpert, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewExpert.getId())) {
			reviewExpert = reviewExpertService.getEntity(ReviewExpertEntity.class, reviewExpert.getId());
			req.setAttribute("reviewExpertPage", reviewExpert);
		}
		return new ModelAndView("review/manage/expert/reviewExpert");
	}

	/**
	 * 新增/修改日历
	 * @param response
	 * @param expertCalendar
	 * @return
	 */
	@RequestMapping(params = "addOrUpdCalendar")
	@ResponseBody
	public AjaxJson addOrUpdCalendar(HttpServletResponse response, ReviewExpertCalendarVO expertCalendar) {
		JSONObject json = new JSONObject();

		ReviewExpertEntity reviewExpertEntity = reviewExpertService.get(ReviewExpertEntity.class, expertCalendar.getId());
		json.put("code", 200);
		json.put("result", reviewExpertEntity);
		json.put("msg", "查询成功");
		CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);

		AjaxJson j = new AjaxJson();
		boolean flag = reviewExpertService.createOrUpdCalendar(expertCalendar);
		String message = "日历保存成功";
		j.setSuccess(flag);
		if (!flag) {
			message = "日历保存失败，请检查开始、结束时间";
		}
		systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}

	/**
	 * 日历设置
	 * @param expertCalendar
	 * @return
	 */
	@RequestMapping(params = "toCalendarSet")
	public ModelAndView toCalendarSet(ReviewExpertCalendarVO expertCalendar) {
		ModelAndView modelAndView = new ModelAndView("review/manage/expert/expertCalendar");
		modelAndView.addObject("expertCalendar", expertCalendar);
		return modelAndView;
	}

	/**
	 * 查询专家日历
	 * @param response
	 * @param reviewExpertCalendar
	 */
	@RequestMapping(params = "listCalendar")
	@ResponseBody
	public void listCalendar(HttpServletResponse response, ReviewExpertCalendarVO reviewExpertCalendar) {
		responseRowsJson(response, 200, reviewExpertService.getReviewExpertCalendars(reviewExpertCalendar));
	}
}
