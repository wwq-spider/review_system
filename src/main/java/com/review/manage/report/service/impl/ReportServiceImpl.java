package com.review.manage.report.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.review.front.vo.ReviewResultVO;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.stereotype.Service;

import com.review.common.CommonUtils;
import com.review.manage.question.entity.ReviewQuestionEntity;
import com.review.manage.report.entity.ReviewReportEntity;
import com.review.manage.report.entity.ReviewReportGradeEntity;
import com.review.manage.report.entity.ReviewReportVariateEntity;
import com.review.manage.report.service.ReportService;
import com.review.manage.report.vo.ReportVO;

/**
 * 报告管理Service接口实现类
 * @author Administrator
 *
 */
@Service("reportService")
public class ReportServiceImpl extends CommonServiceImpl implements ReportService {

	@Override
	public JSONArray getComboTreeData() {
		List<ReviewQuestionEntity> list = getQuestions();
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(ReviewQuestionEntity question : list) {
			map = new HashMap<String, Object>();
			map.put("id", question.getQuestionId());
			map.put("text", question.getQuestionId() + "." + question.getContent());
			map.put("iconCls", "icon-logo");
			mapList.add(map);
		}
		
		return JSONArray.fromObject(mapList);
	}
	
	private List<ReviewQuestionEntity> getQuestions() {
		String hql = "from ReviewQuestionEntity order by questionId asc";
		return this.findHql(hql);
	}

	@Override
	public String getQuestionIds(String reportId) {
		return null;
	}

	@Override
	public Long getReportCount(String reviewType) {
		String sql = "select count(report_id) from review_report";
		if(!"".equals(StringUtils.trimToEmpty(reviewType))) {
			sql += " where class_id='" + reviewType+"'";
		}
		return this.getCountForJdbc(sql);
	}

