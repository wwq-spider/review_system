package com.review.manage.report.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @author yourname
 * 
 */
@Entity
@Table(name="review_report_grade")
public class ReviewReportGradeEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="report_grade_id",nullable=false,length=32)
	private String reportGradeId;
	
	@Column(name="report_id")
	private String reportId;
	
	@Column(name="grade_small")
    private Double gradeSmall;   
    
    @Column(name="result_explain")
    private String resultExplain;
    
    @Column(name="grade_big")
    private Double gradeBig;
    
    @Transient
    private CommonsMultipartFile file;
    
	public String getReportGradeId() {
		return reportGradeId;
	}
	public void setReportGradeId(String reportGradeId) {
		this.reportGradeId = reportGradeId;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public Double getGradeSmall() {
		return gradeSmall;
	}
	public void setGradeSmall(Double gradeSmall) {
		this.gradeSmall = gradeSmall;
	}
	public String getResultExplain() {
		return resultExplain;
	}
	public void setResultExplain(String resultExplain) {
		this.resultExplain = resultExplain;
	}
	public Double getGradeBig() {
		return gradeBig;
	}
	public void setGradeBig(Double gradeBig) {
		this.gradeBig = gradeBig;
	}
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
}
