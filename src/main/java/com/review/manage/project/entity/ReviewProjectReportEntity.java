package com.review.manage.project.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评项目报告
 * @author zhangdaihao
 * @date 2022-01-02 19:50:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_project_report", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewProjectReportEntity implements java.io.Serializable {
	/**主键*/
	private Integer id;
	/**用户id*/
	private String userId;
	/**项目组id*/
	private String groupId;
	/**项目id*/
	private Integer projectId;
	/**报告名称*/
	private String reportName;
	/**报告文件路径*/
	private String reportFilePath;
	/**报告模板路径*/
	private String templatePath;
	/**创建时间*/
	private Date createTime;
	/**操作人*/
	private String operator;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  主键
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=false,precision=19,scale=0)
	public Integer getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  主键
	 */
	public void setId(Integer id){
		this.id = id;
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
	 *@return: java.lang.String  项目组id
	 */
	@Column(name ="GROUP_ID",nullable=true,length=32)
	public String getGroupId(){
		return this.groupId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  项目组id
	 */
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  项目id
	 */
	@Column(name ="PROJECT_ID",nullable=true,precision=19,scale=0)
	public Integer getProjectId(){
		return this.projectId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  项目id
	 */
	public void setProjectId(Integer projectId){
		this.projectId = projectId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报告名称
	 */
	@Column(name ="REPORT_NAME",nullable=true,length=50)
	public String getReportName(){
		return this.reportName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报告名称
	 */
	public void setReportName(String reportName){
		this.reportName = reportName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报告文件路径
	 */
	@Column(name ="REPORT_FILE_PATH",nullable=true,length=200)
	public String getReportFilePath(){
		return this.reportFilePath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报告文件路径
	 */
	public void setReportFilePath(String reportFilePath){
		this.reportFilePath = reportFilePath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报告模板路径
	 */
	@Column(name ="TEMPLATE_PATH",nullable=true,length=200)
	public String getTemplatePath(){
		return this.templatePath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报告模板路径
	 */
	public void setTemplatePath(String templatePath){
		this.templatePath = templatePath;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  操作人
	 */
	@Column(name ="OPERATOR",nullable=true,length=50)
	public String getOperator(){
		return this.operator;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  操作人
	 */
	public void setOperator(String operator){
		this.operator = operator;
	}
}
