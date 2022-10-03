package com.review.manage.variate.entity; 
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
@Table(name="review_variate_grade")
public class ReviewVariateGradeEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="variate_grade_id",nullable=false,length=32)
	private java.lang.String variateGradeId;   
	
 	@Column(name="variate_id")
 	private String variateId;
 	
    @Column(name="grade_samll")
    private Double gradeSmall;   
    
    @Column(name="result_explain")
    private String resultExplain;
    
    @Column(name="grade_big")
    private Double gradeBig;

	@Column(name="level_grade")
	private Integer levelGrade;
    
    @Transient
    private CommonsMultipartFile file;
    
    public void setVariateId(java.lang.String variateId){  
        this.variateId=variateId;   
    }    
       
    public java.lang.String getVariateId(){    
        return this.variateId;    
    }

	public java.lang.String getVariateGradeId() {
		return variateGradeId;
	}

	public void setVariateGradeId(java.lang.String variateGradeId) {
		this.variateGradeId = variateGradeId;
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

	public Integer getLevelGrade() {
		return levelGrade;
	}

	public void setLevelGrade(Integer levelGrade) {
		this.levelGrade = levelGrade;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}    
       
   
} 