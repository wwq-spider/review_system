package com.review.manage.notice.controller;

import com.review.common.Constants;
import com.review.manage.notice.entity.ReviewNoticeEntity;
import com.review.manage.notice.service.ReviewNoticeServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**   
 * @Title: Controller
 * @Description: 公告
 * @author zhangdaihao
 * @date 2021-11-16 15:16:45
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewNotice")
public class ReviewNoticeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewNoticeController.class);

	@Autowired
	private ReviewNoticeServiceI reviewNoticeService;
	@Autowired
	private SystemService systemService;



	/**
	 * 公告列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toList")
	public ModelAndView reviewNotice(HttpServletRequest request) {
		return new ModelAndView("review/manage/notice/reviewNoticeList");
	}

	/**
	 *  easyui AJAX请求数据
	 * @param reviewNotice
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(ReviewNoticeEntity reviewNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewNoticeEntity.class, dataGrid);
		cq.addOrder("updateTime", SortDirection.desc);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewNotice, request.getParameterMap());
		this.reviewNoticeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除公告
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewNoticeEntity reviewNotice) {
		AjaxJson j = new AjaxJson();
		String message = "公告删除成功";
		reviewNoticeService.deleteEntityById(ReviewNoticeEntity.class, reviewNotice.getId());
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加公告
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewNoticeEntity reviewNotice) {
		AjaxJson j = new AjaxJson();
		String message = "";
		if (StringUtil.isNotEmpty(reviewNotice.getId())) {
			message = "公告更新成功";
			ReviewNoticeEntity t = reviewNoticeService.get(ReviewNoticeEntity.class, reviewNotice.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(reviewNotice, t);
				t.setUpdateTime(new Date());
				t.setOperator(ResourceUtil.getSessionUserName().getUserName());
				reviewNoticeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error("save error, ", e);
				message = "公告更新失败";
			}
		} else {
			reviewNotice.setUpdateTime(new Date());
			reviewNotice.setCreateTime(reviewNotice.getUpdateTime());
			reviewNotice.setStatus(Constants.StatusOffline);
			reviewNotice.setOperator(ResourceUtil.getSessionUserName().getUserName());
			message = "公告添加成功";
			reviewNoticeService.save(reviewNotice);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 公告列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewNoticeEntity reviewNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewNotice.getId())) {
			reviewNotice = reviewNoticeService.getEntity(ReviewNoticeEntity.class, reviewNotice.getId());
			req.setAttribute("reviewNotice", reviewNotice);
		}
		return new ModelAndView("review/manage/notice/reviewNotice");
	}

	/**
	 * 发布\停止分类
	 * @param request
	 */
	@RequestMapping(params="publish")
	@ResponseBody
	public AjaxJson publish(HttpServletRequest request) {
		AjaxJson ajax =  new AjaxJson();
		String id = request.getParameter("id");
		String pubType = request.getParameter("pubType");
		try {
			reviewNoticeService.publish(Long.valueOf(id), pubType);
			if("0".equals(pubType)) {
				ajax.setMsg("下线成功!");
			} else if("1".equals(pubType)) {
				ajax.setMsg("发布成功!");
			}
		} catch (Exception e) {
			ajax.setMsg("操作失败!");
			logger.error("publish error, ", e);
		}
		return ajax;
	}
}
