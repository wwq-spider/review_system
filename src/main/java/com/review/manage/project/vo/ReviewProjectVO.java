package com.review.manage.project.vo;

import com.review.manage.project.entity.ReviewProjectClassEntity;
import com.review.manage.project.entity.ReviewProjectUserGroupEntity;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReviewProjectVO implements Serializable {

    private Long id;

    private String projectName;

    private String projectDesc;

    private String appletsCrCodeLink;

    private String createTime;

    private String updateTime;

    private String creator;

    private String classIds;

    private Integer showReport;

    private String cover;

    private Integer isOpen;

    private String groupId;

    private CommonsMultipartFile contentImg;

    private List<ReviewProjectClassEntity> reviewProjectClassList = new ArrayList<>();

    private List<ReviewProjectUserGroupEntity> reviewProjectUserGroupList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getAppletsCrCodeLink() {
        return appletsCrCodeLink;
    }

    public void setAppletsCrCodeLink(String appletsCrCodeLink) {
        this.appletsCrCodeLink = appletsCrCodeLink;
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

    public String getClassIds() {
        return classIds;
    }

    public void setClassIds(String classIds) {
        this.classIds = classIds;
    }

    public CommonsMultipartFile getContentImg() {
        return contentImg;
    }

    public void setContentImg(CommonsMultipartFile contentImg) {
        this.contentImg = contentImg;
    }

    public List<ReviewProjectClassEntity> getReviewProjectClassList() {
        return reviewProjectClassList;
    }

    public void setReviewProjectClassList(List<ReviewProjectClassEntity> reviewProjectClassList) {
        this.reviewProjectClassList = reviewProjectClassList;
    }

    public Integer getShowReport() {
        return showReport;
    }

    public void setShowReport(Integer showReport) {
        this.showReport = showReport;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<ReviewProjectUserGroupEntity> getReviewProjectUserGroupList() {
        return reviewProjectUserGroupList;
    }

    public void setReviewProjectUserGroupList(List<ReviewProjectUserGroupEntity> reviewProjectUserGroupList) {
        this.reviewProjectUserGroupList = reviewProjectUserGroupList;
    }
}
