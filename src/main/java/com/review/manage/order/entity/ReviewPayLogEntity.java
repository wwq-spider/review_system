package com.review.manage.order.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 用户支付日志
 * @author zhangdaihao
 * @date 2021-11-23 17:43:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_pay_log", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewPayLogEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**订单id*/
	private Long orderId;
	/**用户id*/
	private String userId;
	/**订单号*/
	private Long orderNo;
	/**支付请求*/
	private String reqParam;
	/**支付请求响应*/
	private String prePayResp;
	/**回调响应*/
	private String callbackResp;
	/**ip地址*/
	private String ipAddr;
	/**操作类型*/
	private Integer operateType;
	/**浏览器*/
	private String broswer;
	/**操作时间*/
	private Date operateTime;
	/**操作人*/
	private String operator;
	/**扩展字段*/
	private String extra;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单id
	 */
	@Column(name ="ORDER_ID",nullable=true,precision=19,scale=0)
	public Long getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单id
	 */
	public void setOrderId(Long orderId){
		this.orderId = orderId;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单号
	 */
	@Column(name ="ORDER_NO",nullable=true,precision=19,scale=0)
	public Long getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单号
	 */
	public void setOrderNo(Long orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付请求
	 */
	@Column(name ="REQ_PARAM",nullable=true,length=500)
	public String getReqParam(){
		return this.reqParam;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付请求
	 */
	public void setReqParam(String reqParam){
		this.reqParam = reqParam;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付请求响应
	 */
	@Column(name ="PRE_PAY_RESP",nullable=true,length=500)
	public String getPrePayResp(){
		return this.prePayResp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付请求响应
	 */
	public void setPrePayResp(String prePayResp){
		this.prePayResp = prePayResp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  回调响应
	 */
	@Column(name ="CALLBACK_RESP",nullable=true,length=500)
	public String getCallbackResp(){
		return this.callbackResp;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  回调响应
	 */
	public void setCallbackResp(String callbackResp){
		this.callbackResp = callbackResp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ip地址
	 */
	@Column(name ="IP_ADDR",nullable=true,length=20)
	public String getIpAddr(){
		return this.ipAddr;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  ip地址
	 */
	public void setIpAddr(String ipAddr){
		this.ipAddr = ipAddr;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  操作类型
	 */
	@Column(name ="OPERATE_TYPE",nullable=true,precision=10,scale=0)
	public Integer getOperateType(){
		return this.operateType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  操作类型
	 */
	public void setOperateType(Integer operateType){
		this.operateType = operateType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  浏览器
	 */
	@Column(name ="BROSWER",nullable=true,length=100)
	public String getBroswer(){
		return this.broswer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  浏览器
	 */
	public void setBroswer(String broswer){
		this.broswer = broswer;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  扩展字段
	 */
	@Column(name ="EXTRA",nullable=true,length=1000)
	public String getExtra(){
		return this.extra;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  扩展字段
	 */
	public void setExtra(String extra){
		this.extra = extra;
	}
}
