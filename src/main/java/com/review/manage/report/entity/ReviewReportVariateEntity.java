package com.review.manage.report.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author yourname
 * 
 */
@Entity
@Table(name="review_report_variate")
public class ReviewReportVariateEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="report_variate_id",nullable=false,length=32)
	private String reportVariateId;
	
	@Column(name="report_id")
	private String reportId;
	
	@Column(name="variate_id")
	private String variateId;
	
	@Column(name="weight")
    private java.lang.Integer weight;   
    @Column(name="rule_explain")
    private java.lang.String ruleExplain;   
    @Column(name="cal_symbol")
    private java.lang.String calSymbol;

    
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportVariateId() {
		return reportVariateId;
	}
	public void setReportVariateId(String reportVariateId) {
		this.reportVariateId = reportVariateId;
	}
	public String getVariateId() {
		return variateId;
	}
	public void setVariateId(String variateId) {
		this.variateId = variateId;
	}
	public java.lang.Integer getWeight() {
		return weight;
	}
	public void setWeight(java.lang.Integer weight) {
		this.weight = weight;
	}
	public java.lang.String getRuleExplain() {
		return ruleExplain;
	}
	public void setRuleExplain(java.lang.String ruleExplain) {
		this.ruleExplain = ruleExplain;
	}
	public java.lang.String getCalSymbol() {
		return calSymbol;
	}
	public void setCalSymbol(java.lang.String calSymbol) {
		this.calSymbol = calSymbol;
	}  
    
    
}
