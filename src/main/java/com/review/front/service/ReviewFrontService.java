package com.review.front.service;

import java.util.List;

import com.review.front.entity.ReviewResultEntity;
import com.review.manage.userManage.entity.ReviewUserEntity;
import org.jeecgframework.core.common.service.CommonService;

import com.review.front.entity.ReviewReportResultEntity;
import com.review.front.vo.SelectVO;
import com.review.manage.question.vo.QuestionVO;

public interface ReviewFrontService extends CommonService{

	/**
	 * 查询问题列表
	 * @param classId
	 * @return
	 */
	List<QuestionVO> getQuestionVOList(String classId);
	
	/**
	 * 查询问题列表
	 * @param classId
	 * @param num
	 * @return
	 */
	List<QuestionVO> getQuestionVOList(String classId,int page,  int num);
	
	/**
	 * 查询问题详情
	 * @param classId
	 * @param num
	 * @return
	 */
	QuestionVO getQuestionDetail(String classId,int page,  int num);
	
	/**
	 * 查询问题选项
	 * @param questionId
	 * @return
	 */
	List<SelectVO>  getSelectVOList(Integer questionId);
	
	/**
	 * 完成测试
	 * @param resultList
	 * @param classId
	 * @param userId
	 * @param userName
	 * @return
	 */
	ReviewResultEntity completeReview(List<QuestionVO> resultList, String classId,
									  String userId, String userName);
	
	List<ReviewReportResultEntity> getReportResults(String userId, String classId);

	/**
	 * 注册用户信息
	 * @param reviewUser
	 * @return
	 */
	boolean register(ReviewUserEntity reviewUser);

	/**
	 * 查询用户信息
	 * @param openid
	 * @return
	 */
	ReviewUserEntity getUserInfo(String openid);
}
