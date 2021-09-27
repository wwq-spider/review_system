package com.review.manage.question.entity; 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 测评记录实体类
 * @author yourname
 * 
 */
@Entity
@Table(name="review_record")
public class ReviewRecordEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="record_id",nullable=false,length=32)
	private java.lang.String recordId;   
	
    @Column(name="reviewer_id")
    private java.lang.String reviewerId;  
    @Column(name="review_type")
    private java.lang.String reviewType;
    @Column(name="review_grade")
    private java.lang.Double reviewGrade;   
    @Column(name="review_result")
    private java.lang.String reviewResult;   
    @Column(name="physical_feature")
    private java.lang.String physicalFeature;   
    @Column(name="moral_feature")
    private java.lang.String moralFeature;   
    @Column(name="mixed_feature")
    private java.lang.String mixedFeature;   
       
    public void setRecordId(java.lang.String recordId){  
        this.recordId=recordId;   
    }    
       
    public java.lang.String getRecordId(){    
        return this.recordId;    
    }    
       
    public void setReviewerId(java.lang.String reviewerId){  
        this.reviewerId=reviewerId;   
    }    
       
    public java.lang.String getReviewerId(){    
        return this.reviewerId;    
    }    
       
    public java.lang.String getReviewType() {
		return reviewType;
	}

	public void setReviewType(java.lang.String reviewType) {
		this.reviewType = reviewType;
	}

	public void setReviewGrade(java.lang.Double reviewGrade){  
        this.reviewGrade=reviewGrade;   
    }    
       
    public java.lang.Double getReviewGrade(){    
        return this.reviewGrade;    
    }    
       
    public void setReviewResult(java.lang.String reviewResult){  
        this.reviewResult=reviewResult;   
    }    
       
    public java.lang.String getReviewResult(){    
        return this.reviewResult;    
    }    
       
    public void setPhysicalFeature(java.lang.String physicalFeature){  
        this.physicalFeature=physicalFeature;   
    }    
       
    public java.lang.String getPhysicalFeature(){    
        return this.physicalFeature;    
    }    
       
    public void setMoralFeature(java.lang.String moralFeature){  
        this.moralFeature=moralFeature;   
    }    
       
    public java.lang.String getMoralFeature(){    
        return this.moralFeature;    
    }    
       
    public void setMixedFeature(java.lang.String mixedFeature){  
        this.mixedFeature=mixedFeature;   
    }    
       
    public java.lang.String getMixedFeature(){    
        return this.mixedFeature;    
    }    
       
} 