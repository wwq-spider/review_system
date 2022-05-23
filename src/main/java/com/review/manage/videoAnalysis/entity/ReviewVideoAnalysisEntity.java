package com.review.manage.videoAnalysis.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 视频分析记录
 * @author zhangdaihao
 * @date 2022-02-10 18:22:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_video_analysis", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewVideoAnalysisEntity implements java.io.Serializable {
	/**id*/
	private Long id;
	/**视频地址*/
	private String videoPath;
	/**视频时长*/
	private Long videoDuration;
	/**分析结果*/
	private String healthAnalysisResult;
	/**表情分析结果*/
	private String emoAnalysisResult;
	/**emoStatus*/
	private Integer emoStatus;
	/**healthStatus*/
	private Integer healthStatus;
	/**healthMsg*/
	private String healthMsg;
	/**emoMsg*/
	private String emoMsg;
	/**用户id*/
	private String userId;
	/**量表id*/
	private String classId;
	/**项目id*/
	private Long projectId;
	/**创建时间*/
	private Date createTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=19,scale=0)
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  id
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  视频地址
	 */
	@Column(name ="VIDEO_PATH",nullable=true,length=100)
	public String getVideoPath(){
		return this.videoPath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  视频地址
	 */
	public void setVideoPath(String videoPath){
		this.videoPath = videoPath;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  视频时长
	 */
	@Column(name ="VIDEO_DURATION",nullable=true,precision=19,scale=0)
	public Long getVideoDuration(){
		return this.videoDuration;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  视频时长
	 */
	public void setVideoDuration(Long videoDuration){
		this.videoDuration = videoDuration;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分析结果
	 */
	@Column(name ="HEALTH_ANALYSIS_RESULT",nullable=true,length=200)
	public String getHealthAnalysisResult(){
		return this.healthAnalysisResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分析结果
	 */
	public void setHealthAnalysisResult(String healthAnalysisResult){
		this.healthAnalysisResult = healthAnalysisResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表情分析结果
	 */
	@Column(name ="EMO_ANALYSIS_RESULT",nullable=true,length=200)
	public String getEmoAnalysisResult(){
		return this.emoAnalysisResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表情分析结果
	 */
	public void setEmoAnalysisResult(String emoAnalysisResult){
		this.emoAnalysisResult = emoAnalysisResult;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  emoStatus
	 */
	@Column(name ="EMO_STATUS",nullable=true,precision=10,scale=0)
	public Integer getEmoStatus(){
		return this.emoStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  emoStatus
	 */
	public void setEmoStatus(Integer emoStatus){
		this.emoStatus = emoStatus;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  healthStatus
	 */
	@Column(name ="HEALTH_STATUS",nullable=true,precision=10,scale=0)
	public Integer getHealthStatus(){
		return this.healthStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  healthStatus
	 */
	public void setHealthStatus(Integer healthStatus){
		this.healthStatus = healthStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  healthMsg
	 */
	@Column(name ="HEALTH_MSG",nullable=true,length=200)
	public String getHealthMsg(){
		return this.healthMsg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  healthMsg
	 */
	public void setHealthMsg(String healthMsg){
		this.healthMsg = healthMsg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  emoMsg
	 */
	@Column(name ="EMO_MSG",nullable=true,length=200)
	public String getEmoMsg(){
		return this.emoMsg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  emoMsg
	 */
	public void setEmoMsg(String emoMsg){
		this.emoMsg = emoMsg;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  量表id
	 */
	@Column(name ="CLASS_ID",nullable=true,length=32)
	public String getClassId(){
		return this.classId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  量表id
	 */
	public void setClassId(String classId){
		this.classId = classId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  项目id
	 */
	@Column(name ="PROJECT_ID",nullable=true,precision=19,scale=0)
	public Long getProjectId(){
		return this.projectId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  项目id
	 */
	public void setProjectId(Long projectId){
		this.projectId = projectId;
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
