package com.review.manage.question.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.review.front.vo.SelectVO;
import com.review.manage.question.entity.ReviewAnswerEntity;



public class QuestionVO implements Serializable {

	private Integer questionId;   	//题目ID
		
    private String isImportant;  	//是否为重要题目
    
    private String isRight;		    //是否为正确答案
    
    private String rightAnswer;     //正确答案
    
    private String questionType;   	//题目类型
    
    private String classId;         //分类ID

	private Long projectId;    //关联项目ID

    private String answerId;
    
    private String variateName;     //因子名称
    
    private String variateId;       //变量因子ID
    
    private String resultId;
    
    private String reviewType;		//测评类型
    
    private String content;   		//题目内容

    private Integer questionNum;   	//题目序号

    private String createTime;   	//创建时间

    private String createBy; 		//创建人
    
    private String selCode;
    
    private String selectContent;
    
    private String selectGrade;
    
    private String isAttach;

	private String pictureAttach;

    private String isLastQuestion;
    
    private String multiple;     //可多选
    
    private CommonsMultipartFile contentImg;
    
    private List<SelectVO> selectList = new ArrayList<SelectVO>();
    
    private Map<String, Double> gradeMap = new HashMap<String, Double>();
    
    private List<ReviewAnswerEntity> answerList = new ArrayList<ReviewAnswerEntity>();
    
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getIsRight() {
		return isRight;
	}

	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public List<SelectVO> getSelectList() {
		return selectList;
	}

	public void setSelectList(List<SelectVO> selectList) {
		this.selectList = selectList;
	}

	public String getIsLastQuestion() {
		return isLastQuestion;
	}

	public void setIsLastQuestion(String isLastQuestion) {
		this.isLastQuestion = isLastQuestion;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public List<ReviewAnswerEntity> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<ReviewAnswerEntity> answerList) {
		this.answerList = answerList;
	}

	public CommonsMultipartFile getContentImg() {
		return contentImg;
	}

	public void setContentImg(CommonsMultipartFile contentImg) {
		this.contentImg = contentImg;
	}

	public Map<String, Double> getGradeMap() {
		return gradeMap;
	}

	public void setGradeMap(Map<String, Double> gradeMap) {
		this.gradeMap = gradeMap;
	}

	public String getPictureAttach() {
		return pictureAttach;
	}

	public void setPictureAttach(String pictureAttach) {
		this.pictureAttach = pictureAttach;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
