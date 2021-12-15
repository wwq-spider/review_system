package com.review.front.service;

import java.util.List;

import com.review.front.entity.ReviewResultEntity;
import com.review.front.vo.ReviewResultVO;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import net.sf.json.JSONObject;
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
	 * @param reviewUser
	 * @return
	 */
	ReviewResultEntity completeReview(List<QuestionVO> resultList, String classId, ReviewUserEntity reviewUser);

	/**
	 * 查询测评报告
	 * @param userId
	 * @param classId
	 * @return
	 */
	List<ReviewReportResultEntity> getReportResults(String userId, String classId);

	/**
	 * 查询我的报告/测评记录
	 * @param userId
	 * @return
	 */
	List<ReviewResultVO> getReportResults(String userId, Long projectId);

	/**
	 * 获取测评报告
	 * @param userId
	 * @param projectId
	 * @return
	 */
	List<ReviewResultVO> getReviewReports(String userId, Long projectId);

	/**
	 * 注册用户信息
	 * @param reviewUser
	 * @return
	 */
	JSONObject register(ReviewUserEntity reviewUser);

	/**
	 * 查询用户信息
	 * @param openid
	 * @return
	 */
	ReviewUserEntity getUserInfo(String openid);

	/**
	 * 查询用户组下的测评项目
	 * @param groupId
	 * @return
	 */
	List<ReviewClassVO> getReviewClassByGroupId(String groupId);
}
