package com.review.manage.reviewClass.vo;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ReviewClassVO {

	private java.lang.String classId;

	private String nextClassId;

	private Integer sortId;
 
    private java.lang.String title;   

    private java.lang.Integer status;   

    private java.lang.String guide;

	private String classDesc;
    
    private String questionIds;

    private Long projectId;

	private String projectName;

	private String bannerImg;

	private CommonsMultipartFile contentImg;

	private Integer type;

	private DataGrid dataGrid;

	private Long subjectId;

	private String subjectName;

	private Integer charge;

	private String orgPrice;

	private String dicountPrice;

	private String realPrice;

	private Boolean buy;

	private Integer buyCount;

	private Integer reviewStatus;

	/**
	 * 测评次数
	 */
	private Integer reviewTimes;

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

	public DataGrid getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	public String getOrgPrice() {
		return orgPrice;
	}

	public void setOrgPrice(String orgPrice) {
		this.orgPrice = orgPrice;
	}

	public String getDicountPrice() {
		return dicountPrice;
	}

	public void setDicountPrice(String dicountPrice) {
		this.dicountPrice = dicountPrice;
	}

	public String getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}

	public Boolean getBuy() {
		return buy;
	}

	public void setBuy(Boolean buy) {
		this.buy = buy;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public String getProjectName() {
		return projectName;
	}

	public ReviewClassVO setProjectName(String projectName) {
		this.projectName = projectName;
		return this;
	}

	public String getNextClassId() {
		return nextClassId;
	}

	public void setNextClassId(String nextClassId) {
		this.nextClassId = nextClassId;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Integer getReviewTimes() {
		if (reviewTimes == null) {
			return 0;
		}
		return reviewTimes;
	}

	public void setReviewTimes(Integer reviewTimes) {
		this.reviewTimes = reviewTimes;
	}
}
