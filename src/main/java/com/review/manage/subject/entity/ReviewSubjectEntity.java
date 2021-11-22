package com.review.manage.subject.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评主题
 * @author zhangdaihao
 * @date 2021-11-12 19:03:18
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_subject", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewSubjectEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**专题名称*/
	private String subjectName;
	/**专题描述*/
	private String subjectDesc;
	/**专题状态*/
	private Integer status;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**操作者*/
	private String operator;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=19,scale=0)
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题名称
	 */
	@Column(name ="SUBJECT_NAME",nullable=true,length=50)
	public String getSubjectName(){
		return this.subjectName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题名称
	 */
	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专题描述
	 */
	@Column(name ="SUBJECT_DESC",nullable=true,length=200)
	public String getSubjectDesc(){
		return this.subjectDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专题描述
	 */
	public void setSubjectDesc(String subjectDesc){
		this.subjectDesc = subjectDesc;
	}

	/**
	 * 专题状态
	 * @return
	 */
	@Column(name ="STATUS",nullable=true)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="UPDATE_TIME",nullable=true)
	public Date getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作者
	 */
	@Column(name ="OPERATOR",nullable=true,length=50)
	public String getOperator(){
		return this.operator;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作者
	 */
	public void setOperator(String operator){
		this.operator = operator;
	}
}
