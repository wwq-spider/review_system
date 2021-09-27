package com.review.manage.variate.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.review.common.CommonUtils;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.variate.entity.ReviewGradeRuleEntity;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.entity.ReviewVariateGradeEntity;
import com.review.manage.variate.service.VariateService;
import com.review.manage.variate.vo.VariateVO;

@Controller
@RequestMapping("/variate")
public class VariateController extends BaseController{

	@Autowired
	private VariateService variateService;
	
	/**
	 * 跳到变量列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toVariateList")
	public ModelAndView toVariateList(HttpServletRequest request, HttpServletResponse response) {
		List<ReviewClassEntity> classList = variateService.findHql("from ReviewClassEntity order by createTime DESC");
		ModelAndView model = new ModelAndView("review/manage/variate/variateList");
		model.addObject("classList", classList);
		return model;
	}
	
	/**
	 * datagrid数据封装
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params="datagrid")
	public void datagrid(HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
		
		String reviewType = request.getParameter("classId");
		//查询列表数据
		List<Map<String, Object>> list = variateService.getVariateList(dataGrid, reviewType);
		Long count = variateService.getVariateCount(reviewType);
		JSONObject json = new JSONObject();
		json.put("rows", list);
		json.put("total", count);
		CommonUtils.responseDatagrid(response, json);
	}
	
	/**
	 * 封装分类为easyui控件combotree对应的JSON格式数据
	 * @return
	 */
	@RequestMapping(params="getComboTreeData")
	public void getComboTreeData(HttpServletRequest request, 
			HttpServletResponse response){
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(variateService.getComboTreeData().toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(pw);
		}		
	}
	
	/**
	 * 跳到变量添加
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toAdd")
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) {
		String variateId = request.getParameter("variateId");
		ModelAndView model = new ModelAndView("review/manage/variate/variateAdd");
		List<ReviewClassEntity> classList = variateService.findHql("from ReviewClassEntity order by createTime DESC");
		if(!"".equals(StringUtils.trimToEmpty(variateId))) {
			VariateVO variate = variateService.getVariateDetail(variateId);
			model.addObject("variate", variate);
			model.addObject("list", variate.getVariateGradeList());
		} else {
			List<ReviewVariateGradeEntity> list = new ArrayList<ReviewVariateGradeEntity>();
			for(int i=0; i<3; i++){
				list.add(new ReviewVariateGradeEntity());
			}
			model.addObject("list", list);
		}
		model.addObject("classList", classList);
		
		return model;
	}
	
	/**
	 * 添加/修改变量
	 * @param request
	 * @param response
	 * @param question
	 * @return
	 */
	@RequestMapping(params="addorupdate")
	@ResponseBody
	public AjaxJson addQuestion(HttpServletRequest request,
			HttpServletResponse response, VariateVO variate) {
		AjaxJson ajax =  new AjaxJson();
		try {
			if(!"".equals(StringUtils.trimToEmpty(variate.getVariateId()))) {
				variateService.updateVariate(variate);
				ajax.setMsg("修改成功");
			} else {
				variateService.addVariate(variate);
				ajax.setMsg("添加成功");
			}
		} catch (Exception e) {
			ajax.setMsg("操作失败");
			e.printStackTrace();
		}
		return ajax;
	}
	
	
	/**
	 * 删除题目
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="del")
	@ResponseBody
	public AjaxJson delQuestioin(HttpServletRequest request,
			HttpServletResponse response) {
		AjaxJson ajax =  new AjaxJson();
		String variateId = request.getParameter("variateId");
		try {
			variateService.delVariate(variateId);
			ajax.setMsg("删除成功!");
		} catch (Exception e) {
			ajax.setMsg("删除失败!");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 添加积分设置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="addScoreSet")
	@ResponseBody
	public AjaxJson addScoreSet(HttpServletRequest request,
			HttpServletResponse response, VariateVO variate) {
		AjaxJson ajax =  new AjaxJson();
		try {
			variateService.addScoreSet(variate);
			ajax.setMsg("添加成功!");
		} catch (Exception e) {
			ajax.setMsg("添加失败!");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 跳到计分设置页面
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params="toScore")
	public ModelAndView toScore(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String variateId = request.getParameter("variateId");
		String classId = request.getParameter("classId");
		String variateName = request.getParameter("variateName");
		String className = variateService.getClassName(variateId);
		variateName = URLDecoder.decode(variateName, "UTF-8");
		ReviewVariateEntity variate = variateService.get(ReviewVariateEntity.class, variateId);
		ModelAndView model = new ModelAndView("review/manage/variate/scoreSet");
		List<ReviewGradeRuleEntity> list = variateService.getVariateQuestionList(variateId);
		if(list.size() == 0) {
			ReviewGradeRuleEntity reviewGradeRule = null;
			for(int i = 0; i<7; i++) {
				reviewGradeRule = new ReviewGradeRuleEntity();
				list.add(reviewGradeRule);
			}
		}
		model.addObject("variateQuestionList", list);
		model.addObject("variateId", variateId);
		model.addObject("variateName", variateName);
		model.addObject("className", className);
		model.addObject("classId", classId);
		model.addObject("calSymbol", variate.getCalSymbol());
		model.addObject("calTotal", variate.getCalTotal());
		model.addObject("calSymbol1", variate.getCalSymbol1());
		model.addObject("calTotal1", variate.getCalTotal1());
		return model;
	}
	
	/**
	 * 上移下移
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="sortVariate")
	public void sortVariate(HttpServletRequest request,
			HttpServletResponse response) {
		String sortNums = request.getParameter("sortNums");
		variateService.sortVariate(sortNums);
		JSONObject json = new JSONObject(); 
		json.put("result", "succ");
		CommonUtils.responseDatagrid(response, json);
	}
}
