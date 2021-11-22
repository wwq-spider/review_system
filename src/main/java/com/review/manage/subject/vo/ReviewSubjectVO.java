package com.review.manage.subject.vo;

import com.review.manage.reviewClass.vo.ReviewClassVO;
import com.review.manage.subject.entity.ReviewSubjectClassEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.common.model.json.DataGrid;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 测评主题
 * @author zhangdaihao
 * @date 2021-11-12 19:03:18
 * @version V1.0   
 *
 */
public class ReviewSubjectVO implements java.io.Serializable {
	/**id*/
	private Long id;
	/**专题名称*/
	private String subjectName;
	/**专题描述*/
	private String subjectDesc;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**操作者*/
	private String operator;
	/**分类id*/
	private String classIds;
	/**分页查询*/
	private DataGrid dataGrid;
	/**专题状态*/
	private Integer status;

	private List<ReviewSubjectClassEntity> subjectClassList = new ArrayList<>();

	private List<ReviewClassVO> classList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectDesc() {
		return subjectDesc;
	}

	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getClassIds() {
		return classIds;
	}

	public void setClassIds(String classIds) {
		this.classIds = classIds;
	}

	public List<ReviewSubjectClassEntity> getSubjectClassList() {
		return subjectClassList;
	}

	public void setSubjectClassList(List<ReviewSubjectClassEntity> subjectClassList) {
		this.subjectClassList = subjectClassList;
	}

	public DataGrid getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}

	public List<ReviewClassVO> getClassList() {
		return classList;
	}

	public void setClassList(List<ReviewClassVO> classList) {
		this.classList = classList;
	}
}
