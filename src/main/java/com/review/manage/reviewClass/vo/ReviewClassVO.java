package com.review.manage.reviewClass.vo;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ReviewClassVO {

	private java.lang.String classId;   
	
 	private Integer sortId;
 
    private java.lang.String title;   

    private java.lang.Integer status;   

    private java.lang.String guide;

	private String classDesc;
    
    private String questionIds;

    private Long projectId;

	private String bannerImg;

	private CommonsMultipartFile contentImg;

	private Integer type;

	public java.lang.String getClassId() {
		return classId;
	}

	public void setClassId(java.lang.String classId) {
		this.classId = classId;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getGuide() {
		return guide;
	}

	public void setGuide(java.lang.String guide) {
		this.guide = guide;
	}

	public String getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public CommonsMultipartFile getContentImg() {
		return contentImg;
	}

	public void setContentImg(CommonsMultipartFile contentImg) {
		this.contentImg = contentImg;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}
}
