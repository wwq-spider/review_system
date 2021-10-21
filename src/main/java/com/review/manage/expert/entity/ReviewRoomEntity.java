package com.review.manage.expert.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 测评专家预约
 * @author zhangdaihao
 * @date 2021-10-21 11:43:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "review_room", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ReviewRoomEntity implements java.io.Serializable {
	/**房间id*/
	private Long id;
	/**房间编号*/
	private String roomNumber;
	/**房间名称*/
	private String roomName;
	/**房间类型*/
	private Integer type;
	/**房间状态是否允许加入()*/
	private Integer status;
	/**创建时间*/
	private Date createTime;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  房间id
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID",nullable=true,precision=19,scale=0)
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  房间id
	 */
	public void setId(Long id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  房间编号
	 */
	@Column(name ="ROOM_NUMBER",nullable=true,length=50)
	public String getRoomNumber(){
		return this.roomNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  房间编号
	 */
	public void setRoomNumber(String roomNumber){
		this.roomNumber = roomNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  房间名称
	 */
	@Column(name ="ROOM_NAME",nullable=true,length=50)
	public String getRoomName(){
		return this.roomName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  房间名称
	 */
	public void setRoomName(String roomName){
		this.roomName = roomName;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  房间类型
	 */
	@Column(name ="TYPE",nullable=true,precision=10,scale=0)
	public Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  房间类型
	 */
	public void setType(Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  房间状态是否允许加入()
	 */
	@Column(name ="STATUS",nullable=true,precision=10,scale=0)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  房间状态是否允许加入()
	 */
	public void setStatus(Integer status){
		this.status = status;
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
