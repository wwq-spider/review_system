package com.review.front.service.impl;

import com.review.common.Arith;
import com.review.common.Constants;
import com.review.common.DateUtil;
import com.review.front.entity.ReviewReportResultEntity;
import com.review.front.entity.ReviewResultEntity;
import com.review.front.service.ReviewFrontService;
import com.review.front.vo.ReviewResultVO;
import com.review.front.vo.SelectVO;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.question.entity.ReviewQuestionAnswerEntity;
import com.review.manage.question.service.ReviewQuestionAnswerServiceI;
import com.review.manage.question.vo.QuestionVO;
import com.review.manage.record.entity.ReviewRecordEntity;
import com.review.manage.record.service.ReviewRecordServiceI;
import com.review.manage.report.entity.ReviewReportEntity;
import com.review.manage.report.entity.ReviewReportGradeEntity;
import com.review.manage.report.entity.ReviewReportVariateEntity;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.variate.entity.ReviewVariateEntity;
import com.review.manage.variate.entity.ReviewVariateGradeEntity;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.testng.collections.Lists;

import java.util.*;
import java.util.Map.Entry;

@Service("reviewFrontService")
@Transactional
public class ReviewFrontServiceImpl extends CommonServiceImpl implements ReviewFrontService{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReviewQuestionAnswerServiceI questionAnswerServiceI;

	@Autowired
	private IReviewProjectService reviewProjectService;

