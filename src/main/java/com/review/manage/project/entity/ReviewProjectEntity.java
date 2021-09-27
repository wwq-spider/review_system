package com.review.manage.project.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="review_project")
public class ReviewProjectEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name ="id",nullable=false)
    private Long id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_desc")
    private String projectDesc;

    @Column(name = "applets_qr_code_link")
    private String appletsCrCodeLink;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "creator")
    private String creator;

    @Transient
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<ReviewProjectClassEntity> getReviewProjectClassList() {
        return reviewProjectClassList;
    }

    public void setReviewProjectClassList(List<ReviewProjectClassEntity> reviewProjectClassList) {
        this.reviewProjectClassList = reviewProjectClassList;
    }
}
