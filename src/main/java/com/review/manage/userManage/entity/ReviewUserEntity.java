package com.review.manage.userManage.entity;
import java.io.Serializable;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.review.manage.report.vo.ReportVO;
import com.review.manage.variate.vo.VariateVO;

@Entity
@Table(name="review_user")
public class ReviewUserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String userId;
	
	@Excel(exportName="身份证号", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private String idCard;
	
	@Excel(exportName="真实姓名", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private String realName;
	
	@Excel(exportName="性别", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private String sex;
	
	@Excel(exportName="年龄", exportConvertSign = 0, exportFieldWidth = 10, importConvertSign = 0)
	private Integer age;
	
	@Excel(exportName="用户名", exportConvertSign = 0, exportFieldWidth = 50, importConvertSign = 0)
	private String userName;
	
	@Excel(exportName="登录密码", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private String password;
	
	@Excel(exportName="手机号", exportConvertSign = 0, exportFieldWidth = 20, importConvertSign = 0)
	private String mobilePhone;

	private String openid;

	private String groupId;

	private Integer source;

	private Date createTime;

	private Date updateTime;
	/**
	 * 扩展字段
	 */
	private String extra;

	private Map<String, Object> extraObj = new HashMap<>();

	private transient Long projectId;

	private transient String msgCode;

	
	private List<ReportVO> reportVOList = new ArrayList<ReportVO>();
	

	private Map<String, VariateVO> variateMap = new HashMap<String, VariateVO>(); 
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="user_id",nullable=false,length=32)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="id_card")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name="real_name")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name="sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name="age")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="mobile_phone")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name="openid")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name="group_id")
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Transient
	public List<ReportVO> getReportVOList() {
		return reportVOList;
	}

	public void setReportVOList(List<ReportVO> reportVOList) {
		this.reportVOList = reportVOList;
	}
	@Transient
	public Map<String, VariateVO> getVariateMap() {
		return variateMap;
	}

	public void setVariateMap(Map<String, VariateVO> variateMap) {
		this.variateMap = variateMap;
	}

	@Transient
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	@Column(name = "source")
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "extra")
	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Transient
	public Map<String, Object> getExtraObj() {
		return extraObj;
	}

	public void setExtraObj(Map<String, Object> extraObj) {
		this.extraObj = extraObj;
	}

	@Transient
	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
}
