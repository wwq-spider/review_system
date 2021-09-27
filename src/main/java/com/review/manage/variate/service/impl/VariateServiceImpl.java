package com.review.manage.variate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.review.common.CommonUtils;
import com.review.manage.question.entity.ReviewQuestionEntity;
import com.review.manage.question.service.ReviewService;
import com.review.manage.variate.entity.ReviewGradeRuleEntity;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.entity.ReviewVariateGradeEntity;
import com.review.manage.variate.service.VariateService;
import com.review.manage.variate.vo.VariateVO;

@Service("variateService")
@Transactional
public class VariateServiceImpl extends CommonServiceImpl implements VariateService{

	@Autowired
	public ReviewService reviewService;
	
	/**
	 * 封装分类为easyui控件combotree对应的JSON格式数据
	 * @return
	 */
	public JSONArray getComboTreeData() {
		List<ReviewQuestionEntity> list = getQuestions();
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		for(ReviewQuestionEntity question : list) {
			map = new HashMap<String, Object>();
			map.put("id", question.getQuestionId());
			map.put("text", question.getQuestionId() + "." + question.getContent());
			map.put("iconCls", "icon-logo");
			mapList.add(map);
		}
		
		return JSONArray.fromObject(mapList);
	}
	
	private List<ReviewQuestionEntity> getQuestions() {
		String hql = "from ReviewQuestionEntity order by questionId asc";
		return this.findHql(hql);
	}

	@Override
	public String getQuestionIds(String variateId) {
		String sql = "SELECT GROUP_CONCAT(r.`question_id`) questionIds FROM review_grade_rule r WHERE r.`variate_id`=?";
		Map<String, Object> map = this.findOneForJdbc(sql, new Object[]{variateId});
		if(map.get("questionIds") != null) {
			return map.get("questionIds").toString();
		} else {
			return "";
		}
		
	}

	@Override
	public Long getVariateCount(String reviewType) {
		String sql = "select count(variate_id) from review_variate";
		if(!"".equals(StringUtils.trimToEmpty(reviewType))) {
			sql += " where class_id='" + reviewType + "'";
		}
		return this.getCountForJdbc(sql);
	}

