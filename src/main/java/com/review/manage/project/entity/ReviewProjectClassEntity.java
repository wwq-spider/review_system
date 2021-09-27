package com.review.manage.project.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="review_project_class")
public class ReviewProjectClassEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name ="id",nullable=false)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "org_id")
    private Long orgId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
