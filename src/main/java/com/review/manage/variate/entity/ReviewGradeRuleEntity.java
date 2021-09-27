package com.review.manage.variate.entity; 
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
@Table(name="review_grade_rule")
public class ReviewGradeRuleEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="rule_id",nullable=false,length=32)
	private java.lang.String ruleId;   
	
    @Column(name="question_id")
    private java.lang.Integer questionId;   
    @Column(name="variate_id")
    private java.lang.String variateId;   
    @Column(name="weight")
    private java.lang.Integer weight;   
    @Column(name="cal_symbol")
    private java.lang.String calSymbol;   
       
    public void setRuleId(java.lang.String ruleId){  
        this.ruleId=ruleId;   
    }    
       
    public java.lang.String getRuleId(){    
        return this.ruleId;    
    }    
       
    public void setQuestionId(java.lang.Integer questionId){  
        this.questionId=questionId;   
    }    
       
    public java.lang.Integer getQuestionId(){    
        return this.questionId;    
    }    
       
    public void setVariateId(java.lang.String variateId){  
        this.variateId=variateId;   
    }    
       
    public java.lang.String getVariateId(){    
        return this.variateId;    
    }    
       
    public void setWeight(java.lang.Integer weight){  
        this.weight=weight;   
    }    
       
    public java.lang.Integer getWeight(){    
        return this.weight;    
    }    
       
    public void setCalSymbol(java.lang.String calSymbol){  
        this.calSymbol=calSymbol;   
    }    
       
    public java.lang.String getCalSymbol(){    
        return this.calSymbol;    
    }    
       
} 