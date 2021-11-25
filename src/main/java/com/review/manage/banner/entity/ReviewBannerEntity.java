package com.review.manage.banner.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: banner轮播图
 * @author zhangdaihao
 * @date 2021-11-25 11:24:24
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_banner", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewBannerEntity implements java.io.Serializable {
	/**id*/
	private Integer id;
	/**title*/
	private String title;
	/**imgUrl*/
	private String imgUrl;
	/**目标跳转url*/
	private String targetUrl;
	/**状态*/
	private Integer status;
	/**createTime*/
	private Date createTime;
	/**operateTime*/
	private Date operateTime;
	/**operator*/
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
	 *@return: java.lang.String  title
	 */
	@Column(name ="TITLE",nullable=true,length=50)
	public String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  imgUrl
	 */
	@Column(name ="IMG_URL",nullable=true,length=200)
	public String getImgUrl(){
		return this.imgUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  imgUrl
	 */
	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  目标跳转url
	 */
	@Column(name ="TARGET_URL",nullable=true,length=20)
	public String getTargetUrl(){
		return this.targetUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  目标跳转url
	 */
	public void setTargetUrl(String targetUrl){
		this.targetUrl = targetUrl;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  状态
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  状态
	 */
	public void setStatus(Integer status){
		this.status = status;
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
	 *@return: java.util.Date  operateTime
	 */
	@Column(name ="OPERATE_TIME",nullable=true)
	public Date getOperateTime(){
		return this.operateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  operateTime
	 */
	public void setOperateTime(Date operateTime){
		this.operateTime = operateTime;
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
}
