package com.review.manage.report.service;

import java.util.List;
import java.util.Map;
import com.review.front.vo.ReviewResultVO;
import net.sf.json.JSONArray;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.review.manage.report.entity.ReviewReportEntity;
import com.review.manage.report.entity.ReviewReportVariateEntity;
import com.review.manage.report.vo.ReportVO;

/**
 * 报告设置Service接口
 * @author Administrator
 *
 */
public interface ReportService extends CommonService {

	/**
	 * 封装分类为easyui控件combotree对应的JSON格式数据
	 * @return
	 */
	public JSONArray getComboTreeData();
	
	/**
	 * 根据报告查询题目ID
	 * @param reportId
	 * @return
	 */
	public String getQuestionIds(String reportId);
	
	/**
	 * 查询数量
	 * @param reviewType
	 * @return
	 */
	public Long getReportCount(String reviewType);
	
	/**
	 * 查询报告列表
	 * @param dataGrid
	 * @param reviewType
	 * @return
	 */
	public List<Map<String, Object>> getReportList(DataGrid dataGrid, String reviewType);
	
	/**
	 * 添加报告
	 * @param report
	 */
	public void addReport(ReportVO report);
	
	/**
	 * 修改报告
	 * @param report
	 */
	public void updateReport(ReportVO report);
	
	/**
	 * 删除报告
	 * @param reportId
	 */
	public void delReport(String reportId);
	
	/**
	 * 
	 * @param reportId
	 * @return
	 */
	public ReportVO getReportDetail(String reportId);
	
	/**
	 * 查询因子列表
	 * @param page
	 * @param rows
	 * @return
	 */
	List<ReviewReportEntity> getReportList(int page, int rows);
	
	/**
	 * 查询因子报告及其积分信息
	 * @param classId
	 * @return
	 */
	List<ReportVO> getReportVOList(String classId);
	
	/**
	 * 添加积分设置
	 * @param report
	 */
	void addReportSet(ReportVO report);
	
	/**
	 * 根据报告ID 查询因子列表
	 * @param reportId
	 * @return
	 */
	List<ReviewReportVariateEntity> getReportVariateList(String reportId);
	
	/**
	 * 根据报告ID查询分类名称
	 * @param reportId
	 * @return
	 */
	String getClassName(String reportId);

    List<ReviewResultVO> getReviewResult(ReviewResultVO reviewResult);

    List<ReviewResultVO> getProjectReviewResult(ReviewResultVO reviewResult);
}
