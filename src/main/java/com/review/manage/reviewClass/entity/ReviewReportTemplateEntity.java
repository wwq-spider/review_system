package com.review.manage.reviewClass.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评量表报告模板
 * @author zhangdaihao
 * @date 2022-05-06 22:01:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_report_template", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewReportTemplateEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**量表id*/
	private String classId;
	/**标题*/
	private String title;
	/**描述*/
	private String explanation;
	/**排序号码*/
	private Integer orderNum;
	/**操作时间*/
	private Date operateTime;
	/**操作人*/
	private String operator;
	
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
	 *@return: java.lang.String  量表id
	 */
	@Column(name ="CLASS_ID",nullable=true,length=32)
	public String getClassId(){
		return this.classId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  量表id
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=50)
	public String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  描述
	 */
	@Column(name ="EXPLANATION",nullable=true,length=500)
	public String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  描述
	 */
	public void setExplanation(String explanation){
		this.explanation = explanation;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  排序号码
	 */
	@Column(name ="ORDER_NUM",nullable=true,precision=10,scale=0)
	public Integer getOrderNum(){
		return this.orderNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  排序号码
	 */
	public void setOrderNum(Integer orderNum){
		this.orderNum = orderNum;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人
	 */
	@Column(name ="OPERATOR",nullable=true,length=50)
	public String getOperator(){
		return this.operator;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人
	 */
	public void setOperator(String operator){
		this.operator = operator;
	}
}
