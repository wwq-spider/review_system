package com.review.manage.userManage.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.springframework.web.multipart.MultipartFile;

import com.review.manage.userManage.entity.ReviewUserEntity;

public interface ReviewUserService extends CommonService {

	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @return
	 */
	public ReviewUserEntity getUserByUserName(String userName);

	/**
	 * 分页查询用户列表
	 * @param userName
	 * @param realName
	 * @param groupId
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> getReviewUserList(String userName, String realName, String groupId, int page, int rows);

	/**
	 * 查询 用户数量
	 * @param userName
	 * @param groupId
	 * @param realName
	 * @return
	 */
	Long getReviewUserCount(String userName, String groupId, String realName);
	
	/**
	 * 删除用户
	 * @param userId
	 */
	public void delUser(String userId);
	
	/**
	 * 添加/修改用户
	 * @param reviewUser
	 */
	public void addorupdateUser(ReviewUserEntity reviewUser);
	
	/**
	 * 导入题目答案 并直接分析 结果并导出
	 * @param request
	 * @param response
	 * @return
	 */
	public String importUserAndAnswer(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 导入用户
	 * @param fileMap
	 * @return
	 */
	String importUser(Map<String, MultipartFile> fileMap, String groupId) throws IOException;
	
	/**
	 * 查询测评记录
	 * @param userId
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> getReviewRecordList(String userId, int page, int rows);
	
	/**
	 * 查询测评记录数量
	 * @param userId
	 * @return
	 */
	public Long getReviewRecordCount(String userId);
	
	/**
	 * 删除测评记录
	 * @param resultId
	 */
	void delReviewRecord(String resultId);

	/**
	 * 获取测评用户组
	 * @return
	 */
	List<TSDepart> getReviewUserGroup();
}
