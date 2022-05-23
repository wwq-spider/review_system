package com.review.manage.reviewClass.controller;

import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.entity.ReviewReportTemplateEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.reviewClass.service.ReviewReportTemplateServiceI;
import com.review.manage.reviewClass.vo.ReviewClassVO;
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
import java.util.List;

/**   
 * @Title: Controller
 * @Description: 测评量表报告模板
 * @author zhangdaihao
 * @date 2022-05-06 22:01:48
 * @version V1.0   ƒ
 *
 */
@Controller
@RequestMapping("/reviewReportTemplate")
public class ReviewReportTemplateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewReportTemplateController.class);

	@Autowired
	private ReviewReportTemplateServiceI reviewReportTemplateService;

	@Autowired
	private ReviewClassService reviewClassService;

	@Autowired
	private SystemService systemService;


	/**
	 * 测评量表报告模板列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "reviewReportTemplate")
	public ModelAndView reviewReportTemplate(HttpServletRequest request) {
		return new ModelAndView("review/manage/reviewClass/reviewReportTemplateList");
	}

	/**
	 * 查询列表
	 * @param reviewReportTemplate
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(ReviewReportTemplateEntity reviewReportTemplate, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewReportTemplateEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewReportTemplate, request.getParameterMap());
		this.reviewReportTemplateService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除测评量表报告模板
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewReportTemplateEntity reviewReportTemplate, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "测评量表报告模板删除成功";
		reviewReportTemplateService.deleteEntityById(ReviewReportTemplateEntity.class, reviewReportTemplate.getId());
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加测评量表报告模板
	 * @param reviewClass
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewClassVO reviewClass) {
		AjaxJson j = new AjaxJson();
		String message = "";
		try {
			reviewReportTemplateService.save(reviewClass);
			message = "保存报告模板成功";
		} catch (Exception e) {
			logger.error("save error, ", e);
			message = "保存报告模板失败";
		} finally {
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 跳转到报告模板新增页
	 * @param classId
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(String classId, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(classId)) {
			req.setAttribute("classId", classId);
			ReviewClassVO reviewClassVO = reviewReportTemplateService.getByClassId(classId);
			req.setAttribute("reportTips", reviewClassVO.getReportTips());
			req.setAttribute("reportTemplateList", reviewClassVO.getReportTemplateList());
		}
		return new ModelAndView("review/manage/reviewClass/reviewReportTemplate");
	}
}
