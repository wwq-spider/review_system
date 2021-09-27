package com.review.manage.variate.vo;

import java.util.ArrayList;
import java.util.List;

import com.review.manage.variate.entity.ReviewGradeRuleEntity;
import com.review.manage.variate.entity.ReviewVariateGradeEntity;


public class VariateVO{

	private String variateId;   

 	private String classId;

    private String variateName;   

    private String ruleExplain;
   
    private String questionIds;
    
    private String createTime;   

    private String curGradeId;
    
    private String createBy;

    private Integer variateNum;
    
    private Double calTotal;
    
    private String calSymbol;
    
    private Double calTotal1;
    
    private String calSymbol1;
    
    private Double grade;
    

    private List<ReviewVariateGradeEntity> variateGradeList = new ArrayList<ReviewVariateGradeEntity>();
    
    private List<VariateVO> variateList = new ArrayList<VariateVO>();
    
    private List<ReviewGradeRuleEntity> gradeRuleList = new ArrayList<ReviewGradeRuleEntity>();
    
	public List<ReviewGradeRuleEntity> getGradeRuleList() {
		return gradeRuleList;
	}

	public void setGradeRuleList(List<ReviewGradeRuleEntity> gradeRuleList) {
		this.gradeRuleList = gradeRuleList;
	}

	public List<VariateVO> getVariateList() {
		return variateList;
	}

	public void setVariateList(List<VariateVO> variateList) {
		this.variateList = variateList;
	}

	public List<ReviewVariateGradeEntity> getVariateGradeList() {
		return variateGradeList;
	}

	public void setVariateGradeList(List<ReviewVariateGradeEntity> variateGradeList) {
		this.variateGradeList = variateGradeList;
	}

	public java.lang.String getVariateId() {
		return variateId;
	}

	public void setVariateId(java.lang.String variateId) {
		this.variateId = variateId;
	}

	public String getCurGradeId() {
		return curGradeId;
	}

	public void setCurGradeId(String curGradeId) {
		this.curGradeId = curGradeId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public java.lang.String getVariateName() {
		return variateName;
	}

	public void setVariateName(java.lang.String variateName) {
		this.variateName = variateName;
	}

	public String getRuleExplain() {
		return ruleExplain;
	}

	public void setRuleExplain(String ruleExplain) {
		this.ruleExplain = ruleExplain;
	}

	public String getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	public java.lang.String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getVariateNum() {
		return variateNum;
	}

	public void setVariateNum(Integer variateNum) {
		this.variateNum = variateNum;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Double getCalTotal() {
		return calTotal;
	}

	public void setCalTotal(Double calTotal) {
		this.calTotal = calTotal;
	}

	public String getCalSymbol() {
		return calSymbol;
	}

	public void setCalSymbol(String calSymbol) {
		this.calSymbol = calSymbol;
	}

	public Double getCalTotal1() {
		return calTotal1;
	}

	public void setCalTotal1(Double calTotal1) {
		this.calTotal1 = calTotal1;
	}

	public String getCalSymbol1() {
		return calSymbol1;
	}

	public void setCalSymbol1(String calSymbol1) {
		this.calSymbol1 = calSymbol1;
	}


	public int compare(VariateVO o1) {
		
		return o1.getVariateNum().compareTo(this.getVariateNum());
	}
    
}
