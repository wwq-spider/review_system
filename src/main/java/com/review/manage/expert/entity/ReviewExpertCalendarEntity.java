package com.review.manage.expert.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评专家日历
 * @author zhangdaihao
 * @date 2021-10-21 11:42:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_expert_calendar", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewExpertCalendarEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**专家id*/
	private Long expertId;
	/**出诊日期*/
	private Date visitDate;
	/**周几*/
	private Integer weekDay;
	/**beginTime*/
	private Date beginTime;
	/**endTime*/
	private Date endTime;
	/**日历状态(1:已发布；2:未发布)*/
	private Integer status;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**创建人*/
	private String creator;
	
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
	 *方法: 设置java.lang.Long
	 *@param: java.lang.Long  id
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.Long
	 *@return: java.lang.Long  专家id
	 */
	@Column(name ="EXPERT_ID",nullable=true,precision=19,scale=0)
	public Long getExpertId(){
		return this.expertId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  专家id
	 */
	public void setExpertId(Long expertId){
		this.expertId = expertId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  出诊日期
	 */
	@Column(name ="VISIT_DATE",nullable=true)
	public Date getVisitDate(){
		return this.visitDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  出诊日期
	 */
	public void setVisitDate(Date visitDate){
		this.visitDate = visitDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  周几
	 */
	@Column(name ="WEEK_DAY",nullable=true,precision=10,scale=0)
	public Integer getWeekDay(){
		return this.weekDay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  周几
	 */
	public void setWeekDay(Integer weekDay){
		this.weekDay = weekDay;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  beginTime
	 */
	@Column(name ="BEGIN_TIME",nullable=true)
	public Date getBeginTime(){
		return this.beginTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  beginTime
	 */
	public void setBeginTime(Date beginTime){
		this.beginTime = beginTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  endTime
	 */
	@Column(name ="END_TIME",nullable=true)
	public Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  endTime
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  日历状态(1:已发布；2:未发布)
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  日历状态(1:已发布；2:未发布)
	 */
	public void setStatus(Integer status){
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
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATOR",nullable=true,length=50)
	public String getCreator(){
		return this.creator;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreator(String creator){
		this.creator = creator;
	}
}
