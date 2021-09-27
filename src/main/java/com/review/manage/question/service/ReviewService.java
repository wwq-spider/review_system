package com.review.manage.question.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;
import org.springframework.web.multipart.MultipartFile;

import com.review.manage.question.entity.ReviewQuestionEntity;
import com.review.manage.question.vo.QuestionVO;

public interface ReviewService extends CommonService{
	
	/**
	 * 查询试题列表
	 * @param content
	 * @param questionType
	 * @param reviewType
	 * @param datagrid
	 * @return
	 */
	public List<Map<String, Object>> getQuestionList(String content, 
			String questionType, String reviewType, DataGrid datagrid);
	
	/**
	 * 查询试题数量
	 * @param content
	 * @param questionType
	 * @param reviewType
	 * @return
	 */
	public Long getQuestionCount(String content, 
			String questionType, String reviewType);
	
	/**
	 * 添加试题
	 * @param question
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String addQuestion(QuestionVO question,HttpServletRequest request)  throws IOException;
	
	/**
	 * 修改试题
	 * @param question
	 */
	public void updateQuestion(QuestionVO question,HttpServletRequest request)  throws IOException;
	
	/**
	 * 删除试题
	 * @param questionId
	 */
	public void delQuestion(String questionId);
	
	/**
	 * 查询问题详情
	 * @param questionId
	 * @return
	 */
	public QuestionVO getQuestionDetail(String questionId);
	
	/**
	 * 根据问题ID查询选项
	 * @param questionId
	 * @return
	 */
	public List<QuestionVO> getAnswersByQuestionId(String questionId);
	
	/**
	 * 导入EXCEL
	 * @param fileMap
	 * @param classId
	 */
	public String importExcel(Map<String, MultipartFile> fileMap, String classId) throws Exception;
	
	/**
	 * 查询题目表中最大的题目ID
	 * @return
	 */
	public Integer getMaxQuestionId(String classId);
	
	/**
	 * 根据题目编号查询某分类下的题目
	 * @param classId
	 * @param questionNum
	 * @return
	 */
	public ReviewQuestionEntity getQuestionByQnum(String classId, Integer questionNum);
}