	@Override
	public List<Map<String, Object>> getVariateList(DataGrid dataGrid,
			String reviewType) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("	  v.`class_id` classId,");
		sb.append("	  v.`sort_num` sortNum,");
		sb.append("   c.title className,");
		sb.append("   v.`create_by` createBy,");
		sb.append("   v.`variate_name` variateName,");
		sb.append("   v.`variate_id` variateId,");
		sb.append("   DATE_FORMAT(v.`create_time`,'%Y-%m-%e %H:%i:%S') createTime");
		sb.append(" FROM  ");
		sb.append("   review_variate v, review_class c");
		sb.append(" WHERE c.class_id=v.class_id");
		if(!"".equals(StringUtils.trimToEmpty(reviewType))) {
			sb.append(" AND c.`class_id`='"+reviewType+"'");
		}
		sb.append(" ORDER BY v.`class_id`,v.sort_num ASC ");
		return this.findForJdbc(sb.toString(), dataGrid.getPage(), dataGrid.getRows());
	}

	@Override
	public void addVariate(VariateVO variate) {
		List<ReviewVariateGradeEntity> list = variate.getVariateGradeList();
		Integer variateNum = variate.getVariateNum();
		ReviewVariateEntity variateEntity = new ReviewVariateEntity();
		variateEntity.setClassId(variate.getClassId());
		variateEntity.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
		variateEntity.setCreateTime(new Date());
		variateEntity.setVariateName(variate.getVariateName());	
		variateEntity.setSortNum(getMaxSortNum(variate.getClassId()));
		this.save(variateEntity);
		
		String resultExplain = "";
		
		for(ReviewVariateGradeEntity variateGrade : list) {
			if(variateGrade.getFile() != null) {
				if(variateGrade.getFile().getSize() > 0) {
					resultExplain = CommonUtils.readTxtFile(variateGrade.getFile());
					variateGrade.setResultExplain(resultExplain);
				}
			}
			variateGrade.setVariateId(variateEntity.getVariateId());
			this.save(variateGrade);
		}
		if(variateNum != null) {
			variateEntity.setCurGradeId(list.get(variateNum).getVariateGradeId());
		}
	}

	
	
	@Override
	public void updateVariate(VariateVO variate) {
		String resultExplain = "";
		List<ReviewVariateGradeEntity> list = variate.getVariateGradeList();
		Integer variateNum = variate.getVariateNum();
		ReviewVariateEntity variateEntity = this.get(ReviewVariateEntity.class, variate.getVariateId());
		variateEntity.setClassId(variate.getClassId());
		variateEntity.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
		variateEntity.setCreateTime(new Date());
		variateEntity.setVariateName(variate.getVariateName());

		//先删除变量题目关联表
		String sql = "delete from review_variate_grade where variate_id=?";
		this.executeSql(sql, new Object[]{variate.getVariateId()});
		
		for(ReviewVariateGradeEntity variateGrade : list) {
			variateGrade.setVariateId(variate.getVariateId());
			if(variateGrade.getFile() != null) {
				if(variateGrade.getFile().getSize() > 0) {
					resultExplain = CommonUtils.readTxtFile(variateGrade.getFile());
					variateGrade.setResultExplain(resultExplain);
				}
			}
			this.save(variateGrade);
		}
		if(variateNum != null) {
			variateEntity.setCurGradeId(list.get(variateNum).getVariateGradeId());
		}
		this.saveOrUpdate(variateEntity);
	}

	/**
	 * 获取某分类下的因子最大编号
	 * @param classId
	 * @return
	 */
	private Integer getMaxSortNum(String classId) {
		String sql = "SELECT MAX(v.`sort_num`) sortNum FROM review_variate v WHERE v.`class_id` =?";
		Map<String, Object> map = this.findOneForJdbc(sql,new Object[]{classId});
		if(map.get("sortNum") == null) {
			return 0;
		} else {
			return Integer.parseInt(map.get("sortNum").toString());
		}
	}
	
	@Override
	public void delVariate(String variateId) {
		//先删除变量题目关联表
		String sql = "delete from review_variate_grade where variate_id=?";
		this.executeSql(sql, new Object[]{variateId});
		
		//删除变量-报告关联表数据
		String sqlV = "DELETE FROM review_report_variate WHERE variate_id=?";
		this.executeSql(sqlV, new Object[]{variateId});
		
		//删除变量
		this.deleteEntityById(ReviewVariateEntity.class, variateId);
	}

	@Override
	public VariateVO getVariateDetail(String variateId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("   r.`variate_name` variateName,");
		sb.append("   r.`variate_id` variateId,");
		sb.append("   r.class_id classId,   ");
		sb.append("   r.`cur_grade_id` curGradeId ");
		sb.append(" FROM");
		sb.append("   review_variate r ");
		sb.append(" WHERE r.`variate_id` = :variateId ");
		Map<String, String> map = new HashMap<String, String>();
		map.put("variateId", variateId);
		VariateVO variate = this.getObjectDetail(sb.toString(), map, VariateVO.class);
		
		List<ReviewVariateGradeEntity> variateGradeList = this.findHql("from ReviewVariateGradeEntity where variateId=?", new Object[]{variateId});
		if(variateGradeList.size() == 0) {
			for(int i=0; i<3; i++){
				variateGradeList.add(new ReviewVariateGradeEntity());
			}
		}
		variate.setVariateGradeList(variateGradeList);
		return variate;
	}

	@Override
	public String getClassName(String variateId){
		String sql = "select c.title as className from review_variate v inner join review_class c on v.class_id=c.class_id where v.variate_id=?";
		Map<String, Object> map = this.findOneForJdbc(sql, new Object[]{variateId});
		if(map.get("className") != null) {
			return map.get("className").toString();
		} else {
			return "";
		}
	}
	
	@Override
	public List<ReviewVariateEntity> getVariateList(int page, int rows) {
		return this.findByQueryString("from ReviewVariateEntity ORDER BY createTime DESC");
	}

	@Override
	public void addScoreSet(VariateVO variate) {
		List<ReviewGradeRuleEntity> gradeRuleList = variate.getGradeRuleList();
		String sql = "delete from review_grade_rule where variate_id=?";
		this.executeSql(sql, new Object[]{variate.getVariateId()});

		//更新总条目运算信息
		ReviewVariateEntity variateEntity = this.get(ReviewVariateEntity.class, variate.getVariateId());
		variateEntity.setCalSymbol(variate.getCalSymbol());
		variateEntity.setCalTotal(variate.getCalTotal());
		variateEntity.setCalSymbol1(variate.getCalSymbol1());
		variateEntity.setCalTotal1(variate.getCalTotal1());
		this.saveOrUpdate(variateEntity);
		
		ReviewQuestionEntity question = null;
		
		for(ReviewGradeRuleEntity gradeRule : gradeRuleList) {
			question = reviewService.getQuestionByQnum(variate.getClassId(), gradeRule.getQuestionId());
			gradeRule.setQuestionId(question.getQuestionId());
			gradeRule.setVariateId(variate.getVariateId());
			this.save(gradeRule);
		}
	}

	@Override
	public List<VariateVO> getVariateVOList(String classId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("   v.`variate_id` variateId,");
		sb.append("   v.`variate_name` variateName ");
		sb.append(" FROM");
		sb.append("   review_variate v ");

		List<VariateVO> list = this.getObjectList(sb.toString(), null, VariateVO.class);
		List<ReviewGradeRuleEntity> gradeRuleList = null;
		for(VariateVO variate : list) {
			gradeRuleList = this.findHql(" from ReviewGradeRuleEntity where variateId=?", new Object[]{variate.getVariateId()});
			if(gradeRuleList.size() == 0) {
				gradeRuleList = new ArrayList<ReviewGradeRuleEntity>();
				for(int i=0;i<8;i++) {
					gradeRuleList.add(new ReviewGradeRuleEntity());
				}
			}
			variate.setGradeRuleList(gradeRuleList);
		}
		return list;
	}

	@Override
	public List<ReviewGradeRuleEntity> getVariateQuestionList(String variateId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("   r.`cal_symbol` calSymbol,");
		sb.append("   r.`rule_id` ruleId,");
		sb.append("   r.`variate_id` variateId,");
		sb.append("   q.`question_num` questionId ");
		sb.append(" FROM");
		sb.append("   review_grade_rule r INNER JOIN");
		sb.append("   review_question q ");
		sb.append("   ON r.`question_id`=q.`question_id`");
		sb.append(" WHERE r.variate_id=:variateId");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("variateId", variateId);
		return this.getObjectList(sb.toString(), map, ReviewGradeRuleEntity.class);
	}

	@Override
	public void sortVariate(String sortNums) {
		String[] arr = sortNums.split(",");
		String sql = "update review_variate set sort_num=? where variate_id=?";
		String sqlC = "select count(variate_id) from review_variate where sort_num=? and variate_id=?";
		Long count = 0L;
		for(int i = 0; i<arr.length; i++) {
			count = this.getCountForJdbcParam(sqlC, new Object[]{arr[i].split("_")[1],arr[i].split("_")[0]});
			if(count == 0) {
				this.executeSql(sql, new Object[]{arr[i].split("_")[1],arr[i].split("_")[0]});
			}
			
		}
	}
}
