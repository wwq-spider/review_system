package com.review.manage.order.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 公告
 * @author zhangdaihao
 * @date 2021-11-22 15:20:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_order", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewOrderEntity implements java.io.Serializable {
	/**主键*/
	private Long id;
	/**用户id*/
	private String userId;
	/**测评量表id*/
	private String classId;
	/**量表名称*/
	private String className;
	/**订单实付金额*/
	private BigDecimal orderAmount;
	/**原始价格*/
	private BigDecimal orgAmount;
	/**订单号*/
	private Long orderNo;
	/**订单状态*/
	private Integer status;
	/**支付状态码*/
	private String payResultCode;
	/**支付状态描述*/
	private String payResultMsg;
	/**外部支付id*/
	private String payId;
	/**支付方式*/
	private Integer payType;
	/**项目id*/
	private Long projectId;
	/**测评专题id*/
	private Long subjectId;
	/**用户组id*/
	private String groupId;
	/**支付时间*/
	private Date payTime;
	/**创建时间*/
	private Date createTime;
	/**操作时间*/
	private Date operateTime;
	/**操作人*/
	private String operator;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=19,scale=0)
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(Long id){
		this.id = id;
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
	 *@return: java.lang.String  测评量表id
	 */
	@Column(name ="CLASS_ID",nullable=true,length=32)
	public String getClassId(){
		return this.classId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  测评量表id
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  量表名称
	 */
	@Column(name ="CLASS_NAME",nullable=true,length=200)
	public String getClassName(){
		return this.className;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  量表名称
	 */
	public void setClassName(String className){
		this.className = className;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  订单实付金额
	 */
	@Column(name ="ORDER_AMOUNT",nullable=true,precision=20,scale=5)
	public BigDecimal getOrderAmount(){
		return this.orderAmount;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  订单实付金额
	 */
	public void setOrderAmount(BigDecimal orderAmount){
		this.orderAmount = orderAmount;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  原始价格
	 */
	@Column(name ="ORG_AMOUNT",nullable=true,precision=20,scale=5)
	public BigDecimal getOrgAmount(){
		return this.orgAmount;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  原始价格
	 */
	public void setOrgAmount(BigDecimal orgAmount){
		this.orgAmount = orgAmount;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单状态
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单状态
	 */
	public void setStatus(Integer status){
		this.status = status;
	}

	@Column(name ="PAY_RESULT_CODE",nullable=true,length=50)
	public String getPayResultCode() {
		return payResultCode;
	}

	public ReviewOrderEntity setPayResultCode(String payResultCode) {
		this.payResultCode = payResultCode;
		return this;
	}

	@Column(name ="PAY_RESULT_MSG",nullable=true, length = 100)
	public String getPayResultMsg() {
		return payResultMsg;
	}

	public ReviewOrderEntity setPayResultMsg(String payResultMsg) {
		this.payResultMsg = payResultMsg;
		return this;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  外部支付id
	 */
	@Column(name ="PAY_ID",nullable=true,length=200)
	public String getPayId(){
		return this.payId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  外部支付id
	 */
	public void setPayId(String payId){
		this.payId = payId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  支付方式
	 */
	@Column(name ="PAY_TYPE",nullable=true,precision=10,scale=0)
	public Integer getPayType(){
		return this.payType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  支付方式
	 */
	public void setPayType(Integer payType){
		this.payType = payType;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  支付时间
	 */
	@Column(name ="PAY_TIME",nullable=true)
	public Date getPayTime(){
		return this.payTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  支付时间
	 */
	public void setPayTime(Date payTime){
		this.payTime = payTime;
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
