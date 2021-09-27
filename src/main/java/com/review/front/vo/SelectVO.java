package com.review.front.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SelectVO {

	private String selectId;
	
	private String selCode;
    
    private String selectContent;
    
    private String selectGrade;
    
    private String isAttach;

    private CommonsMultipartFile pictureAttach;
    
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

	public CommonsMultipartFile getPictureAttach() {
		return pictureAttach;
	}

	public void setPictureAttach(CommonsMultipartFile pictureAttach) {
		this.pictureAttach = pictureAttach;
	}
    
    
}
