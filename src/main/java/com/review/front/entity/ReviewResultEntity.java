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
@Table(name="review_result")
public class ReviewResultEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="result_id",nullable=false,length=32)
	private String resultId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="class_id")
	private String classId;

	@Column(name="project_id")
	private Long projectId;
	
	@Column(name="grade_total")
	private Double gradeTotal;
	
	@Column(name="review_result")
	private String reviewResult;

	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_by")
	private String createBy;

	@Column(name="level_grade")
	private Integer levelGrade;

	@Column(name="combine_result")
	private String combineVarResult;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public String getReviewResult() {
		return reviewResult;
	}

	public void setReviewResult(String reviewResult) {
		this.reviewResult = reviewResult;
	}

	public Integer getLevelGrade() {
		return levelGrade;
	}

	public void setLevelGrade(Integer levelGrade) {
		this.levelGrade = levelGrade;
	}

	public String getCombineVarResult() {
		return combineVarResult;
	}

	public void setCombineVarResult(String combineVarResult) {
		this.combineVarResult = combineVarResult;
	}
}
