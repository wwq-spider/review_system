package com.review.front.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author javabage
 * @date 2022/6/2
 */
public class ConsultationVO implements Serializable {
    /**
        预约id
     */
    private Integer id;
    /**
        日历id
     */
    private Integer calendarId;
    /**
        用户id
     */
    private String userId;
    /**
     * 专家id
     */
    private Long expertId;
    /**
        专家姓名
     */
    private String expertName;
    /**
        专家性别
     */
    private Integer expertSex;
    /**
        专家职称
     */
    private String expertJobTitle;
    /**
        专家介绍
     */
    private String expertIntroduction;
    /**
        专家标签
     */
    private String expertLable;
    /**
     * 专家电话
     */
    private String expertPhone;
    private String avatar;
    /**
        预约人姓名
     */
    private String patientName;
    /**
        预约人性别
     */
    private String patientSex;
    /**
        预约人年龄
     */
    private Integer patientAge;
    /**
        预约人电话
     */
    private String userPhone;
    /**
        预约人身份证号
     */
    private String userIdCard;
    /**
     * 预约日期
     */
    private String visitDate;
    /**
        预约开始时间
     */
    private String beginTime;
    /**
        预约结束时间
     */
    private String endTime;
    /**
        周几（1，2，3...）
     */
    private Integer weekDay;
    /**
        周几（周一，周二，周三...）
     */
    private String weekDayName;
    /**
        预约状态
     */
    private Integer status;
    private String statusName;
    /**
     *  是否收费(0:免费；1:收费)
     */
    private Integer charge;
    /**
     *  原始价格
     */
    private String orgPrice;
    /**
     *  优惠价格
     */
    private String dicountPrice;
    /**
     *  用户是否支付专家问诊
     */
    private Boolean buy;
    /**
     * 实际价格
     */
    private String realPrice;
    /**
     * 支付状态
     */
    private Integer payStatus;
    private String payStatusName;
    private String roomId;
    private String confirmFlag;
    /**
     * 发送短信提醒标记
     */
    private Integer sendFlag;

    private String createTime;

    /**
     * 腾讯会议码
     */
    private String txNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public Integer getExpertSex() {
        return expertSex;
    }

    public void setExpertSex(Integer expertSex) {
        this.expertSex = expertSex;
    }

    public String getExpertJobTitle() {
        return expertJobTitle;
    }

    public void setExpertJobTitle(String expertJobTitle) {
        this.expertJobTitle = expertJobTitle;
    }

    public String getExpertIntroduction() {
        return expertIntroduction;
    }

    public void setExpertIntroduction(String expertIntroduction) {
        this.expertIntroduction = expertIntroduction;
    }

    public String getExpertLable() {
        return expertLable;
    }

    public void setExpertLable(String expertLable) {
        this.expertLable = expertLable;
    }

    public String getExpertPhone() {
        return expertPhone;
    }

    public void setExpertPhone(String expertPhone) {
        this.expertPhone = expertPhone;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeekDayName() {
        return weekDayName;
    }

    public void setWeekDayName(String weekDayName) {
        this.weekDayName = weekDayName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public String getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(String orgPrice) {
        this.orgPrice = orgPrice;
    }

    public String getDicountPrice() {
        return dicountPrice;
    }

    public void setDicountPrice(String dicountPrice) {
        this.dicountPrice = dicountPrice;
    }

    public Boolean getBuy() {
        return buy;
    }

    public void setBuy(Boolean buy) {
        this.buy = buy;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getConfirmFlag() {
        return confirmFlag;
    }

    public void setConfirmFlag(String confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getTxNumber() {
        return txNumber;
    }

    public void setTxNumber(String txNumber) {
        this.txNumber = txNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