	@Override
	public List<Map<String, Object>> getReportList(DataGrid dataGrid,
			String reviewType) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("	  v.`class_id` classId,");
		sb.append("   c.title className,");
		sb.append("   v.`create_by` createBy,");
		sb.append("   v.`report_name` reportName,");
		sb.append("   v.`report_id` reportId,");
		sb.append("   DATE_FORMAT(v.`create_time`,'%Y-%m-%e %H:%i:%S') createTime");
		sb.append(" FROM  ");
		sb.append("   review_report v, review_class c ");
		sb.append(" WHERE v.`class_id`=c.`class_id`");
		if(!"".equals(StringUtils.trimToEmpty(reviewType))) {
			sb.append(" AND c.`class_id`='"+reviewType+"'");
		}
		sb.append(" ORDER BY v.CREATE_TIME DESC ");
		return this.findForJdbc(sb.toString(), dataGrid.getPage(), dataGrid.getRows());
	}

	@Override
	public void addReport(ReportVO report) {
		String resultExplain = "";
		List<ReviewReportGradeEntity> list = report.getReportGradeList();
		Integer reportNum = report.getReportNum();
		ReviewReportEntity reportEntity = new ReviewReportEntity();
		reportEntity.setClassId(report.getClassId());
		reportEntity.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
		reportEntity.setCreateTime(new Date());
		reportEntity.setReportName(report.getReportName());	
		this.save(reportEntity);
		
		
		for(ReviewReportGradeEntity reportGrade : list) {
			if(reportGrade.getFile() != null) {
				if(reportGrade.getFile().getSize() > 0) {
					resultExplain = CommonUtils.readTxtFile(reportGrade.getFile());
					reportGrade.setResultExplain(resultExplain);
				}
			}
			reportGrade.setReportId(reportEntity.getReportId());
			this.save(reportGrade);
		}
		if(reportNum != null) {
			reportEntity.setCurGradeId(list.get(reportNum).getReportGradeId());
		}
	}

	@Override
	public void updateReport(ReportVO report) {
		String resultExplain = "";
		List<ReviewReportGradeEntity> list = report.getReportGradeList();
		Integer reportNum = report.getReportNum();
		ReviewReportEntity reportEntity = this.get(ReviewReportEntity.class, report.getReportId());
		reportEntity.setClassId(report.getClassId());
		reportEntity.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
		reportEntity.setCreateTime(new Date());
		reportEntity.setReportName(report.getReportName());
		
		//先删除变量题目关联表
		String sql = "delete from review_report_grade where report_id=?";
		this.executeSql(sql, new Object[]{report.getReportId()});
		
		for(ReviewReportGradeEntity reportGrade : list) {
			if(reportGrade.getFile() != null) {
				if(reportGrade.getFile().getSize() > 0) {
					resultExplain = CommonUtils.readTxtFile(reportGrade.getFile());
					reportGrade.setResultExplain(resultExplain);
				}
			}
			reportGrade.setReportId(report.getReportId());
			this.save(reportGrade);
		}
		if(reportNum != null) {
			reportEntity.setCurGradeId(list.get(reportNum).getReportGradeId());
		}
		this.saveOrUpdate(reportEntity);
	}

	@Override
	public void delReport(String reportId) {
		//先删除变量题目关联表
		String sql = "delete from review_report_grade where report_id=?";
		this.executeSql(sql, new Object[]{reportId});
		
		//删除变量-报告关联表数据
		String sqlV = "DELETE FROM review_report_variate WHERE report_id=?";
		this.executeSql(sqlV, new Object[]{reportId});
		
		//删除报告
		this.deleteEntityById(ReviewReportEntity.class, reportId);
	}

	@Override
	public ReportVO getReportDetail(String reportId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("   r.`report_name` reportName,");
		sb.append("   r.`report_id` reportId,");
		sb.append("   r.class_id classId,   ");
		sb.append("   r.`cur_grade_id` curGradeId ");
		sb.append(" FROM");
		sb.append("   review_report r ");
		sb.append(" WHERE r.`report_id` = :reportId ");
		Map<String, String> map = new HashMap<String, String>();
		map.put("reportId", reportId);
		ReportVO report = this.getObjectDetail(sb.toString(), map, ReportVO.class);
		
		List<ReviewReportGradeEntity> reportGradeList = this.findHql("from ReviewReportGradeEntity where reportId=?", new Object[]{reportId});
		if(reportGradeList.size() == 0) {
			for(int i=0; i<3; i++){
				reportGradeList.add(new ReviewReportGradeEntity());
			}
		}
		report.setReportGradeList(reportGradeList);
		return report;
	}

	@Override
	public List<ReviewReportEntity> getReportList(int page, int rows) {
		return this.findByQueryString("from ReviewReportEntity ORDER BY createTime DESC");
	}

	@Override
	public List<ReportVO> getReportVOList(String classId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("   v.`report_id` reportId,");
		sb.append("   v.`report_name` reportName ");
		sb.append(" FROM");
		sb.append("   review_report v ");

		List<ReportVO> list = this.getObjectList(sb.toString(), null, ReportVO.class);
		List<ReviewReportVariateEntity> reportVariateList = null;
		for(ReportVO report : list) {
			reportVariateList = this.findHql(" from ReviewReportVariateEntity where reportId=?", new Object[]{report.getReportId()});
			if(reportVariateList.size() == 0) {
				reportVariateList = new ArrayList<ReviewReportVariateEntity>();
				for(int i=0;i<8;i++) {
					reportVariateList.add(new ReviewReportVariateEntity());
				}
			}
			report.setReportVariateList(reportVariateList);
		}
		return list;
	}

	@Override
	public void addReportSet(ReportVO report) {
		List<ReviewReportVariateEntity> reportVariateList = report.getReportVariateList();
		String sql = "";
		sql = "delete from review_report_variate where report_id=?";
		this.executeSql(sql, new Object[]{report.getReportId()});
		List<ReviewReportVariateEntity> list = null;
		for(ReviewReportVariateEntity reportVariate : reportVariateList) {
			if(!"".equals(StringUtils.trimToEmpty(reportVariate.getVariateId()))) {
				list = this.findHql("from ReviewReportVariateEntity where variateId=? and reportId=?", new Object[]{reportVariate.getVariateId(),report.getReportId()});
				if(list.size() == 0) {
					reportVariate.setReportId(report.getReportId());
					this.save(reportVariate);
				}
			}
		}
	}

	@Override
	public List<ReviewReportVariateEntity> getReportVariateList(String reportId) {
		List<ReviewReportVariateEntity> reportVariateList = this.findHql(" from ReviewReportVariateEntity where reportId=?", new Object[]{reportId});
		int size = 8-reportVariateList.size();
		if(reportVariateList.size() <= 8) {
			for(int i=0; i < size; i++) {
				reportVariateList.add(new ReviewReportVariateEntity());
			}
		}
		return reportVariateList;
	}

	@Override
	public String getClassName(String reportId) {
		String sql = "select c.title as className from review_report r inner join review_class c on r.class_id=c.class_id where r.report_id=?";
		Map<String, Object> map = this.findOneForJdbc(sql, new Object[]{reportId});
		if(map.get("className") != null) {
			return map.get("className").toString();
		} else {
			return "";
		}
	}

	@Override
	public List<ReviewResultVO> getReviewResult(ReviewResultVO reviewResult) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT  ");
		sb.append("   r.`result_id` resultId,");
		sb.append("   c.`title` title,");
		sb.append("   DATE_FORMAT(r.`create_time`,'%Y-%m-%e %H:%i:%S') createTime,");
		sb.append("   r.`review_result` reviewResult,");
		sb.append("   r.`grade_total` gradeTotal ");
		sb.append(" FROM review_result r,review_class c   ");
		sb.append(" WHERE r.`class_id` = c.`class_id`");
		sb.append(" AND r.user_id = :userId ");
		sb.append(" AND r.project_id = :projectId ");
		sb.append(" ORDER BY r.`create_time` DESC");
		Map map = new HashMap<String, String>();
		if (reviewResult.getLimitId() != null && reviewResult.getLimitId() != 0){
			sb.append(" LIMIT :limitId , :pCount");
			map.put("limitId", reviewResult.getLimitId());
		}else {
			sb.append(" LIMIT :pCount");
		}
		map.put("userId", reviewResult.getUserId());
		map.put("projectId", reviewResult.getProjectId());
		map.put("pCount", reviewResult.getpCount());
		return this.getObjectList(sb.toString(),map,ReviewResultVO.class);
	}

	@Override
	public List<ReviewResultVO> getProjectReviewResult(ReviewResultVO reviewResult) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT  ");
		sb.append(" class_id classId, ");
		sb.append(" create_time createTime ");
		sb.append(" FROM ");
		sb.append(" review_result ");
		sb.append(" WHERE ");
		sb.append(" user_id = :userId ");
		sb.append(" AND project_id = :projectId ");
		sb.append(" ORDER BY create_time DESC ");
		Map map = new HashMap<String, String>();
		map.put("userId", reviewResult.getUserId());
		map.put("projectId", reviewResult.getProjectId());
		List<ReviewResultVO> result = this.getObjectList(sb.toString(),map,ReviewResultVO.class);
		//该人员该项目下测评总量表数
		int resultCount = result.size();
		//该项目测评次数
		int limit = (int) Math.ceil(resultCount / reviewResult.getpCount().intValue());
		List<ReviewResultVO> resultVOS = new ArrayList<>();
		for (int i = 0; i < limit; i++) {
			ReviewResultVO reviewResultVO = new ReviewResultVO();
			reviewResultVO.setLimitId(i * reviewResult.getpCount().intValue());
			reviewResultVO.setClassId(result.get(i * reviewResult.getpCount().intValue()).getClassId());
			reviewResultVO.setCreateTime(result.get(i * reviewResult.getpCount().intValue()).getCreateTime());
			resultVOS.add(reviewResultVO);
		}
		return resultVOS;
	}
}
