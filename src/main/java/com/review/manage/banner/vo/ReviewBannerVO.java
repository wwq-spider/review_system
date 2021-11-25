package com.review.manage.banner.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;

public class ReviewBannerVO implements Serializable {

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
    private String createTime;
    /**operateTime*/
    private String operateTime;
    /**operator*/
    private String operator;

    private CommonsMultipartFile contentImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
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

    public CommonsMultipartFile getContentImg() {
        return contentImg;
    }

    public void setContentImg(CommonsMultipartFile contentImg) {
        this.contentImg = contentImg;
    }
}
