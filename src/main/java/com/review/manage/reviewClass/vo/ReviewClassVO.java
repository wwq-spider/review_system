package com.review.manage.reviewClass.vo;


public class ReviewClassVO {

	private java.lang.String classId;   
	
 	private Integer sortId;
 
    private java.lang.String title;   

    private java.lang.Integer status;   

    private java.lang.String guide;
    
    private String questionIds;

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
    
}
