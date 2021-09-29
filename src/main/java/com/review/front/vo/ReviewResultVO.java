package com.review.front.vo;


import java.io.Serializable;

public class ReviewResultVO implements Serializable {

	private String classId;

	private String classTitle;

	private String createTime;

	private String classCover;

	private String resultId;
	
	private String reportName;
	
	private String reportResult;
	
	private String reportGrade;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassTitle() {
		return classTitle;
	}

	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClassCover() {
		return classCover;
	}

	public void setClassCover(String classCover) {
		this.classCover = classCover;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportResult() {
		return reportResult;
	}

	public void setReportResult(String reportResult) {
		this.reportResult = reportResult;
	}

	public String getReportGrade() {
		return reportGrade;
	}

	public void setReportGrade(String reportGrade) {
		this.reportGrade = reportGrade;
	}
	
	
}