	@Autowired
	private ReviewRecordServiceI reviewRecordService;

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
		sb.append("   q.`question_type` questionType,");
		sb.append("   q.`picture_attach` pictureAttach,");
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
			sb.append(" LIMIT ");
			sb.append(page);
			sb.append(",");
			sb.append(num);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", classId);
		return this.getObjectList(sb.toString(), map, QuestionVO.class);
	}

	
	@Override
	public QuestionVO getQuestionDetail(String classId,int page) {
		//每次查2条 用于判断是否为最后一题
		List<QuestionVO> list = getQuestionVOList(classId, page, 2);
		if (CollectionUtils.isNotEmpty(list)) {
			QuestionVO question = list.get(0);
			question.setMultiple("N");
			question.setIsLastQuestion(list.size() == 1 ? Constants.YES : Constants.NO);
			List<SelectVO> selectList = this.getSelectVOList(question.getQuestionId());
			question.setSelectList(selectList);
			return question;
		}
		return null;
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
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("questionId", questionId);
		List<SelectVO> list = this.getObjectList(sb.toString(), map, SelectVO.class);
		return list;
	}

	@Override
	public ReviewResultEntity completeReview(List<QuestionVO> resultList, String classId, ReviewUserEntity reviewUser) {
		sort(resultList);
		//存放变量因子对应的分值
		Map<String, Double> map = new HashMap<String, Double>();
		QuestionVO question = null;
		Double totalGrade = 0.0;
		String[] idArr = null;

		//添加测评结果
		ReviewResultEntity reviewResult = new ReviewResultEntity();
		reviewResult.setUserId(reviewUser.getUserId());
		reviewResult.setClassId(classId);
		reviewResult.setCreateTime(new Date());
		reviewResult.setCreateBy(reviewUser.getUserName());
		reviewResult.setProjectId(resultList.get(0).getProjectId()); //项目id
		ReviewProjectEntity reviewProject = null;
		String groupId = "";
		if(reviewResult.getProjectId() != null && reviewResult.getProjectId() > 0) {
			reviewProject = reviewProjectService.get(ReviewProjectEntity.class, reviewResult.getProjectId());
			groupId = reviewProject.getGroupId();
		} else {
			groupId = reviewUser.getGroupId().split(",")[0];
		}
		this.save(reviewResult);

		List<ReviewQuestionAnswerEntity> reviewQuestionAnswerList = Lists.newArrayList(1200);

		Date now = new Date();
		//计算因子对应的分值
		for(int i=0; i<resultList.size(); i++) {
			question = resultList.get(i);
			ReviewQuestionAnswerEntity reviewQuestionAnswer = new ReviewQuestionAnswerEntity();
			try {
				MyBeanUtils.copyBean2Bean(reviewQuestionAnswer, question);
				reviewQuestionAnswer.setGroupId(groupId);
				reviewQuestionAnswer.setUserId(reviewUser.getUserId());
				reviewQuestionAnswer.setUserName(reviewUser.getUserName());
				reviewQuestionAnswer.setMobilePhone(reviewUser.getMobilePhone());
				reviewQuestionAnswer.setSex(reviewUser.getSex());
				reviewQuestionAnswer.setAge(reviewUser.getAge());
				reviewQuestionAnswer.setCreateTime(now);
				reviewQuestionAnswer.setResultId(reviewResult.getResultId());
				reviewQuestionAnswerList.add(reviewQuestionAnswer);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			if (StringUtils.isBlank(question.getSelectGrade())) {
				continue;
			}
			double grade = Double.valueOf(question.getSelectGrade());
			if(!"".equals(StringUtils.trimToEmpty(question.getVariateId()))) {
				idArr = question.getVariateId().split(",");
				for(int j = 0; j < idArr.length; j++) {
					if(map.get(idArr[j]) != null) {
						map.put(idArr[j], Arith.add(map.get(idArr[j]), grade));
					} else {
						map.put(idArr[j], grade);
					}
				}
			}
			totalGrade = Arith.add(totalGrade, grade);
		}
		reviewResult.setGradeTotal(totalGrade);

		//保存答题记录
		if (reviewQuestionAnswerList.size() > 0) {
			questionAnswerServiceI.batchSave(reviewQuestionAnswerList);
			reviewQuestionAnswerList.clear();
		}
		
		//查询某分类下的报告
		List<ReviewReportEntity> reportList = this.findByProperty(ReviewReportEntity.class, "classId", classId);
		
		//报告因子
		List<ReviewReportVariateEntity> reportVariateList = null;
		
		//报告结果分值范围
		List<ReviewReportGradeEntity> gradeList = null;
		
		ReviewReportResultEntity reportResult = null;
		Double grade = null;
		
		//将所有报告的结果拼装起来成字符串
		StringBuilder resultExplain = new StringBuilder();
		
		String variateResultExplain = "";
		List<ReviewVariateGradeEntity> variateGradeList = null;
		ReviewVariateGradeEntity variateGrade = null;
		ReviewVariateEntity variateEntity = null;
		Double variateTotalGrade = 0.0;

		List<String> resultCombine = new ArrayList<>();
		Integer levelGradeTotal = 0;

		//遍历因子
		for(Entry<String, Double> entry : map.entrySet()) {	
			variateEntity = this.get(ReviewVariateEntity.class, entry.getKey());
			reportResult = new ReviewReportResultEntity();
			variateGradeList = this.findByProperty(ReviewVariateGradeEntity.class, "variateId", entry.getKey());
			
			//System.out.println("1："+entry.getValue());
			
			//计算因子最后得分
			variateTotalGrade = calVariateGrade(variateEntity.getCalSymbol1(), entry.getValue(), variateEntity.getCalTotal1());
			
			//System.out.println("2："+variateTotalGrade);
			
			variateTotalGrade = calVariateGrade(variateEntity.getCalSymbol(), variateTotalGrade, variateEntity.getCalTotal());

			//System.out.println("3："+variateTotalGrade);

			int levelGrade = 0;

			for(int i=0; i< variateGradeList.size();i++) {
				variateGrade = variateGradeList.get(i);
				if(variateGrade.getGradeSmall() <= variateTotalGrade && variateTotalGrade <= variateGrade.getGradeBig()) {
					variateResultExplain = variateGrade.getResultExplain();
					resultCombine.add(variateResultExplain);
					if (variateGrade.getLevelGrade() != null) {
						levelGrade = variateGrade.getLevelGrade();
						levelGradeTotal += levelGrade;
					}
					break;
				}
			}
			
			//拼装因子的结果描述
			if(resultExplain.length() == 0) {
				resultExplain.append(variateEntity.getVariateName())
						.append("得分:").append(variateTotalGrade)
						.append("; 结果:").append(variateResultExplain);
			} else {
				resultExplain.append("<br>").append(variateEntity.getVariateName())
						.append("得分:").append(variateTotalGrade)
						.append("; 结果:").append(variateResultExplain);
			}
			
			//添加因子测试结果
			if(variateEntity.getSortNum() != null) {
				reportResult.setCreateTime(DateUtil.addMillSecond(now, variateEntity.getSortNum()* 1000));
			} 
			
			reportResult.setExplainResult(variateResultExplain);
			reportResult.setGrade(variateTotalGrade);
			reportResult.setLevelGrade(levelGrade);
			reportResult.setReportId(entry.getKey());
			reportResult.setReportName(variateEntity.getVariateName());
			reportResult.setResultId(reviewResult.getResultId());
			reportResult.setResultType("1");//因子类型
			this.save(reportResult);
		}
		
		//循环报告结果
		for(ReviewReportEntity report : reportList) {
			grade = 0.0;

			reportResult = new ReviewReportResultEntity();
			
			reportVariateList = this.findByProperty(ReviewReportVariateEntity.class, "reportId", report.getReportId());
			for(ReviewReportVariateEntity reportVariate : reportVariateList) {
				if(map.get(reportVariate.getVariateId()) != null) {
					grade = Arith.add(grade, map.get(reportVariate.getVariateId()));
				}
			}

			int levelGrade = 0;

			gradeList = this.findByProperty(ReviewReportGradeEntity.class, "reportId", report.getReportId());
			for(ReviewReportGradeEntity reportGrade : gradeList) {
				if(grade <= reportGrade.getGradeBig() && grade >= reportGrade.getGradeSmall()) {
					reportResult.setExplainResult(reportGrade.getResultExplain());
					if(reportGrade.getLevelGrade() != null) {
						levelGrade = reportGrade.getLevelGrade();
					}
					//拼装报告的结果描述
					if(resultExplain.length() == 0) {
						resultExplain.append(report.getReportName())
								.append("得分:").append(grade)
								.append("; 结果:").append(reportGrade.getResultExplain());
					} else {
						resultExplain.append("<br>").append(report.getReportName())
								.append("得分:").append(grade)
								.append("; 结果:").append(reportGrade.getResultExplain());
					}
				}
			}
			
			if(gradeList.size() > 0 && grade > 0) {
				reportResult.setReportId(reviewResult.getResultId());
				reportResult.setGrade(grade);
				reportResult.setLevelGrade(levelGrade);
				reportResult.setCreateTime(report.getCreateTime());
				reportResult.setReportId(report.getReportId());
				reportResult.setReportName(report.getReportName());
				reportResult.setResultId(reviewResult.getResultId());
				reportResult.setResultType("2");//报告类型
				this.save(reportResult);
			}
		}
		//将结果存起来
		reviewResult.setReviewResult(resultExplain.toString());
		reviewResult.setLevelGrade(levelGradeTotal);
		reviewResult.setCombineVarResult(StringUtils.join(resultCombine, ","));
		this.saveOrUpdate(reviewResult);
		return reviewResult;
	}
	
	/**
	 * 计算因子得分 
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
	 * 按照因子排序
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

	@Override
	public List<ReviewResultVO> getReportResults(String userId, Long projectId) {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuilder sql = new StringBuilder("select r.result_id resultId, " +
				"       r.class_id classId, " +
				"       DATE_FORMAT(r.`create_time`,'%Y-%m-%e %H:%i:%S') createTime," +
				"       r.grade_total reportGrade," +
				"       c.banner_img classCover," +
				"       c.title classTitle" +
				" from review_result r inner join review_class c on r.class_id=c.class_id  where r.user_id =:userId");
		paramMap.put("userId", userId);
		if (projectId != null && projectId > 0) {
			sql.append(" and r.project_id=:projectId ");
			paramMap.put("projectId", projectId);
		}
		sql.append(" order by r.`create_time` desc");
		return this.getObjectList(sql.toString(), paramMap, ReviewResultVO.class);
	}

	@Override
	public List<ReviewResultVO> getReviewReports(String userId, Long projectId) {
		Map<String, Object> paramMap = new HashMap<>();
		StringBuilder sql =new StringBuilder("select r.result_id                                  resultId,\n" +
				"       r.class_id                                        classId,\n" +
				"       DATE_FORMAT(r.`create_time`, '%Y-%m-%e %H:%i:%S') createTime,\n" +
				"       r.grade_total                                     reportGrade,\n" +
				"       c.banner_img                                      classCover,\n" +
				"       c.title                                           classTitle\n" +
				" from review_result r\n" +
				"         inner join review_class c on r.class_id = c.class_id\n" );

		if (projectId != null && projectId > 0) {
			sql.append(" inner join (select id from review_project where id=:projectId and show_report = 2) p on r.project_id = p.id ");
			sql.append(" where r.user_id =:userId");
			paramMap.put("projectId", projectId);
		} else {
			sql.append(" left join (select id from review_project where show_report = 2) p on r.project_id = p.id " +
					" where r.user_id =:userId and (r.project_id is null or r.project_id = 0 or p.id != null)");
		}
		sql.append(" order by r.`create_time` desc");
		paramMap.put("userId", userId);
		return this.getObjectList(sql.toString(), paramMap, ReviewResultVO.class);
	}

	@Override
	public JSONObject register(ReviewUserEntity reviewUser) {

		List<ReviewUserEntity> reviewUserList = this.findHql("from ReviewUserEntity where mobilePhone=?", new Object[]{reviewUser.getMobilePhone()});
		JSONObject jsonObject = new JSONObject();

//		if(CollectionUtils.isEmpty(reviewUserList)) {
//			logger.warn("register failed, mobilephone" + reviewUser.getMobilePhone() + " not exists");
//			jsonObject.put("code", 1000);
//			jsonObject.put("msg", "不是该项目测评用户");
//			return jsonObject;
//		}

		ReviewUserEntity reviewUserEntity = CollectionUtils.isNotEmpty(reviewUserList) ? reviewUserList.get(0) : new ReviewUserEntity();
		//判断用户是否已经绑定过openid
		if(StringUtils.isNotBlank(reviewUserEntity.getOpenid()) && !reviewUserEntity.getOpenid().equals(reviewUser.getOpenid())) {
			jsonObject.put("code", 1001);
			jsonObject.put("msg", "用户已注册");
			return jsonObject;
		}

		try {
			MyBeanUtils.copyBean2Bean(reviewUserEntity, reviewUser);
			//存储用户额外信息
			if (!reviewUser.getExtraObj().isEmpty()) {
				reviewUserEntity.setExtra(com.alibaba.fastjson.JSONObject.toJSONString(reviewUser.getExtraObj()));
			}
		} catch (Exception e) {
			logger.error("copyBean2Bean error, ", e);
			jsonObject.put("code", 500);
			jsonObject.put("msg", "注册失败");
			return jsonObject;
		}

		//设置用户组
		boolean flag = setUserGroup(reviewUser.getProjectId(), reviewUserEntity);
		if (!flag) {
			jsonObject.put("code", 1000);
			jsonObject.put("msg", "用户没有该项目测评权限，请联系管理员");
			return jsonObject;
		}

		if (StringUtils.isBlank(reviewUserEntity.getGroupId())) { //设置默认用户组
			reviewUserEntity.setGroupId("1");
		}

		if (StringUtils.isBlank(reviewUserEntity.getUserId())) {
			reviewUserEntity.setCreateTime(new Date());
			reviewUserEntity.setUpdateTime(reviewUserEntity.getUpdateTime());
			reviewUserEntity.setSource(Constants.UserSource.Register);
			this.save(reviewUserEntity);
		} else {
			reviewUserEntity.setUpdateTime(new Date());
			this.saveOrUpdate(reviewUserEntity);
		}
		jsonObject.put("code", 200);
		jsonObject.put("userId", reviewUserEntity.getUserId());
		return jsonObject;
	}

	/**
	 * 设置用户组 同时判断 如果是指定项目测评 判断用户是否有测评权限
	 * @param projectId
	 * @param reviewUserEntity
	 * @return
	 */
	private boolean setUserGroup(Long projectId, ReviewUserEntity reviewUserEntity) {
		if (projectId != null && projectId > 0) { //是否加入用户组
			ReviewProjectEntity reviewProject = reviewProjectService.get(ReviewProjectEntity.class, projectId);
			if (reviewProject == null) {
				return false;
			}
			String userGroupId = reviewUserEntity.getGroupId();
			if (reviewProject.getIsOpen() == 2) {
				if (StringUtils.isBlank(userGroupId)) {
					reviewUserEntity.setGroupId(reviewProject.getGroupId());
				} else if (userGroupId.indexOf(reviewProject.getGroupId()) == -1) {
					reviewUserEntity.setGroupId(reviewUserEntity.getGroupId() +"," + reviewProject.getGroupId());
				}
			} else if (StringUtils.isBlank(userGroupId) || userGroupId.indexOf(reviewProject.getGroupId()) == -1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ReviewUserEntity getUserInfo(String openid) {
		List<ReviewUserEntity> reviewUserList = this.findHql("from ReviewUserEntity where openid=?", new Object[]{openid});
		if (CollectionUtils.isEmpty(reviewUserList)) {
			return null;
		}
		return reviewUserList.get(0);
	}

	@Override
	public List<ReviewClassVO> getReviewClassByGroupId(String groupId, String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("	  c.`class_id` classId,");
		sb.append("   c.`sort_id` sortId,");
		sb.append("   c.`banner_img` bannerImg,");
		sb.append("   c.`status`,");
		sb.append("   c.`type`,");
		sb.append("   c.`title`,");
		sb.append("   c.`charge`,");
		sb.append("   c.`org_price` orgPrice,");
		sb.append("   c.`dicount_price` dicountPrice,");
		sb.append("   (c.`org_price` - c.`dicount_price`) as realPrice,");
		sb.append("   c.`class_desc` classDesc,");
		sb.append("   p.`project_name` projectName,");
		sb.append("   p.`id` projectId");
		sb.append(" FROM  ");
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("groupId", groupId);
		sb.append("   review_class c inner join review_project_class pc on c.class_id=pc.class_id");
		sb.append(" inner join review_project p on pc.project_id=p.id ");
		sb.append(" WHERE c.`status`=1 and p.group_id=:groupId");
		sb.append(" ORDER BY p.id, c.`sort_id` ASC ");
		List<ReviewClassVO> reviewClassList = this.getObjectList(sb.toString(), paramMap, ReviewClassVO.class);

		//封装测评记录
		Map<String, List<ReviewClassVO>> resultMap = new HashMap<>();
		paramMap.put("userId", userId);
		List<ReviewClassVO> reviewRecordList = reviewRecordService.getObjectList("select class_id classId, count(result_id) as reviewTimes " +
				"from review_result\n" +
				"where user_id =:userId\n" +
				"group by class_id\n" +
				"order by count(result_id) desc", paramMap, ReviewClassVO.class);

		if (CollectionUtils.isNotEmpty(reviewRecordList)) {
			Map<String, Integer> map = new HashMap<>();
			for (ReviewClassVO reviewRecord : reviewRecordList) {
				map.put(reviewRecord.getClassId(), reviewRecord.getReviewTimes());
			}
			for (ReviewClassVO reviewClass : reviewClassList) {
				if (map.get(reviewClass.getClassId()) != null) {
					reviewClass.setReviewTimes(map.get(reviewClass.getClassId()));
				}
				String key = reviewClass.getProjectId() + reviewClass.getProjectName();
				if (resultMap.get(key) == null) {
					resultMap.put(key, new ArrayList<>());
				}
				resultMap.get(key).add(reviewClass);
			}
		}
		return reviewClassList;
	}

	@Override
	public String getNextClassId(String curClassId, String groupId, String userId) {
		//获取测评量表
		List<ReviewClassVO> reviewClassList = getReviewClassByGroupId(groupId, userId);
		int size = reviewClassList.size();
		for (int i=0; i < size; i++) {
			ReviewClassVO reviewClass = reviewClassList.get(i);
			if (curClassId.equals(reviewClass.getClassId()) && i < size-1) {
				for (int j = i+1; j<size; j++) {
					ReviewClassVO reviewClassNew = reviewClassList.get(j);
					if (reviewClassNew.getReviewTimes() == 0) {
						return reviewClassNew.getClassId();
					}
				}
			}
		}
		return null;
	}
}
