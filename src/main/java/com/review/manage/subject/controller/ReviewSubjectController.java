package com.review.manage.subject.controller;

import com.review.manage.project.entity.ReviewProjectClassEntity;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.subject.entity.ReviewSubjectClassEntity;
import com.review.manage.subject.entity.ReviewSubjectEntity;
import com.review.manage.subject.service.ReviewSubjectServiceI;
import com.review.manage.subject.vo.ReviewSubjectVO;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Controller
 * @Description: 测评主题
 * @author zhangdaihao
 * @date 2021-11-12 19:03:18
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/reviewSubject")
public class ReviewSubjectController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewSubjectController.class);

	@Autowired
	private ReviewSubjectServiceI reviewSubjectService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 测评主题列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView reviewSubject() {
		return new ModelAndView("review/manage/subject/reviewSubjectList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(ReviewSubjectEntity reviewSubject, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(ReviewSubjectEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, reviewSubject, request.getParameterMap());
		this.reviewSubjectService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除测评主题
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(ReviewSubjectEntity reviewSubject) {
		AjaxJson j = new AjaxJson();
		message = "测评主题删除成功";
		reviewSubjectService.deleteEntityById(ReviewSubjectEntity.class, reviewSubject.getId());
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加测评主题
	 * 
	 * @param reviewSubject
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(ReviewSubjectVO reviewSubject) {
		AjaxJson j = new AjaxJson();
		String message = reviewSubjectService.saveOrUpdate(reviewSubject);
		j.setMsg(message);
		if (message.indexOf("成功") == -1) {
			j.setSuccess(false);
		}
		return j;
	}

	/**
	 * 测评主题列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(ReviewSubjectEntity reviewSubject, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(reviewSubject.getId())) {
			reviewSubject = reviewSubjectService.getEntity(ReviewSubjectEntity.class, reviewSubject.getId());
			ReviewSubjectVO subjectVO = new ReviewSubjectVO();
			try {
				MyBeanUtils.copyBeanNotNull2Bean(reviewSubject, subjectVO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("subjectId", reviewSubject.getId().toString());
			subjectVO.setSubjectClassList(reviewSubjectService.getObjectList("select id, subject_id subjectId, class_id classId from review_subject_class where subject_id=:subjectId", paramMap, ReviewSubjectClassEntity.class));
			req.setAttribute("reviewSubject", subjectVO);
		}
		List<ReviewClassEntity> classList = reviewSubjectService.findHql("from ReviewClassEntity order by createTime DESC");
		ModelAndView model = new ModelAndView("review/manage/subject/reviewSubject");
		model.addObject("classList", classList);
		return model;
	}

	/**
	 * 发布\下线专题
	 * @param request
	 */
	@RequestMapping(params="publish")
	@ResponseBody
	public AjaxJson publish(HttpServletRequest request) {
		AjaxJson ajax =  new AjaxJson();
		String subjectId = request.getParameter("subjectId");
		String pubType = request.getParameter("pubType");
		try {
			reviewSubjectService.publish(Long.valueOf(subjectId), pubType);
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
