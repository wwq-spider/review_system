package com.review.manage.reviewClass.entity; 
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
@Table(name="review_class")
public class ReviewClassEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="class_id",nullable=false,length=32)
	private java.lang.String classId;   
	
 	@Column(name="sort_id")
 	private Integer sortId;
 	
    @Column(name="title")
    private java.lang.String title;   
    @Column(name="status")
    private java.lang.Integer status;   
    @Column(name="guide")
    private java.lang.String guide;   
    @Column(name="create_time")
    private java.util.Date createTime;   
    @Column(name="create_by")
    private java.lang.String createBy;      
    
    public void setClassId(java.lang.String classId){  
        this.classId=classId;   
    }    
       
    public java.lang.String getClassId(){    
        return this.classId;    
    }    
       
    public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public void setTitle(java.lang.String title){  
        this.title=title;   
    }    
       
    public java.lang.String getTitle(){    
        return this.title;    
    }    
       
    public void setStatus(java.lang.Integer status){  
        this.status=status;   
    }    
       
    public java.lang.Integer getStatus(){    
        return this.status;    
    }    
       
    public void setGuide(java.lang.String guide){  
        this.guide=guide;   
    }    
       
    public java.lang.String getGuide(){    
        return this.guide;    
    }

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}    
       
} 