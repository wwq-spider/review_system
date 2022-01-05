package com.review.manage.project.vo;
import com.review.front.vo.ReviewResultVO;
import java.io.Serializable;
import java.util.List;

public class ProjectResultVO implements Serializable {

    private String userId;

    private String idCard;

    private String userName;

    private String realName;
    //项目id
    private Long projectId;
    //等级分数
    private Integer levelGrade;
    //总结论
    private String allCompletion;
    //建议
    private String suggestion;
    //测评结果列表
    private List<ReviewResultVO> resultList;
    //等级时间
    private String createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getLevelGrade() {
        return levelGrade;
    }

    public void setLevelGrade(Integer levelGrade) {
        this.levelGrade = levelGrade;
    }

    public String getAllCompletion() {
        return allCompletion;
    }

    public void setAllCompletion(String allCompletion) {
        this.allCompletion = allCompletion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public List<ReviewResultVO> getResultList() {
        return resultList;
    }

    public void setResultList(List<ReviewResultVO> resultList) {
        this.resultList = resultList;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
