package com.review.manage.record.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 量表测评记录
 * @author zhangdaihao
 * @date 2021-12-18 15:21:29
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_record", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewRecordEntity implements java.io.Serializable {
	/**主键id*/
	private Long id;
	/**项目id*/
	private Long projectId;
	/**测评类型*/
	private Integer reviewType;
	/**用户id*/
	private String userId;
	/**用户组id*/
	private String groupId;
	/**测评分类id*/
	private String classId;
	/**测评状态(0:测评中；1:已完成)*/
	private Integer status;
	/**创建时间*/
	private Date createTime;
	/**创建人*/
	private String creator;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=19,scale=0)
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键id
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  项目id
	 */
	@Column(name ="PROJECT_ID",nullable=true,precision=19,scale=0)
	public Long getProjectId(){
		return this.projectId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  项目id
	 */
	public void setProjectId(Long projectId){
		this.projectId = projectId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  测评类型
	 */
	@Column(name ="REVIEW_TYPE",nullable=true,precision=10,scale=0)
	public Integer getReviewType(){
		return this.reviewType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  测评类型
	 */
	public void setReviewType(Integer reviewType){
		this.reviewType = reviewType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户组id
	 */
	@Column(name ="GROUP_ID",nullable=true,length=32)
	public String getGroupId(){
		return this.groupId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户组id
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  测评分类id
	 */
	@Column(name ="CLASS_ID",nullable=true,length=32)
	public String getClassId(){
		return this.classId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  测评分类id
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  测评状态(0:测评中；1:已完成)
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  测评状态(0:测评中；1:已完成)
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
