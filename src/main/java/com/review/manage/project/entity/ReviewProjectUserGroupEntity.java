package com.review.manage.project.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="review_project_user_group")
public class ReviewProjectUserGroupEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name ="id",nullable=false)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "group_id")
    private String groupId;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
