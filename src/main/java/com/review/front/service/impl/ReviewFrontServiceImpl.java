package com.review.front.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.review.common.Arith;
import com.review.common.DateUtil;
import com.review.front.entity.ReviewReportResultEntity;
import com.review.front.entity.ReviewResultEntity;
import com.review.front.service.ReviewFrontService;
import com.review.front.vo.SelectVO;
import com.review.manage.question.vo.QuestionVO;
import com.review.manage.report.entity.ReviewReportEntity;
import com.review.manage.report.entity.ReviewReportGradeEntity;
import com.review.manage.report.entity.ReviewReportVariateEntity;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.entity.ReviewVariateGradeEntity;

@Service("reviewFrontService")
@Transactional
public class ReviewFrontServiceImpl extends CommonServiceImpl implements ReviewFrontService{

	@Override
	public List<QuestionVO> getQuestionVOList(String classId) {
		return getQuestionVOList(classId, 0, 0);
	}

	@Override
	public List<QuestionVO> getQuestionVOList(String classId,int page, int num) {
		StringBuilder sb =  new StringBuilder();
		sb.append(" SELECT   ");
		sb.append("   q.`content` content,");
		sb.append("   q.`question_id` questionId,");
		sb.append("   q.`question_num` questionNum,");
		sb.append("   q.`question_num` questionNum,");
		sb.append("  (CASE ISNULL(q.`picture_attach`) WHEN 1 THEN  'N' ELSE 'Y' END) AS isAttach,");
		sb.append("   qc.`class_id` classId,");
		sb.append("   (SELECT GROUP_CONCAT(r.variate_id) FROM review_grade_rule r WHERE r.question_id=q.question_id) variateId,");
		sb.append("   (SELECT GROUP_CONCAT(v.variate_name) FROM review_grade_rule r,review_variate v WHERE r.variate_id=v.variate_id AND r.question_id=q.question_id) variateName");
		sb.append(" FROM     ");
		sb.append("   review_question q inner join review_question_class qc on q.`question_id` = qc.`question_id`");
		//sb.append("   left join review_grade_rule r on q.question_id = r.question_id");
		//sb.append("   left join review_variate v on  r.variate_id = v.variate_id");
		sb.append(" WHERE qc.class_id=:classId");
		sb.append(" GROUP BY q.`question_id`");
		sb.append(" ORDER BY q.question_num asc");
		if(num > 0) {
			sb.append(" LIMIT "+page+","+num);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("classId", classId);
		return this.getObjectList(sb.toString(), map, QuestionVO.class);
	}

	
	@Override
	public QuestionVO getQuestionDetail(String classId,int page, int num) {
		List<QuestionVO> list = getQuestionVOList(classId,page, 0);
		QuestionVO question = list.get(page);
		
		if(page == list.size()-1) {
			question.setIsLastQuestion("Y");
		} 
		question.setMultiple("N");
		List<SelectVO> selectList = this.getSelectVOList(question.getQuestionId());
		question.setSelectList(selectList);
		return question;
	}

	@Override
	public List<SelectVO> getSelectVOList(Integer questionId) {
		StringBuilder sb =  new StringBuilder();
		sb.append(" SELECT   ");
		sb.append("   a.`answer_code` selCode,");
		sb.append("   a.`answer_content` selectContent,");
		sb.append("   a.`answer_id` selectId,");
		sb.append("   a.`grade` selectGrade,");
		sb.append("   (CASE ISNULL(a.`picture_attach`) WHEN 1 THEN  'N' ELSE 'Y' END) AS isAttach");
		sb.append(" FROM     ");
		sb.append("   review_answer a ");
		sb.append(" WHERE a.`question_id` =:questionId");
		sb.append(" ORDER BY a.`answer_code` ASC");
		Map<String,String> map = new HashMap<String, String>();
		map.put("questionId", questionId+"");
		List<SelectVO> list = this.getObjectList(sb.toString(), map, SelectVO.class);
		return list;
	}

	@Override
	public ReviewResultEntity completeReview(List<QuestionVO> resultList, String classId, String userId,String userName) {
		sort(resultList);
		//?????????????????????????????????
		Map<String, Double> map = new HashMap<String, Double>();
		QuestionVO question = null;
		Double totalGrade = 0.0;
		String[] idArr = null;
		//???????????????????????????
		for(int i=0; i<resultList.size(); i++) {
			question = resultList.get(i);
			if (StringUtils.isBlank(question.getSelectGrade())) {
				continue;
			}
			double grade = Double.valueOf(question.getSelectGrade());
			if(!"".equals(StringUtils.trimToEmpty(question.getVariateId()))) {
				idArr = question.getVariateId().split(",");
				for(int j=0; j<idArr.length; j++) {
					if(map.get(idArr[j]) != null) {
						map.put(idArr[j], Arith.add(map.get(idArr[j]), grade));
					} else {
						map.put(idArr[j], grade);
					}
				}
			}
			totalGrade = Arith.add(totalGrade, grade);
		}
		
		//???????????????????????????
		List<ReviewReportEntity> reportList = this.findByProperty(ReviewReportEntity.class, "classId", classId);
		
		//????????????
		List<ReviewReportVariateEntity> reportVariateList = null;
		
		//????????????????????????
		List<ReviewReportGradeEntity> gradeList = null;
		
		//??????????????????
		ReviewResultEntity reviewResult = new ReviewResultEntity();
		reviewResult.setUserId(userId);
		reviewResult.setClassId(classId);
		reviewResult.setCreateTime(new Date());
		reviewResult.setCreateBy(userName);
		reviewResult.setGradeTotal(totalGrade);
		this.save(reviewResult);
		
		ReviewReportResultEntity reportResult = null;
		Double grade = null;
		
		//????????????????????????????????????????????????
		StringBuilder resultExplain = new StringBuilder();
		
		String variateResultExplain = "";
		List<ReviewVariateGradeEntity> variateGradeList = null;
		ReviewVariateGradeEntity variateGrade = null;
		ReviewVariateEntity variateEntity = null;
		Double variateTotalGrade = 0.0;
		
		Date now = new Date();
		
		//????????????
		for(Entry<String, Double> entry : map.entrySet()) {	
			variateEntity = this.get(ReviewVariateEntity.class, entry.getKey());
			reportResult = new ReviewReportResultEntity();
			variateGradeList = this.findByProperty(ReviewVariateGradeEntity.class, "variateId", entry.getKey());
			
			//System.out.println("1???"+entry.getValue());
			
			//????????????????????????
			variateTotalGrade = calVariateGrade(variateEntity.getCalSymbol1(), entry.getValue(), variateEntity.getCalTotal1());
			
			//System.out.println("2???"+variateTotalGrade);
			
			variateTotalGrade = calVariateGrade(variateEntity.getCalSymbol(), variateTotalGrade, variateEntity.getCalTotal());
			
			//System.out.println("3???"+variateTotalGrade);
			
			for(int i=0; i< variateGradeList.size();i++) {
				variateGrade = variateGradeList.get(i);
				if(variateGrade.getGradeSmall() <= variateTotalGrade && variateTotalGrade <= variateGrade.getGradeBig()) {
					variateResultExplain = variateGrade.getResultExplain();
				}
			}
			
			//???????????????????????????
			if(resultExplain.length() == 0) {
				resultExplain.append(variateEntity.getVariateName())
						.append("??????:").append(variateTotalGrade)
						.append("; ??????:").append(variateResultExplain);
			} else {
				resultExplain.append("<br>").append(variateEntity.getVariateName())
						.append("??????:").append(variateTotalGrade)
						.append("; ??????:").append(variateResultExplain);
			}
			
			//????????????????????????
			if(variateEntity.getSortNum() != null) {
				reportResult.setCreateTime(DateUtil.addMillSecond(now, variateEntity.getSortNum()* 1000));
			} 
			
			reportResult.setExplainResult(variateResultExplain);
			reportResult.setGrade(variateTotalGrade);
			reportResult.setReportId(entry.getKey());
			reportResult.setReportName(variateEntity.getVariateName());
			reportResult.setResultId(reviewResult.getResultId());
			reportResult.setResultType("1");//????????????
			this.save(reportResult);
		}
		
		//??????????????????
		for(ReviewReportEntity report : reportList) {
			grade = 0.0;

			reportResult = new ReviewReportResultEntity();
			
			reportVariateList = this.findByProperty(ReviewReportVariateEntity.class, "reportId", report.getReportId());
			for(ReviewReportVariateEntity reportVariate : reportVariateList) {
				if(map.get(reportVariate.getVariateId()) != null) {
					grade = Arith.add(grade, map.get(reportVariate.getVariateId()));
				}
			}
			gradeList = this.findByProperty(ReviewReportGradeEntity.class, "reportId", report.getReportId());
			for(ReviewReportGradeEntity reportGrade : gradeList) {
				if(grade <= reportGrade.getGradeBig() && grade >= reportGrade.getGradeSmall()) {
					reportResult.setExplainResult(reportGrade.getResultExplain());
					//???????????????????????????
					if(resultExplain.length() == 0) {
						resultExplain.append(report.getReportName())
								.append("??????:").append(grade)
								.append("; ??????:").append(reportGrade.getResultExplain());
					} else {
						resultExplain.append("<br>").append(report.getReportName())
								.append("??????:").append(grade)
								.append("; ??????:").append(reportGrade.getResultExplain());
					}
				}
			}
			
			if(gradeList.size() > 0 && grade > 0) {
				reportResult.setReportId(reviewResult.getResultId());
				reportResult.setGrade(grade);
				reportResult.setCreateTime(report.getCreateTime());
				reportResult.setReportId(report.getReportId());
				reportResult.setReportName(report.getReportName());
				reportResult.setResultId(reviewResult.getResultId());
				reportResult.setResultType("2");//????????????
				this.save(reportResult);
			}
		}
		//??????????????????
		reviewResult.setReviewResult(resultExplain.toString());
		this.saveOrUpdate(reviewResult);
		return reviewResult;
	}
	
	/**
	 * ?????????????????? 
	 * @param calymbol
	 * @param grade
	 * @param calTotal
	 * @return
	 */
	private Double calVariateGrade(String calymbol, Double grade,Double calTotal) {
		if(calTotal != null) {
			if(calTotal > 0) {
				if("+".equals(StringUtils.trimToEmpty(calymbol))) {
					grade += calTotal;
				} else if ("-".equals(StringUtils.trimToEmpty(calymbol))) {
					grade -= calTotal;
				} else if ("*".equals(StringUtils.trimToEmpty(calymbol))) {
					grade = grade * calTotal;
				} else if ("/".equals(StringUtils.trimToEmpty(calymbol))) {
					grade = Arith.div(grade, calTotal, 2);
				}
			}
		}
		return grade;
	}
	
	/**
	 * ??????????????????
	 * @param resultList
	 */
	private void sort(List<QuestionVO> resultList) {
		Collections.sort(resultList,new Comparator<QuestionVO>(){

			@Override
			public int compare(QuestionVO o1, QuestionVO o2) {
				String str1 = o1.getVariateId();
				String str2 = o2.getVariateId();
				if(str1.equals(str2)){
					return 0;
				}else{
					return 1;
				}
			}
		});
	}

	@Override
	public List<ReviewReportResultEntity> getReportResults(String userId,
			String classId) {
		List<ReviewResultEntity> list = this.findHql("from ReviewResultEntity where classId=? and userId=? order by createTime DESC", 
				new Object[]{classId,userId});
		return this.findHql("from ReviewReportResultEntity where resultId=? order by resultType,createTime ASC", 
				new Object[]{list.get(0).getResultId()});
	}
}
