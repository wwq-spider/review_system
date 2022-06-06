package com.review.front.vo;

import java.io.Serializable;

/**
 * @author javabage
 * @date 2022/6/2
 */
public class ConsultationVO implements Serializable {
    /*
        预约id
     */
    private Integer id;
    /*
        用户id
     */
    private String userId;
    /*
        专家姓名
     */
    private String expertName;
    /*
        专家性别
     */
    private Integer expertSex;
    /*
        专家职称
     */
    private String expertJobTitle;
    /*
        专家介绍
     */
    private String expertIntroduction;
    /*
        专家标签
     */
    private String expertLable;
    /*
        预约人姓名
     */
    private String patientName;
    /*
        预约人性别
     */
    private String patientSex;
    /*
        预约人年龄
     */
    private Integer patientAge;
    /*
        预约人电话
     */
    private String userPhone;
    /*
        预约人身份证号
     */
    private String userIdCard;
    /*
        预约开始时间
     */
    private String beginTime;
    /*
        预约结束时间
     */
    private String endTime;
    /*
        周几（1，2，3...）
     */
    private Integer weekDay;
    /*
        周几（周一，周二，周三...）
     */
    private String weekDayName;
    /*
        预约状态
     */
    private Integer status;
    private String statusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
