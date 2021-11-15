package com.review.manage.subject.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评主题量表关联
 * @author zhangdaihao
 * @date 2021-11-12 19:21:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_subject_class", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewSubjectClassEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**分类id*/
	private String classId;
	/**测评专题id*/
	private Long subjectId;
	/**操作时间*/
	private Date operateTime;
	
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
	 *@return: java.lang.String  分类id
	 */
	@Column(name ="CLASS_ID",nullable=true,length=32)
	public String getClassId(){
		return this.classId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类id
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  测评专题id
	 */
	@Column(name ="SUBJECT_ID",nullable=true,precision=19,scale=0)
	public Long getSubjectId(){
		return this.subjectId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  测评专题id
	 */
	public void setSubjectId(Long subjectId){
		this.subjectId = subjectId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  操作时间
	 */
	@Column(name ="OPERATE_TIME",nullable=true)
	public Date getOperateTime(){
		return this.operateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  操作时间
	 */
	public void setOperateTime(Date operateTime){
		this.operateTime = operateTime;
	}
}
