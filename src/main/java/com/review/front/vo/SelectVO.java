package com.review.front.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SelectVO {

	private String selectId;
	
	private String selCode;
    
    private String selectContent;
    
    private String selectGrade;
    
    private String isAttach;

    private String pictureAttach;

	private CommonsMultipartFile contentImg;
    
	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public String getSelCode() {
		return selCode;
	}

	public void setSelCode(String selCode) {
		this.selCode = selCode;
	}

	public String getSelectContent() {
		return selectContent;
	}

	public void setSelectContent(String selectContent) {
		this.selectContent = selectContent;
	}

	public String getSelectGrade() {
		return selectGrade;
	}

	public void setSelectGrade(String selectGrade) {
		this.selectGrade = selectGrade;
	}

	public String getIsAttach() {
		return isAttach;
	}

	public void setIsAttach(String isAttach) {
		this.isAttach = isAttach;
	}

	public String getPictureAttach() {
		return pictureAttach;
	}

	public void setPictureAttach(String pictureAttach) {
		this.pictureAttach = pictureAttach;
	}

	public CommonsMultipartFile getContentImg() {
		return contentImg;
	}

	public void setContentImg(CommonsMultipartFile contentImg) {
		this.contentImg = contentImg;
	}
}
