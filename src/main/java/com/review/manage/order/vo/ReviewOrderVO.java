package com.review.manage.order.vo;

import java.io.Serializable;

/**
 * 测评订单
 */
public class ReviewOrderVO implements Serializable {

    /**主键*/
    private Long id;
    /**用户id*/
    private String userId;
    /**用户手机号*/
    private String mobilePhone;
    /**微信openid*/
    private String openid;
    /**支付ip地址*/
    private String ipAddr;
    /**测评量表id*/
    private String classId;
    /**量表名称*/
    private String className;
    /**量表封面*/
    private String bannerImg;
    /**订单实付金额*/
    private String orderAmount;
    /**原始价格*/
    private String orgAmount;
    /**订单号*/
    private Long orderNo;
    /**订单状态*/
    private Integer status;
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
    private String payTime;
    /**创建时间*/
    private String createTime;
    /**操作时间*/
    private String operateTime;
    /**操作人*/
    private String operator;

    private String broswer;

    /**微信订单id*/
    private String transactionId;

    private Long expertId;

    private Integer isExistClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public String getOpenid() {
        return openid;
    }

    public ReviewOrderVO setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public ReviewOrderVO setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrgAmount() {
        return orgAmount;
    }

    public void setOrgAmount(String orgAmount) {
        this.orgAmount = orgAmount;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBroswer() {
        return broswer;
    }

    public void setBroswer(String broswer) {
        this.broswer = broswer;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public Integer getIsExistClass() {
        return isExistClass;
    }

    public void setIsExistClass(Integer isExistClass) {
        this.isExistClass = isExistClass;
    }
}
