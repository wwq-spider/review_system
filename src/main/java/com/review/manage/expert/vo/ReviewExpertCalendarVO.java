package com.review.manage.expert.vo;

import java.io.Serializable;

/**
 * 专家日历
 */
public class ReviewExpertCalendarVO implements Serializable {

    /**id*/
    private Long id;
    /**专家id*/
    private Long expertId;
    /**出诊日期*/
    private String visitDate;
    /**周几*/
    private Integer weekDay;
    /**beginTime*/
    private String beginTime;
    /**endTime*/
    private String endTime;
    /**日历状态(1:已发布；2:未发布)*/
    private Integer status;
    /**创建时间*/
    private String createTime;
    /**更新时间*/
    private String updateTime;
    /**创建人*/
    private String creator;
    private String weekDayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getWeekDayName() {
        return weekDayName;
    }

    public void setWeekDayName(String weekDayName) {
        this.weekDayName = weekDayName;
    }
}
