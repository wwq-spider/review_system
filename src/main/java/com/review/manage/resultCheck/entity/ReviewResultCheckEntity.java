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
@Table(name="review_result_check")
public class ReviewResultCheckEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="check_id",nullable=false,length=32)
	private java.lang.String checkId;   
	
    @Column(name="check_type")
    private java.lang.String checkType;   
    @Column(name="check_title")
    private java.lang.String checkTitle;   
    @Column(name="create_time")
    private java.util.Date createTime;   
    @Column(name="create_by")
    private java.lang.String createBy;   
       
    public void setCheckId(java.lang.String checkId){  
        this.checkId=checkId;   
    }    
       
    public java.lang.String getCheckId(){    
        return this.checkId;    
    }    
       
    public void setCheckType(java.lang.String checkType){  
        this.checkType=checkType;   
    }    
       
    public java.lang.String getCheckType(){    
        return this.checkType;    
    }    
       
    public void setCheckTitle(java.lang.String checkTitle){  
        this.checkTitle=checkTitle;   
    }    
       
    public java.lang.String getCheckTitle(){    
        return this.checkTitle;    
    }    
       
    public void setCreateTime(java.util.Date createTime){  
        this.createTime=createTime;   
    }    
       
    public java.util.Date getCreateTime(){    
        return this.createTime;    
    }    
       
    public void setCreateBy(java.lang.String createBy){  
        this.createBy=createBy;   
    }    
       
    public java.lang.String getCreateBy(){    
        return this.createBy;    
    }    
       
} 