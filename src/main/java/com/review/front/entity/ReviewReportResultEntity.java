package com.review.front.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="review_report_result")
public class ReviewReportResultEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="result_report_id",nullable=false,length=32)
	private String reportResultId;
	
	@Column(name="report_id")
	private String reportId;
	
	@Column(name="result_id")
	private String resultId;
	
	@Column(name="explain_result")
	private String explainResult;

	@Column(name="report_name")
	private String reportName;
	
	@Column(name="grade")
	private Double grade;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="result_type")
	private String resultType;
	
	public String getReportResultId() {
		return reportResultId;
	}

	public void setReportResultId(String reportResultId) {
		this.reportResultId = reportResultId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getExplainResult() {
		return explainResult;
	}

	public void setExplainResult(String explainResult) {
		this.explainResult = explainResult;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}


}
