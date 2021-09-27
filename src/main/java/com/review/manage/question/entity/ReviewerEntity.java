package com.review.manage.question.entity; 
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 测评人实体类
 * @author wwq
 * 
 */
@Entity
@Table(name="reviewer")
public class ReviewerEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="reviewer_id",nullable=false,length=32)
	private java.lang.String reviewerId;   
	
    @Column(name="id_card")
    private java.lang.String idCard;   
    @Column(name="name")
    private java.lang.String name;   
    @Column(name="age")
    private java.lang.Integer age;   
    @Column(name="sex")
    private java.lang.String sex;   
    @Column(name="mobile_phone")
    private java.lang.String mobilePhone;   
       
    public void setReviewerId(java.lang.String reviewerId){  
        this.reviewerId=reviewerId;   
    }    
       
    public java.lang.String getReviewerId(){    
        return this.reviewerId;    
    }    
       
    public void setIdCard(java.lang.String idCard){  
        this.idCard=idCard;   
    }    
       
    public java.lang.String getIdCard(){    
        return this.idCard;    
    }    
       
    public void setName(java.lang.String name){  
        this.name=name;   
    }    
       
    public java.lang.String getName(){    
        return this.name;    
    }    
       
    public void setAge(java.lang.Integer age){  
        this.age=age;   
    }    
       
    public java.lang.Integer getAge(){    
        return this.age;    
    }    
       
    public void setSex(java.lang.String sex){  
        this.sex=sex;   
    }    
       
    public java.lang.String getSex(){    
        return this.sex;    
    }    
       
    public void setMobilePhone(java.lang.String mobilePhone){  
        this.mobilePhone=mobilePhone;   
    }    
       
    public java.lang.String getMobilePhone(){    
        return this.mobilePhone;    
    }    
       
} 