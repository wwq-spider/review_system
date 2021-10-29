package com.review.manage.question.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

public class ReviewQuestionAnswerVO implements Serializable {

    private String classId;

    /**题目内容*/
    @Excel(exportName="题目内容", exportConvertSign = 0, exportFieldWidth = 200, importConvertSign = 0)
    private String content;

    private String userId;

    /**用户名*/
    @Excel(exportName="姓名", exportConvertSign = 0, exportFieldWidth = 50, importConvertSign = 0)
    private String userName;

    private String realName;

    @Excel(exportName="手机号", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
    private String mobilePhone;

    @Excel(exportName="性别", exportConvertSign = 0, exportFieldWidth = 5, importConvertSign = 0)
    private String sex;

    @Excel(exportName="年龄", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
    private Integer age;
    /**选择答案*/
    private String rightAnswer;
    /**所选选项code*/
    private String selCode;

    /**选项分数*/
    @Excel(exportName="分数", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
    private String selectGrade;
    /**变量id*/
    private String variateId;

    private String resultId;
    /**因子名称*/
    private String variateName;
    /**创建时间*/
    @Excel(exportName="创建时间", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
    private String createTime;

    private String extra;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getSelCode() {
        return selCode;
    }

    public void setSelCode(String selCode) {
        this.selCode = selCode;
    }

    public String getSelectGrade() {
        return selectGrade;
    }

    public void setSelectGrade(String selectGrade) {
        this.selectGrade = selectGrade;
    }

    public String getVariateId() {
        return variateId;
    }

    public void setVariateId(String variateId) {
        this.variateId = variateId;
    }

    public String getVariateName() {
        return variateName;
    }

    public void setVariateName(String variateName) {
        this.variateName = variateName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
