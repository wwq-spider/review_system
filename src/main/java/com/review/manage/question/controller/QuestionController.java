package com.review.manage.question.controller;

import com.review.common.CommonUtils;
import com.review.manage.question.service.ReviewService;
import com.review.manage.question.vo.QuestionVO;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测评后台管理controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/review")
public class QuestionController extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(QuestionController.class);
	
	@Autowired
	public ReviewService reviewService;
	
	/**
	 * 题目列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toQuestionList")
	public ModelAndView toQuestionList(HttpServletRequest request,
			HttpServletResponse response) {
		String classId = request.getParameter("classId");
		ModelAndView model = new ModelAndView("review/manage/question/questionList");
		model.addObject("classId", classId);
		return model;
	}
	
	/**
	 * 跳到题目添加
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(params="toAdd")
	public ModelAndView toAdd(HttpServletRequest request) throws UnsupportedEncodingException {
		String questionId = request.getParameter("questionId");
		String classId = request.getParameter("classId");
		String title = request.getParameter("title");
		title = URLDecoder.decode(title,"UTF-8");
		ModelAndView model = new ModelAndView("");
		if(!"".equals(StringUtils.trimToEmpty(questionId))) {
			model.setViewName("review/manage/question/questionEdit");
			QuestionVO question = reviewService.getQuestionDetail(questionId);
			model.addObject("question", question);
			List<QuestionVO> list = reviewService.getAnswersByQuestionId(questionId);
			model.addObject("answersList", list);
		} else {
			List<QuestionVO> list = new ArrayList<QuestionVO>();
			Integer maxQuestionNum = reviewService.getMaxQuestionId(classId);
			String[] arr = new String[]{"A","B","C","D"};
			QuestionVO questionVO = null;
			for(int i=0; i<arr.length; i++) {
				questionVO = new QuestionVO();
				questionVO.setSelCode(arr[i]);
				list.add(questionVO);
			}
			model.addObject("questionNum", maxQuestionNum+1);
			model.addObject("answersList", list);
			model.setViewName("review/manage/question/question");
		}
		model.addObject("reviewType", title);
		model.addObject("classId", classId);
		return model;
	}

	/**
	 * datagrid题目数据
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params="datagrid")
	public void datagrid(HttpServletRequest request,
						 HttpServletResponse response, DataGrid dataGrid) {

		//获取查询条件
		String questionType = request.getParameter("questionType");
		String reviewType = request.getParameter("classId");
		String content = request.getParameter("content");

		//查询列表数据
		List<Map<String, Object>> list = reviewService.getQuestionList(content, questionType,
				reviewType, dataGrid);
		Long count = reviewService.getQuestionCount(content, questionType, reviewType);
		JSONObject json = new JSONObject();
		json.put("rows", list);
		json.put("total", count);
		CommonUtils.responseDatagrid(response, json);
	}
	
	/**
	 * 添加/修改题目
	 * @param request
	 * @param question
	 * @return
	 */
	@RequestMapping(params="add")
	@ResponseBody
	public AjaxJson addQuestion(HttpServletRequest request, QuestionVO question) {
		AjaxJson ajax =  new AjaxJson();
		
		try {
			String falg = reviewService.addQuestion(question,request);
			if("succ".equals(falg)) {
				ajax.setMsg("添加成功");
			} else {
				ajax.setMsg("添加失败,题目编号已存在，请检查修正");
			}
			
		} catch (Exception e) {
			ajax.setMsg("添加失败");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 修改题目
	 * @param request
	 * @param response
	 * @param question
	 * @return
	 */
	@RequestMapping(params="update")
	@ResponseBody
	public AjaxJson updQuestion(HttpServletRequest request,
			HttpServletResponse response, QuestionVO question) {
		AjaxJson ajax =  new AjaxJson();
		
		try {
			reviewService.updateQuestion(question,request);
			ajax.setMsg("修改成功");
		} catch (Exception e) {
			ajax.setMsg("修改失败");
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
		String questionId = request.getParameter("questionId");
		try {
			reviewService.delQuestion(questionId);
			ajax.setMsg("删除成功!");
		} catch (Exception e) {
			ajax.setMsg("删除失败!");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 预览图片
	 * @param request
	 * @param response
	 */
	@RequestMapping(params="previewImg")
	public void previewImg(HttpServletRequest request,
			HttpServletResponse response) {
//		BufferedInputStream bis = null;
//		BufferedOutputStream bos = null;
//		InputStream in = null;
//		OutputStream out = null;
//		String answerId = request.getParameter("answerId");
//		String questionId = request.getParameter("questionId");
//		byte[] attach = new byte[0];
//		if(!"".equals(StringUtils.trimToEmpty(questionId))) {
//			ReviewQuestionEntity questionEntity = reviewService.get(ReviewQuestionEntity.class, Integer.parseInt(questionId));
//			attach = questionEntity.getPictureAttach();
//		} else if(!"".equals(StringUtils.trimToEmpty(answerId))) {
//			ReviewAnswerEntity answerEntity = reviewService.get(ReviewAnswerEntity.class, answerId);
//			attach = answerEntity.getPictureAttach();
//		}
//
//		try {
//			response.setContentType("image/jpeg");
//			out = response.getOutputStream();
//			in = new ByteArrayInputStream(attach);
//			bis = new BufferedInputStream(in);
//			bos = new BufferedOutputStream(out);
//			byte[] buff = new byte[8 * 1024];
//			int bytesRead;
//			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
//			    bos.write(buff, 0, bytesRead);
//			    bos.flush();
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			IOUtils.closeQuietly(in);
//			IOUtils.closeQuietly(out);
//			IOUtils.closeQuietly(bis);
//			IOUtils.closeQuietly(bos);
//		}
	}
	
	/**
	 * 跳到导入题库页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toQuestionImport")
	public  ModelAndView toQuestionImport(HttpServletRequest request,
			HttpServletResponse response) {
		String classId = request.getParameter("classId");
		ModelAndView model = new ModelAndView("review/manage/question/questionImport");
		model.addObject("classId", classId);
		return model;
	}
	
	/**
	 * 导入EXCEL
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		String classId = request.getParameter("classId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		try {
			String questionIds = reviewService.importExcel(fileMap, classId);
			if("".equals(questionIds)) {
				j.setMsg("导入成功！"); 
			} else {
				j.setMsg("题目编号 "+questionIds+"已存在，请修正再导入");
			}
		} catch (Exception e) {
			j.setMsg("导入失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		return j;
	}
	
	
}
