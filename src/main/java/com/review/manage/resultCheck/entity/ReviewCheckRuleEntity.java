package com.review.manage.resultCheck.entity; 
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
@Table(name="review_check_rule")
public class ReviewCheckRuleEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="check_rule_id",nullable=false,length=32)
	private java.lang.String checkRuleId;   
	
    @Column(name="check_id")
    private java.lang.String checkId;   
    @Column(name="variate_id")
    private java.lang.String variateId;   
    @Column(name="weight")
    private java.lang.Integer weight;   
    @Column(name="rule_explain")
    private java.lang.String ruleExplain;   
    @Column(name="cal_symbol")
    private java.lang.String calSymbol;   
       
    public void setCheckRuleId(java.lang.String checkRuleId){  
        this.checkRuleId=checkRuleId;   
    }    
       
    public java.lang.String getCheckRuleId(){    
        return this.checkRuleId;    
    }    
       
    public void setCheckId(java.lang.String checkId){  
        this.checkId=checkId;   
    }    
       
    public java.lang.String getCheckId(){    
        return this.checkId;    
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
       
    public void setRuleExplain(java.lang.String ruleExplain){  
        this.ruleExplain=ruleExplain;   
    }    
       
    public java.lang.String getRuleExplain(){    
        return this.ruleExplain;    
    }    
       
    public void setCalSymbol(java.lang.String calSymbol){  
        this.calSymbol=calSymbol;   
    }    
       
    public java.lang.String getCalSymbol(){    
        return this.calSymbol;    
    }    
       
} 