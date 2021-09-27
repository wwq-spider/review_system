package com.review.manage.variate.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.review.manage.variate.entity.ReviewGradeRuleEntity;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.vo.VariateVO;

public interface VariateService extends CommonService{

	/**
	 * 封装分类为easyui控件combotree对应的JSON格式数据
	 * @return
	 */
	public JSONArray getComboTreeData();
	
	/**
	 * 根据变量查询题目ID
	 * @param variateId
	 * @return
	 */
	public String getQuestionIds(String variateId);
	
	/**
	 * 查询数量
	 * @param reviewType
	 * @return
	 */
	public Long getVariateCount(String reviewType);
	
	/**
	 * 查询变量列表
	 * @param dataGrid
	 * @param reviewType
	 * @return
	 */
	public List<Map<String, Object>> getVariateList(DataGrid dataGrid, String reviewType);
	
	/**
	 * 添加变量
	 * @param variate
	 */
	public void addVariate(VariateVO variate);
	
	/**
	 * 修改变量
	 * @param variate
	 */
	public void updateVariate(VariateVO variate);
	
	/**
	 * 删除变量
	 * @param variateId
	 */
	public void delVariate(String variateId);
	
	/**
	 * 
	 * @param variateId
	 * @return
	 */
	public VariateVO getVariateDetail(String variateId);
	
	/**
	 * 查询因子列表
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<ReviewVariateEntity> getVariateList(int page, int rows);
	
	/**
	 * 查询因子变量及其积分信息
	 * @param classId
	 * @return
	 */
	public List<VariateVO> getVariateVOList(String classId);
	
	/**
	 * 添加积分设置
	 * @param variate
	 */
	public void addScoreSet(VariateVO variate);
	
	/**
	 * 根据变量ID查询变量计分题
	 * @param variateId
	 * @return
	 */
	public List<ReviewGradeRuleEntity> getVariateQuestionList(String variateId);
	
	/**
	 * 根据 因子ID查询分类名称
	 * @param variateId
	 * @return
	 */
	public String getClassName(String variateId);
	
	/**
	 * 排序
	 * @param sortNums
	 */
	public void sortVariate(String sortNums);
}
