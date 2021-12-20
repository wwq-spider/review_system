package com.review.manage.record.controller;

import com.review.manage.record.entity.ReviewRecordEntity;
import com.review.manage.record.service.ReviewRecordServiceI;
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
 * @Description: 量表测评记录
 * @author zhangdaihao
 * @date 2021-12-18 15:21:29
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewRecord")
public class ReviewRecordController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewRecordController.class);

	@Autowired
	private ReviewRecordServiceI reviewRecordService;
	@Autowired
	private SystemService systemService;


	/**
	 * 量表测评记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "reviewRecord")
	public ModelAndView reviewRecord(HttpServletRequest request) {
		return new ModelAndView("review/manage/review.manage/reviewRecordList");
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
	public void datagrid(ReviewRecordEntity reviewRecord, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewRecordEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewRecord, request.getParameterMap());
		this.reviewRecordService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除量表测评记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewRecordEntity reviewRecord, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "量表测评记录删除成功";
		reviewRecordService.deleteEntityById(ReviewRecordEntity.class, reviewRecord.getId());
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加量表测评记录
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewRecordEntity reviewRecord, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "";
		if (StringUtil.isNotEmpty(reviewRecord.getId())) {
			message = "量表测评记录更新成功";
			ReviewRecordEntity t = reviewRecordService.get(ReviewRecordEntity.class, reviewRecord.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(reviewRecord, t);
				reviewRecordService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error("save error, ", e);
				message = "量表测评记录更新失败";
			}
		} else {
			message = "量表测评记录添加成功";
			reviewRecordService.save(reviewRecord);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 量表测评记录列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewRecordEntity reviewRecord, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewRecord.getId())) {
			reviewRecord = reviewRecordService.getEntity(ReviewRecordEntity.class, reviewRecord.getId());
			req.setAttribute("reviewRecord", reviewRecord);
		}
		return new ModelAndView("review/manage/review.manage/reviewRecord");
	}
}
