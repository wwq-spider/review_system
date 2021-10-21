package com.review.manage.expert.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评专家预约
 * @author zhangdaihao
 * @date 2021-10-21 11:42:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_expert_reserve", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewExpertReserveEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**专家id*/
	private Integer expertId;
	/**测评用户id*/
	private String userId;
	/**日历id*/
	private Integer calendarId;
	/**预约状态(1:待问诊；2:问诊结束；3:取消预约)*/
	private Integer status;
	/**预约类型(1:专家视频问诊；2:专家电话问诊)*/
	private Integer type;
	/**就诊人姓名*/
	private String patientName;
	/**就诊人性别*/
	private Integer patientSex;
	/**就诊人年龄*/
	private Integer patientAge;
	/**就诊附件*/
	private String attachFiles;
	/**备注*/
	private String note;
	/**删除标记：(1:未删除；2:已删除)*/
	private Integer delFlag;
	/**创建时间*/
	private Date createTime;
	/**房间id*/
	private Integer roomId;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=10,scale=0)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  专家id
	 */
	@Column(name ="EXPERT_ID",nullable=true,precision=19,scale=0)
	public Integer getExpertId(){
		return this.expertId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  专家id
	 */
	public void setExpertId(Integer expertId){
		this.expertId = expertId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  测评用户id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  测评用户id
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  日历id
	 */
	@Column(name ="CALENDAR_ID",nullable=true,precision=19,scale=0)
	public Integer getCalendarId(){
		return this.calendarId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  日历id
	 */
	public void setCalendarId(Integer calendarId){
		this.calendarId = calendarId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  预约状态(1:待问诊；2:问诊结束；3:取消预约)
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  预约状态(1:待问诊；2:问诊结束；3:取消预约)
	 */
	public void setStatus(Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  预约类型(1:专家视频问诊；2:专家电话问诊)
	 */
	@Column(name ="TYPE",nullable=true,precision=10,scale=0)
	public Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  预约类型(1:专家视频问诊；2:专家电话问诊)
	 */
	public void setType(Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  就诊人姓名
	 */
	@Column(name ="PATIENT_NAME",nullable=true,length=50)
	public String getPatientName(){
		return this.patientName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  就诊人姓名
	 */
	public void setPatientName(String patientName){
		this.patientName = patientName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  就诊人性别
	 */
	@Column(name ="PATIENT_SEX",nullable=true,precision=10,scale=0)
	public Integer getPatientSex(){
		return this.patientSex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  就诊人性别
	 */
	public void setPatientSex(Integer patientSex){
		this.patientSex = patientSex;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  就诊人年龄
	 */
	@Column(name ="PATIENT_AGE",nullable=true,precision=10,scale=0)
	public Integer getPatientAge(){
		return this.patientAge;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  就诊人年龄
	 */
	public void setPatientAge(Integer patientAge){
		this.patientAge = patientAge;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  就诊附件
	 */
	@Column(name ="ATTACH_FILES",nullable=true,length=500)
	public String getAttachFiles(){
		return this.attachFiles;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  就诊附件
	 */
	public void setAttachFiles(String attachFiles){
		this.attachFiles = attachFiles;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=200)
	public String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  删除标记：(1:未删除；2:已删除)
	 */
	@Column(name ="DEL_FLAG",nullable=true,precision=10,scale=0)
	public Integer getDelFlag(){
		return this.delFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  删除标记：(1:未删除；2:已删除)
	 */
	public void setDelFlag(Integer delFlag){
		this.delFlag = delFlag;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  房间id
	 */
	@Column(name ="ROOM_ID",nullable=true,precision=19,scale=0)
	public Integer getRoomId(){
		return this.roomId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  房间id
	 */
	public void setRoomId(Integer roomId){
		this.roomId = roomId;
	}
}
