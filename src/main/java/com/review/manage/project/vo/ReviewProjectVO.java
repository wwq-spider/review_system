package com.review.manage.project.vo;
import com.review.manage.project.entity.ReviewProjectClassEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    private List<ReviewProjectClassEntity> reviewProjectClassList = new ArrayList<>();

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

    public List<ReviewProjectClassEntity> getReviewProjectClassList() {
        return reviewProjectClassList;
    }

    public void setReviewProjectClassList(List<ReviewProjectClassEntity> reviewProjectClassList) {
        this.reviewProjectClassList = reviewProjectClassList;
    }
}
