package com.review.manage.userManage.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
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

import com.review.common.CommonUtils;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.userManage.service.ReviewUserService;

@Controller
@RequestMapping("/reviewUser")
public class ReviewUserController extends BaseController{

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReviewUserController.class);
	
	@Autowired
	private ReviewUserService reviewUserService;
	
	/**
	 * 跳到测评用户页面 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toReviewUserList")
	public ModelAndView toReviewUserList(HttpServletRequest request, 
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("review/manage/userManage/reviewUserList");
		return model;
	}
	
	/**
	 * datagrid数据显示
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param reviewUser
	 */
	@RequestMapping(params="datagrid")
	public void  datagrid(HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid, ReviewUserEntity  reviewUser){
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> list = reviewUserService.getReviewUserList(reviewUser.getUserName(), 
				reviewUser.getRealName(), dataGrid.getPage(), dataGrid.getRows());
		Long count = reviewUserService.getReviewUserCount(reviewUser.getUserName(), 
				reviewUser.getRealName());
		jsonObject.put("rows", list);
		jsonObject.put("total", count);
		CommonUtils.responseDatagrid(response, jsonObject);
	}
	
	/**
	 * 添加或修改用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="addorupdate")
	@ResponseBody
	public AjaxJson addorupdate(HttpServletRequest request, 
			HttpServletResponse response, ReviewUserEntity reviewUser){
		AjaxJson ajax = new AjaxJson();
		try {
			if(!"".equals(StringUtils.trimToEmpty(reviewUser.getUserId()))) {
				reviewUserService.addorupdateUser(reviewUser);
				ajax.setMsg("修改成功");
			} else {
				reviewUserService.addorupdateUser(reviewUser);
				ajax.setMsg("添加成功");
			}
			
		} catch (Exception e) {
			ajax.setMsg("操作失败");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 跳到添加或修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toAdd")
	public ModelAndView toAdd(HttpServletRequest request, 
			HttpServletResponse response, ReviewUserEntity reviewUser) {
		ModelAndView model = new ModelAndView("review/manage/userManage/reviewUserAdd");
		if(!"".equals(StringUtils.trimToEmpty(reviewUser.getUserId()))) {
			model.addObject("user", reviewUserService.get(ReviewUserEntity.class, reviewUser.getUserId()));
		}
		return model;
	}
	
	/**
	 * 删除用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="del")
	@ResponseBody
	public AjaxJson delUser(HttpServletRequest request, 
			HttpServletResponse response) {
		AjaxJson ajax = new AjaxJson();
		String userId = request.getParameter("userId");
		try {
			reviewUserService.deleteEntityById(ReviewUserEntity.class, userId);
			ajax.setMsg("删除成功");
		} catch (Exception e) {
			ajax.setMsg("删除失败");
			e.printStackTrace();
		}
		return ajax;
	}
	
	/**
	 * 跳到批量导入页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toBatchImport")
	public  ModelAndView toBatchImport(HttpServletRequest request,
			HttpServletResponse response) {
		List<ReviewClassEntity> classList = reviewUserService.findHql("from ReviewClassEntity order by createTime DESC");
		ModelAndView model = new ModelAndView("review/manage/userManage/batchImport");
		model.addObject("classList", classList);
		return model;
	}
	
	/**
	 * 导入用户数据并导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "upload")
	@ResponseBody
	public AjaxJson excelImport(HttpServletRequest request,HttpServletResponse response) {

		 AjaxJson j = new AjaxJson();

		 try {
			String strPath = reviewUserService.importUserAndAnswer(request, response);
			
			j.setMsg("导入成功！"); 
			j.setParams(strPath);
		} catch (Exception e) {
			j.setMsg("导入失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} 
		 return j;
	}
	
	/**
	 * 导入用户数据并导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "excelExport")
	public void excelExport(HttpServletRequest request,HttpServletResponse response) {
		 response.setContentType("application/vnd.ms-excel");
		 response.setContentType("text/html;charset=UTF-8");  
		 BufferedInputStream bis = null;  
		 BufferedOutputStream bos = null;  
		 try {
			//Properties props = System.getProperties();
			//String separator = props.getProperty("file.separator");// 文件分隔符 
				 
			String fileTempName = request.getParameter("fileTempName");
				// 设置文档生成的临时位置
			//String strPath = request.getSession().getServletContext().getRealPath(separator)+ separator+ fileTempName;
			
			long fileLength = new File(fileTempName).length(); 
			response.setContentType("application/octet-stream");  
			String fileName = "结果列表.xls";
			response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1"));  
			response.setHeader("Content-Length", String.valueOf(fileLength)); 
			
			bis = new BufferedInputStream(new FileInputStream(fileTempName));
			bos = new BufferedOutputStream(response.getOutputStream());  
			byte[] buff = new byte[8 * 1024];  
			int bytesRead;  
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
			    bos.write(buff, 0, bytesRead);  
			}
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(bos);		
		}
	}
	
	
	/**
	 * 跳到用户导入页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toUserImport")
	public  ModelAndView toUserImport(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("review/manage/userManage/userImport");
		return model;
	}
	
	/**
	 * 导入EXCEL
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "importUser", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importUser(HttpServletRequest request, HttpServletResponse response){
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		try {
			String userNames = reviewUserService.importUser(fileMap);
			if("".equals(userNames)) {
				j.setMsg("导入成功！"); 
			} else {
				j.setMsg("用户 "+userNames+"已存在");
			}
		} catch (Exception e) {
			j.setMsg("导入失败！");
			logger.error(ExceptionUtil.getExceptionMessage(e));
		}
		return j;
	}
	
	/**
	 * 跳到测评记录页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="toReviewRecord")
	public ModelAndView toReviewRecord(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("review/manage/userManage/reviewRecord");
		model.addObject("userId", request.getParameter("userId"));
		return model;
	}
	
	/**
	 * 查询测评记录
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params="recordDatagrid")
	public void  datagrid(HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid){
		JSONObject jsonObject = new JSONObject();
		String userId = request.getParameter("userId");
		List<Map<String, Object>> list = reviewUserService.getReviewRecordList(userId,
				dataGrid.getPage(), dataGrid.getRows());
		Long count = reviewUserService.getReviewRecordCount(userId);
		jsonObject.put("rows", list);
		jsonObject.put("total", count);
		CommonUtils.responseDatagrid(response, jsonObject);
	}
	
	/**
	 * 删除测评记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="delRecord")
	@ResponseBody
	public AjaxJson delRecord(HttpServletRequest request, 
			HttpServletResponse response) {
		AjaxJson ajax = new AjaxJson();
		String resultId = request.getParameter("resultId");
		try {
			reviewUserService.delReviewRecord(resultId);
			ajax.setMsg("删除成功");
		} catch (Exception e) {
			ajax.setMsg("删除失败");
			e.printStackTrace();
		}
		return ajax;
	}
}
