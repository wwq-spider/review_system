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

	private String userId;

	private String idCard;

	private String userName;

	private String realName;

	private String combineVarResult;

	private Integer levelGrade;

	private Long projectId;

	private Double gradeTotal;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCombineVarResult() {
		return combineVarResult;
	}

	public void setCombineVarResult(String combineVarResult) {
		this.combineVarResult = combineVarResult;
	}

	public Integer getLevelGrade() {
		return levelGrade;
	}

	public void setLevelGrade(Integer levelGrade) {
		this.levelGrade = levelGrade;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Double getGradeTotal() {
		return gradeTotal;
	}

	public void setGradeTotal(Double gradeTotal) {
		this.gradeTotal = gradeTotal;
	}
}
