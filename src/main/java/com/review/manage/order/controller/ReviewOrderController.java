package com.review.manage.order.controller;

import com.review.manage.order.entity.ReviewOrderEntity;
import com.review.manage.order.service.ReviewOrderServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**   
 * @Title: Controller
 * @Description: 公告
 * @author zhangdaihao
 * @date 2021-11-22 15:20:09
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewOrder")
public class ReviewOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewOrderController.class);

	@Autowired
	private ReviewOrderServiceI reviewOrderService;


	/**
	 * 订单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toList")
	public ModelAndView reviewOrder(HttpServletRequest request) {
		return new ModelAndView("review/manage/order/reviewOrderList");
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
	public void datagrid(ReviewOrderEntity reviewOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewOrderEntity.class, dataGrid);
		cq.addOrder("operateTime", SortDirection.desc);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewOrder, request.getParameterMap());
		this.reviewOrderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 公告列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewOrderEntity reviewOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewOrder.getId())) {
			reviewOrder = reviewOrderService.getEntity(ReviewOrderEntity.class, reviewOrder.getId());
			req.setAttribute("reviewOrder", reviewOrder);
		}
		return new ModelAndView("review/manage/order/reviewOrder");
	}
}
