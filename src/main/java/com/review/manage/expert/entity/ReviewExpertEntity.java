package com.review.manage.expert.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评专家模块
 * @author zhangdaihao
 * @date 2021-10-21 11:38:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_expert", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewExpertEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**left*/
	private String expertName;
	/**性别*/
	private Integer sex;
	/**年龄*/
	private Integer age;
	/**关联测评用户id*/
	private String userId;
	/**专家手机号*/
	private String mobilePhone;
	/**职称*/
	private String jobTitle;
	/**专家介绍*/
	private String introduction;
	/**工作机构名称*/
	private String workOrgName;
	/**专家标签*/
	private String label;
	/**头像*/
	private String avatar;
	/**专家状态**/
	private Integer status;
	/**排序**/
	private Integer sortNum;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**创建人*/
	private String creator;

	private Integer charge;

	private BigDecimal orgPrice;

	private BigDecimal dicountPrice;
	
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
	 *@return: java.lang.String  专家姓名
	 */
	@Column(name ="EXPERT_NAME",nullable=true,length=50)
	public String getExpertName(){
		return this.expertName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专家姓名
	 */
	public void setExpertName(String expertName){
		this.expertName = expertName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  性别
	 */
	@Column(name ="SEX",nullable=true,precision=10,scale=0)
	public Integer getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  性别
	 */
	public void setSex(Integer sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  年龄
	 */
	@Column(name ="AGE",nullable=true,precision=10,scale=0)
	public Integer getAge(){
		return this.age;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  年龄
	 */
	public void setAge(Integer age){
		this.age = age;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联测评用户id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联测评用户id
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专家手机号
	 */
	@Column(name ="MOBILE_PHONE",nullable=true,length=20)
	public String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专家手机号
	 */
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职称
	 */
	@Column(name ="JOB_TITLE",nullable=true,length=20)
	public String getJobTitle(){
		return this.jobTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职称
	 */
	public void setJobTitle(String jobTitle){
		this.jobTitle = jobTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专家介绍
	 */
	@Column(name ="INTRODUCTION",nullable=true,length=500)
	public String getIntroduction(){
		return this.introduction;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专家介绍
	 */
	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作机构名称
	 */
	@Column(name ="WORK_ORG_NAME",nullable=true,length=100)
	public String getWorkOrgName(){
		return this.workOrgName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作机构名称
	 */
	public void setWorkOrgName(String workOrgName){
		this.workOrgName = workOrgName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  专家标签
	 */
	@Column(name ="LABEL",nullable=true,length=100)
	public String getLabel(){
		return this.label;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  专家标签
	 */
	public void setLabel(String label){
		this.label = label;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  头像
	 */
	@Column(name ="AVATAR",nullable=true,length=200)
	public String getAvatar(){
		return this.avatar;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  头像
	 */
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	@Column(name ="STATUS",nullable=true,length=1)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name ="SORT_NUM", nullable=true, length=1)
	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
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

	@Column(name ="charge",nullable=true,length=1)
	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	@Column(name ="org_price",nullable=true,length=20)
	public BigDecimal getOrgPrice() {
		return orgPrice;
	}

	public void setOrgPrice(BigDecimal orgPrice) {
		this.orgPrice = orgPrice;
	}

	@Column(name ="dicount_price",nullable=true,length=20)
	public BigDecimal getDicountPrice() {
		return dicountPrice;
	}

	public void setDicountPrice(BigDecimal dicountPrice) {
		this.dicountPrice = dicountPrice;
	}
}
