package com.review.manage.banner.controller;

import cn.hutool.core.util.StrUtil;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.banner.entity.ReviewBannerEntity;
import com.review.manage.banner.service.ReviewBannerServiceI;
import com.review.manage.banner.vo.ReviewBannerVO;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MyBeanUtils;
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
 * @Description: banner轮播图
 * @author zhangdaihao
 * @date 2021-11-25 11:24:24
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewBanner")
public class ReviewBannerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewBannerController.class);

	@Autowired
	private ReviewBannerServiceI reviewBannerService;
	@Autowired
	private SystemService systemService;


	/**
	 * banner轮播图列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "toList")
	public ModelAndView reviewBanner(HttpServletRequest request) {
		return new ModelAndView("review/manage/banner/reviewBannerList");
	}

	/**
	 * 列表数据查询
	 * @param reviewBanner
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(ReviewBannerEntity reviewBanner,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewBannerEntity.class, dataGrid);
		cq.addOrder("operateTime", SortDirection.desc);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewBanner, request.getParameterMap());
		this.reviewBannerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除banner轮播图
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewBannerEntity reviewBanner, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "banner轮播图删除成功";
		reviewBannerService.deleteEntityById(ReviewBannerEntity.class, reviewBanner.getId());
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加banner轮播图
	 * @param reviewBanner
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewBannerVO reviewBanner) {
		AjaxJson j = new AjaxJson();
		String message = "";
		if (StringUtil.isNotEmpty(reviewBanner.getId())) {
			message = "banner轮播图更新成功";
			ReviewBannerEntity t = reviewBannerService.get(ReviewBannerEntity.class, reviewBanner.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(reviewBanner, t);
				String imgUrl = CommonUtils.saveCoverImg(reviewBanner.getContentImg(), Constants.ReviewBannerDir);
				if (StrUtil.isNotBlank(imgUrl)) {
					t.setImgUrl(imgUrl);
				}
				t.setOperateTime(new Date());
				t.setOperator(ContextHolderUtils.getLoginUserName());
				reviewBannerService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error("save error, ", e);
				message = "banner轮播图更新失败";
			}
		} else {
			ReviewBannerEntity t = new ReviewBannerEntity();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(reviewBanner, t);
				message = "轮播图添加成功";
				t.setCreateTime(new Date());
				t.setOperateTime(t.getCreateTime());
				//设置头像
				t.setImgUrl(CommonUtils.saveCoverImg(reviewBanner.getContentImg(), Constants.ReviewBannerDir));
				t.setOperator(ContextHolderUtils.getLoginUserName());
				t.setStatus(Constants.StatusOffline);
				reviewBannerService.save(t);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				logger.error("save error, ", e);
				message = "banner轮播图创建失败";
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * banner轮播图列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewBannerEntity reviewBanner, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewBanner.getId())) {
			reviewBanner = reviewBannerService.getEntity(ReviewBannerEntity.class, reviewBanner.getId());
			req.setAttribute("reviewBanner", reviewBanner);
		}
		return new ModelAndView("review/manage/banner/reviewBanner");
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
			reviewBannerService.publish(Long.valueOf(id), pubType);
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
