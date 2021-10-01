package com.review.manage.question.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测试
 * @author zhangdaihao
 * @date 2021-10-01 20:34:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_question_answer", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewQuestionAnswerEntity implements java.io.Serializable {
	/**主键*/
	private Long id;
	/**分类id*/
	private String classId;
	/**量表标题*/
	//private String classTitle;
	/**问题id*/
	private Integer questionId;
	/**题目编号*/
	private Integer questionNum;
	/**题目内容*/
	private String content;
	/**用户id*/
	private String userId;
	/**用户名*/
	private String userName;
	private String mobilePhone;
	private String sex;
	private Integer age;
	/**用户分组id*/
	private String groupId;
	/**选择答案*/
	private String rightAnswer;
	/**所选选项code*/
	private String selCode;
	/**选项分数*/
	private String selectGrade;
	/**变量id*/
	private String variateId;
	/**因子名称*/
	private String variateName;
	/**创建时间*/
	private Date createTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=19,scale=0)
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类id
	 */
	@Column(name ="CLASS_ID",nullable=true,length=32)
	public String getClassId(){
		return this.classId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类id
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  量表标题
	 */
//	@Column(name ="CLASS_TITLE",nullable=true,length=200)
//	public String getClassTitle(){
//		return this.classTitle;
//	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  量表标题
	 */
//	public void setClassTitle(String classTitle){
//		this.classTitle = classTitle;
//	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  问题id
	 */
	@Column(name ="QUESTION_ID",nullable=true,precision=19,scale=0)
	public Integer getQuestionId(){
		return this.questionId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  问题id
	 */
	public void setQuestionId(Integer questionId){
		this.questionId = questionId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  题目编号
	 */
	@Column(name ="QUESTION_NUM",nullable=true,precision=10,scale=0)
	public Integer getQuestionNum(){
		return this.questionNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  题目编号
	 */
	public void setQuestionNum(Integer questionNum){
		this.questionNum = questionNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  题目内容
	 */
	@Column(name ="CONTENT",nullable=true,length=500)
	public String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  题目内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}

	@Column(name ="USER_NAME",nullable=true,length=50)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name ="mobile_phone",nullable=true,length=20)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name ="SEX",nullable=true,length=10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name ="AGE",nullable=true,length=10)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户分组id
	 */
	@Column(name ="GROUP_ID",nullable=true,length=32)
	public String getGroupId(){
		return this.groupId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户分组id
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选择答案
	 */
	@Column(name ="RIGHT_ANSWER",nullable=true,length=50)
	public String getRightAnswer(){
		return this.rightAnswer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选择答案
	 */
	public void setRightAnswer(String rightAnswer){
		this.rightAnswer = rightAnswer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所选选项code
	 */
	@Column(name ="SEL_CODE",nullable=true,length=20)
	public String getSelCode(){
		return this.selCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所选选项code
	 */
	public void setSelCode(String selCode){
		this.selCode = selCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  选项分数
	 */
	@Column(name ="SELECT_GRADE",nullable=true,length=20)
	public String getSelectGrade(){
		return this.selectGrade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选项分数
	 */
	public void setSelectGrade(String selectGrade){
		this.selectGrade = selectGrade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  变量id
	 */
	@Column(name ="VARIATE_ID",nullable=true,length=32)
	public String getVariateId(){
		return this.variateId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  变量id
	 */
	public void setVariateId(String variateId){
		this.variateId = variateId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  因子名称
	 */
	@Column(name ="VARIATE_NAME",nullable=true,length=50)
	public String getVariateName(){
		return this.variateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  因子名称
	 */
	public void setVariateName(String variateName){
		this.variateName = variateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
}
