package com.review.manage.expert.vo;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.io.Serializable;
import java.math.BigDecimal;

public class ReviewExpertVO implements Serializable {

    private Long id;
    /**专家姓名*/
    private String expertName;
    /**性别*/
    private Integer sex;
    /**年龄*/
    private Integer age;
    /**关联测评用户id*/
    private String userId;
    /**专家手机号*/
    private String mobilePhone;
    /**职称*/
    private String jobTitle;
    /**专家介绍*/
    private String introduction;
    /**工作机构名称*/
    private String workOrgName;
    /**专家标签*/
    private String label;
    /**头像*/
    private String avatar;
    private Integer status;
    private Integer sortNum;
    /**创建时间*/
    private String createTime;
    /**更新时间*/
    private String updateTime;
    /**创建人*/
    private String creator;

    private CommonsMultipartFile contentImg;

    private DataGrid dataGrid;

    private Integer charge;

    private BigDecimal orgPrice;

    private BigDecimal dicountPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getWorkOrgName() {
        return workOrgName;
    }

    public void setWorkOrgName(String workOrgName) {
        this.workOrgName = workOrgName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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

    public CommonsMultipartFile getContentImg() {
        return contentImg;
    }

    public void setContentImg(CommonsMultipartFile contentImg) {
        this.contentImg = contentImg;
    }

    public DataGrid getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(DataGrid dataGrid) {
        this.dataGrid = dataGrid;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public BigDecimal getOrgPrice() {
        return orgPrice;
    }

    public void setOrgPrice(BigDecimal orgPrice) {
        this.orgPrice = orgPrice;
    }

    public BigDecimal getDicountPrice() {
        return dicountPrice;
    }

    public void setDicountPrice(BigDecimal dicountPrice) {
        this.dicountPrice = dicountPrice;
    }
}
