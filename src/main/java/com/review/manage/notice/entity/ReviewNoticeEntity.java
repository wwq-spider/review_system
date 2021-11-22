package com.review.manage.notice.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 公告
 * @author zhangdaihao
 * @date 2021-11-16 15:16:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_notice", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewNoticeEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**noticeName*/
	private String noticeName;
	/**noticeDesc*/
	private String noticeDesc;
	/**公告状态*/
	private Integer status;
	/**operator*/
	private String operator;
	/**createTime*/
	private Date createTime;
	/**updateTime*/
	private Date updateTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=19,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  noticeName
	 */
	@Column(name ="NOTICE_NAME",nullable=true,length=50)
	public String getNoticeName(){
		return this.noticeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  noticeName
	 */
	public void setNoticeName(String noticeName){
		this.noticeName = noticeName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  noticeDesc
	 */
	@Column(name ="NOTICE_DESC",nullable=true,length=1000)
	public String getNoticeDesc(){
		return this.noticeDesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  noticeDesc
	 */
	public void setNoticeDesc(String noticeDesc){
		this.noticeDesc = noticeDesc;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  公告状态
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  公告状态
	 */
	public void setStatus(Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  operator
	 */
	@Column(name ="OPERATOR",nullable=true,length=50)
	public String getOperator(){
		return this.operator;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  operator
	 */
	public void setOperator(String operator){
		this.operator = operator;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createTime
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createTime
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  updateTime
	 */
	@Column(name ="UPDATE_TIME",nullable=true)
	public Date getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  updateTime
	 */
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
}
