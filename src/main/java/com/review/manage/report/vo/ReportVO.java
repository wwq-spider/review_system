package com.review.manage.report.vo;

import java.util.ArrayList;
import java.util.List;

import com.review.manage.report.entity.ReviewReportGradeEntity;
import com.review.manage.report.entity.ReviewReportVariateEntity;

public class ReportVO {

	private String reportId;
	
	private String classId;
	
	private String reportName;
	
	private String createTime;
	
	private String crateBy;
	
	private Integer reportNum;
	
	private String curGradeId;

	private List<ReportVO> reportList = new ArrayList<ReportVO>();
	
	private Double grade;
	
	
	
	private List<ReviewReportGradeEntity> reportGradeList = new ArrayList<ReviewReportGradeEntity>();

	private List<ReviewReportVariateEntity> reportVariateList = new ArrayList<ReviewReportVariateEntity>();

	public List<ReviewReportVariateEntity> getReportVariateList() {
		return reportVariateList;
	}

	public void setReportVariateList(List<ReviewReportVariateEntity> reportVariateList) {
		this.reportVariateList = reportVariateList;
	}

	public List<ReportVO> getReportList() {
		return reportList;
	}


	public void setReportList(List<ReportVO> reportList) {
		this.reportList = reportList;
	}


	public String getReportId() {
		return reportId;
	}


	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getClassId() {
		return classId;
	}


	public void setClassId(String classId) {
		this.classId = classId;
	}


	public String getReportName() {
		return reportName;
	}


	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getCrateBy() {
		return crateBy;
	}


	public void setCrateBy(String crateBy) {
		this.crateBy = crateBy;
	}


	public Integer getReportNum() {
		return reportNum;
	}


	public void setReportNum(Integer reportNum) {
		this.reportNum = reportNum;
	}


	public List<ReviewReportGradeEntity> getReportGradeList() {
		return reportGradeList;
	}


	public void setReportGradeList(List<ReviewReportGradeEntity> reportGradeList) {
		this.reportGradeList = reportGradeList;
	}

	public String getCurGradeId() {
		return curGradeId;
	}

	public void setCurGradeId(String curGradeId) {
		this.curGradeId = curGradeId;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}
}
