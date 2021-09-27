package com.review.manage.report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.review.manage.report.entity.ReviewReportGradeEntity;
import com.review.manage.report.entity.ReviewReportVariateEntity;
import com.review.manage.report.service.ReportService;
import com.review.manage.report.vo.ReportVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.service.VariateService;

/**
 * 报告管理Controller
 * @author wwq
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {

	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private VariateService variateService;
	
	/**
	 * 跳到报告列表页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toReportList")
	public ModelAndView toReportList(HttpServletRequest request, HttpServletResponse response) {
		List<ReviewClassEntity> classList = reportService.findHql("from ReviewClassEntity order by createTime DESC");
		ModelAndView model = new ModelAndView("review/manage/report/reportList");
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
		List<Map<String, Object>> list = reportService.getReportList(dataGrid, reviewType);
		Long count = reportService.getReportCount(reviewType);
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
			pw.write(reportService.getComboTreeData().toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(pw);
		}		
	}
	
	/**
	 * 跳到报告添加
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toAdd")
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		ModelAndView model = new ModelAndView("review/manage/report/reportAdd");
		List<ReviewClassEntity> classList = reportService.findHql("from ReviewClassEntity order by createTime DESC");
		if(!"".equals(StringUtils.trimToEmpty(reportId))) {
			ReportVO report = reportService.getReportDetail(reportId);
			model.addObject("report", report);
			model.addObject("list", report.getReportGradeList());
		} else {
			List<ReviewReportGradeEntity> reportGradeList = new ArrayList<ReviewReportGradeEntity>();
			for(int i=0; i<3; i++){
				reportGradeList.add(new ReviewReportGradeEntity());
			}
			model.addObject("list", reportGradeList);
		}
		model.addObject("classList", classList);
		return model;
	}
	
	/**
	 * 添加/修改报告
	 * @param request
	 * @param response
	 * @param question
	 * @return
	 */
	@RequestMapping(params="addorupdate")
	@ResponseBody
	public AjaxJson addQuestion(HttpServletRequest request,
			HttpServletResponse response, ReportVO report) {
		AjaxJson ajax =  new AjaxJson();
		try {
			if(!"".equals(StringUtils.trimToEmpty(report.getReportId()))) {
				reportService.updateReport(report);
				ajax.setMsg("修改成功");
			} else {
				reportService.addReport(report);
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
		String reportId = request.getParameter("reportId");
		try {
			reportService.delReport(reportId);
			ajax.setMsg("删除成功!");
		} catch (Exception e) {
			ajax.setMsg("删除失败!");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 添加报告设置
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="addReportSet")
	@ResponseBody
	public AjaxJson addReportSet(HttpServletRequest request,
			HttpServletResponse response, ReportVO report) {
		AjaxJson ajax =  new AjaxJson();
		try {
			reportService.addReportSet(report);
			ajax.setMsg("设置成功!");
		} catch (Exception e) {
			ajax.setMsg("设置失败!");
			e.printStackTrace();
		}
		return ajax;
	}
	
	
	
	/**
	 * 跳到报告设置页面
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params="toReportSet")
	public ModelAndView toReportSet(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String reportId = request.getParameter("reportId");
		String reportName = request.getParameter("reportName");
		String classId = request.getParameter("classId");
		String className = reportService.getClassName(reportId);
		reportName = URLDecoder.decode(reportName, "UTF-8");
		ModelAndView model = new ModelAndView("review/manage/report/reportSet");
		List<ReviewReportVariateEntity> list = reportService.getReportVariateList(reportId);
		List<ReviewVariateEntity> variateList = variateService.findByProperty(ReviewVariateEntity.class, "classId", classId);
		model.addObject("reportVariateList", list);
		model.addObject("variateListJSON",JSONArray.fromObject(variateList).toString().replace("\"", "\\\""));
		model.addObject("reportId", reportId);
		model.addObject("reportName", reportName);
		model.addObject("className", className);
		model.addObject("variateList", variateList);
		return model;
	}
}
