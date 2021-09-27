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
@Table(name="review_variate")
public class ReviewVariateEntity implements Serializable{


	private static final long serialVersionUID = 1L;
	
 	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="variate_id",nullable=false,length=32)
	private java.lang.String variateId;   
	
 	@Column(name="class_id")
 	private String classId;
 	
    @Column(name="variate_name")
    private java.lang.String variateName;   
    
    @Column(name="cal_symbol")
    private String calSymbol;
    
    @Column(name="cal_total")
    private Double calTotal;
    
    @Column(name="cal_symbol1")
    private String calSymbol1;
    
    @Column(name="cal_total1")
    private Double calTotal1;
    
    @Column(name="cur_grade_id")
    private String curGradeId;
    
    @Column(name="create_time")
    private java.util.Date createTime;   
    
    @Column(name="create_by")
    private java.lang.String createBy; 
    
    @Column(name="sort_num")
    private Integer sortNum;
    
    public void setVariateId(java.lang.String variateId){  
        this.variateId=variateId;   
    }    
       
    public java.lang.String getVariateId(){    
        return this.variateId;    
    }    
       
    public void setVariateName(java.lang.String variateName){  
        this.variateName=variateName;   
    }    
       
    public java.lang.String getVariateName(){    
        return this.variateName;    
    }

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getCurGradeId() {
		return curGradeId;
	}

	public void setCurGradeId(String curGradeId) {
		this.curGradeId = curGradeId;
	}

	public String getCalSymbol() {
		return calSymbol;
	}

	public void setCalSymbol(String calSymbol) {
		this.calSymbol = calSymbol;
	}

	public Double getCalTotal() {
		return calTotal;
	}

	public void setCalTotal(Double calTotal) {
		this.calTotal = calTotal;
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

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getCalSymbol1() {
		return calSymbol1;
	}

	public void setCalSymbol1(String calSymbol1) {
		this.calSymbol1 = calSymbol1;
	}

	public Double getCalTotal1() {
		return calTotal1;
	}

	public void setCalTotal1(Double calTotal1) {
		this.calTotal1 = calTotal1;
	}
} 