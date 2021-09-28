package com.review.manage.reviewClass.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.review.manage.reviewClass.vo.ReviewClassVO;

public interface ReviewClassService extends CommonService{

	/**
	 * 添加分类
	 * @param reviewClass
	 */
	public void addReviewClass(ReviewClassVO reviewClass) throws Exception;
	
	/**
	 * 修改分类
	 * @param reviewClass
	 */
	public void updateReviewClass(ReviewClassVO reviewClass) throws Exception;
	
	/**
	 * 查询分类列表
	 * @param reviewClass
	 * @return
	 */
	public List<Map<String, Object>> getReviewClassList(ReviewClassVO reviewClass,DataGrid dataGrid);

	/**
	 * 查询测评项目下的题目
	 * @param projectId
	 * @return
	 */
	List<ReviewClassVO> getReviewClassByProjectId(Long projectId);
	
	/**
	 * 查询分类数量
	 * @param reviewClass
	 * @return
	 */
	public Long getReviewClassCount(ReviewClassVO reviewClass);
	
	/**
	 * 删除分类
	 * @param classId
	 */
	public void delReviewClass(String classId);
	
	/**
	 * 发布/停止分类
	 * @param classId
	 * @param pubType
	 */
	void publishClass(String classId, String pubType);

	/**
	 * 设置热门
	 * @param classId
	 * @param opt 1：置为热门 2：取消热门
	 */
	void setUpHot(String classId, Integer opt);
}
