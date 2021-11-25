package com.review.manage.question.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.common.OssUtils;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.review.front.vo.SelectVO;
import com.review.manage.question.entity.QuestionImportEntity;
import com.review.manage.question.entity.ReviewAnswerEntity;
import com.review.manage.question.entity.ReviewQuestionEntity;
import com.review.manage.question.service.ReviewService;
import com.review.manage.question.vo.QuestionVO;
import com.review.manage.reviewClass.entity.ReviewQuestionClassEntity;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@SuppressWarnings("deprecation")
@Service("reviewService")
@Transactional
public class ReviewServiceImpl extends CommonServiceImpl implements ReviewService{

	@Override
	public List<Map<String, Object>> getQuestionList(String content,
			String questionType, String reviewType, DataGrid datagrid) {
		String sqlWhere = getSqlWhere(content, questionType, reviewType);
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("	  q.`question_id` questionId,");
		sb.append("   q.`content` content,");
		sb.append("   q.`is_important` isImportant,");
		sb.append("   DATE_FORMAT(q.`create_time`,'%Y-%m-%e %H:%i:%S') AS createTime,");
		sb.append("   q.`create_by` createBy,");
		sb.append("   q.`question_type` questionType,");
		sb.append("   q.`picture_attach` pictureAttach");
		//sb.append("   q.`review_type` reviewType ");
		sb.append(" FROM  ");
		sb.append("   review_question q LEFT JOIN review_question_class c ");
		sb.append("   on q.question_id=c.question_id");
	
		//查询条件
		if(!"".equals(sqlWhere)) {
			sb.append(" WHERE "+sqlWhere);
		}
		sb.append(" GROUP BY q.`question_id`");
		sb.append(" ORDER BY q.`question_id` asc ");
		return this.findForJdbc(sb.toString(), datagrid.getPage(), datagrid.getRows());
	}

	@Override
	public Long getQuestionCount(String content, String questionType,
			String reviewType) {
		String sqlWhere = getSqlWhere(content, questionType, reviewType);
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT ");
		sb.append("	  COUNT(q.`question_id`)");;
		sb.append(" FROM  ");
		sb.append("   review_question q LEFT JOIN review_question_class c ");
		sb.append("   on q.question_id=c.question_id");
		
		//查询条件
		if(!"".equals(sqlWhere)) {
			sb.append(" WHERE "+sqlWhere);
		}
		return this.getCountForJdbc(sb.toString());
	}

	@Override
	public String addQuestion(QuestionVO question,HttpServletRequest request) throws IOException {
		String classId = request.getParameter("classId");
		ReviewQuestionEntity reviewQuestion = this.getQuestionByQnum(classId, question.getQuestionNum());
		if(reviewQuestion == null) {
			reviewQuestion = new ReviewQuestionEntity();
			reviewQuestion.setContent(question.getContent());
			reviewQuestion.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
			reviewQuestion.setCreateTime(new Date());
			reviewQuestion.setIsImportant(question.getIsImportant());
			reviewQuestion.setQuestionType(question.getQuestionType());
			reviewQuestion.setQuestionNum(question.getQuestionNum());
			reviewQuestion.setRightAnswer(question.getRightAnswer());
			reviewQuestion.setPictureAttach(CommonUtils.saveCoverImg(question.getContentImg(), Constants.ReviewQuestionDir));
			this.save(reviewQuestion);
			
			//添加题目分类
			ReviewQuestionClassEntity questionClass = new ReviewQuestionClassEntity();
			questionClass.setClassId(classId);
			questionClass.setQuestionId(reviewQuestion.getQuestionId());
			this.save(questionClass);
			
			List<SelectVO> selectList = question.getSelectList();
			SelectVO select = null;
			ReviewAnswerEntity answerEntity = null;
			for(int i=0; i<selectList.size();i++) {
				select = selectList.get(i);
				answerEntity = new ReviewAnswerEntity();
				answerEntity.setQuestionId(reviewQuestion.getQuestionId());
				answerEntity.setAnswerCode(select.getSelCode());
				answerEntity.setAnswerContent(select.getSelectContent());
				if(!"".equals(StringUtils.trimToEmpty(select.getSelectGrade()))) {
					answerEntity.setGrade(Double.valueOf(select.getSelectGrade()));
				}
				answerEntity.setPictureAttach(CommonUtils.saveCoverImg(select.getContentImg(), Constants.ReviewAnswerDir));
				this.save(answerEntity);
			}
			return "succ";
		} else {
			return "fail";
		}
	}

	/**
	 * 判断是否为正确 答案
	 * @param rightAnswer
	 * @param selCode
	 * @return
	 */
	@SuppressWarnings("unused")
	private String isRight(String rightAnswer, String selCode) {
		if("".equals(StringUtils.trimToEmpty(rightAnswer))) {
			return "";
		} else {
			if(selCode.equals(StringUtils.trimToEmpty(rightAnswer))) {
				return "Y";
			} else {
				return "N";
			}
		}
	}
	
	@Override
	public void updateQuestion(QuestionVO question,HttpServletRequest request) throws IOException {
		
		//更新问题
		ReviewQuestionEntity reviewQuestion = this.get(ReviewQuestionEntity.class,question.getQuestionId());
		reviewQuestion.setContent(question.getContent());
		reviewQuestion.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
		reviewQuestion.setCreateTime(new Date());
		reviewQuestion.setIsImportant(question.getIsImportant());
		reviewQuestion.setQuestionType(question.getQuestionType());
		reviewQuestion.setRightAnswer(question.getRightAnswer());

		String path = CommonUtils.saveCoverImg(question.getContentImg(), Constants.ReviewQuestionDir);
		if (StringUtils.isNotBlank(path)) {
			reviewQuestion.setPictureAttach(path);
		}
		this.saveOrUpdate(reviewQuestion);
		
		//删除选项
		String delSelectIds = request.getParameter("delSelectIds");
		if(!"".equals(StringUtils.trimToEmpty(delSelectIds))) {
			String[] arr = delSelectIds.split(",");
			for(String selectId : arr) {
				this.deleteEntityById(ReviewAnswerEntity.class, selectId);
			}
		}
		
		List<SelectVO> selectList = question.getSelectList();
		SelectVO select = null;
		ReviewAnswerEntity answerEntity = null;
		for(int i=0; i<selectList.size(); i++) {
			select = selectList.get(i);
			if(!"".equals(StringUtils.trimToEmpty(select.getSelectId()))) {
				answerEntity = this.get(ReviewAnswerEntity.class, select.getSelectId());
			} else {
				answerEntity = new ReviewAnswerEntity();
				answerEntity.setQuestionId(reviewQuestion.getQuestionId());
			}
			
			answerEntity.setAnswerCode(select.getSelCode());
			answerEntity.setAnswerContent(select.getSelectContent());
			if(!"".equals(StringUtils.trimToEmpty(select.getSelectGrade()))) {
				answerEntity.setGrade(Double.valueOf(select.getSelectGrade()));
			}
			String pathA = CommonUtils.saveCoverImg(select.getContentImg(), Constants.ReviewAnswerDir);
			if (StringUtils.isNotBlank(pathA)) {
				answerEntity.setPictureAttach(pathA);
			}

			this.saveOrUpdate(answerEntity);
		}
	}

	/**
	 * 更新选项
	 * @param reviewAnswer
	 * @param selText
	 * @param selGrade
	 * @param selCode
	 * @param questionId
	 * @param isRight
	 */
	@SuppressWarnings("unused")
	private void updateReviewAnswer(ReviewAnswerEntity reviewAnswer,String selText,Integer selGrade, 
			String selCode, Integer questionId,String isRight) {
		
		reviewAnswer.setAnswerCode(selCode);
		reviewAnswer.setAnswerContent(selText);
		if(!"".equals(StringUtils.trimToEmpty(selCode))) {
			reviewAnswer.setGrade(Double.valueOf(selGrade));
		}
		reviewAnswer.setQuestionId(questionId);
		reviewAnswer.setIsRight(isRight);
		this.saveOrUpdate(reviewAnswer);
	}
	
	@Override
	public void delQuestion(String questionId) {
		
		String querySql = "select question_num questionNum,qc.class_id classId from review_question q,review_question_class qc where q.question_id=qc.question_id AND q.question_id=?";
		Map<String, Object> map = this.findOneForJdbc(querySql, new Object[]{questionId});
		
		//先删除题目--分类关联表数据
		String sqlc = "delete from review_question_class where question_id=?";
		this.executeSql(sqlc, new Object[]{questionId});
		
		//再删除题目--因子关联表数据
		String sqlv  = "DELETE FROM review_grade_rule WHERE question_id=?";
		this.executeSql(sqlv, new Object[]{questionId});
		
		//再删除题目--选项关联表数据
		String sql = "delete from review_answer where question_id=?";
		this.executeSql(sql, new Object[]{questionId});
		
		//最后删除题目
		this.deleteEntityById(ReviewQuestionEntity.class, Integer.parseInt(questionId));
		
		//更新题目编号
		String updSql = "UPDATE review_question q inner join review_question_class qc on q.question_id=qc.question_id and qc.class_id=? SET q.question_num = q.question_num-1 WHERE q.question_num > ? ";
		this.executeSql(updSql, new Object[]{map.get("classId").toString(),Integer.parseInt(map.get("questionNum").toString())});
	}

	/**
	 * 拼查询条件（where语句）
	 * @param content
	 * @param questionType
	 * @param reviewType
	 * @return
	 */
	private String getSqlWhere(String content,
			String questionType, String reviewType) {
		
		// 拼出条件语句
		String sqlWhere = "";
		
		//题目内容
		if (!"".equals(StringUtils.trimToEmpty(content))) {
			
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " q.content like '%" + StringUtils.trimToEmpty(content) + "%'";
		}
		
		//题目类型
		if(!"".equals(StringUtils.trimToEmpty(questionType))) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " q.question_type='" + questionType + "'";
		}
		
		//测评类型
		if(!"".equals(StringUtils.trimToEmpty(reviewType))) {
			if (!sqlWhere.isEmpty()) {
				sqlWhere += " and";
			}
			sqlWhere += " c.class_id='" + reviewType + "'";
		}
		return sqlWhere;
	}

	@Override
	public QuestionVO getQuestionDetail(String questionId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT    ");
		sb.append("  q.`content` content,");
		sb.append("  q.`is_important` isImportant,");
		sb.append("  q.`picture_attach` pictureAttach,");
		sb.append("  q.`question_id` questionId,");
		sb.append("  q.`question_num` questionNum,");
		sb.append("  q.`question_type` questionType,");
		sb.append("  q.`right_answer` rightAnswer");
		sb.append(" FROM     ");
		sb.append("  review_question q ");
		sb.append(" WHERE q.question_id=:questionId ");
		Map<String, String> map = new HashMap<String, String>();
		map.put("questionId", questionId);
		return this.getObjectDetail(sb.toString(), map, QuestionVO.class);
	}

	@Override
	public List<QuestionVO> getAnswersByQuestionId(String questionId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT    ");
		sb.append("  a.`answer_id` answerId,");
		sb.append("  a.`grade` selectGrade,");
		sb.append("  a.`answer_content` selectContent,");
		sb.append("  a.`picture_attach` pictureAttach,");
		sb.append("  a.`answer_code` selCode ");
		sb.append(" FROM     ");
		sb.append("  review_answer a ");
		sb.append(" WHERE a.`question_id` =:questionId ");
		sb.append(" ORDER BY a.`answer_code` ");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionId", questionId);
		return this.getObjectList(sb.toString(), map, QuestionVO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String importExcel(Map<String, MultipartFile> fileMap, String classId) throws Exception {
		String questionIds = "";
		
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			List<QuestionImportEntity> listQuestions;
			listQuestions = (List<QuestionImportEntity>) ExcelUtil.importExcelByIs(file.getInputStream(), QuestionImportEntity.class);
			
			ReviewQuestionEntity questionEntity = null;
			
			List<ReviewAnswerEntity> selectList = null;
			ReviewQuestionClassEntity questionClass = null;
			
			//先删除该分类下的所有题目
			String sql = "delete q.* from review_question q inner join review_question_class qc on q.question_id=qc.question_id and qc.class_id=?";
			this.executeSql(sql, new Object[]{classId});
			//删除分类题目关联 
			String sqlC = "delete from review_question_class where class_id=?";
			this.executeSql(sqlC, new Object[]{classId});
			
			for(QuestionImportEntity questionImport : listQuestions) {
				questionEntity= this.getQuestionByQnum(classId, questionImport.getQuestionNum());
				if(questionEntity == null) {
					questionEntity = new ReviewQuestionEntity();
					questionClass = new ReviewQuestionClassEntity();
					questionEntity.setContent(questionImport.getQuestionContent());
					questionEntity.setQuestionNum(questionImport.getQuestionNum());
					questionEntity.setCreateTime(new Date());
					questionEntity.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
					this.save(questionEntity);
					
					questionClass.setClassId(classId);
					questionClass.setQuestionId(questionEntity.getQuestionId());
					
					this.save(questionClass);
					
					selectList = getSelectInfo(questionEntity.getQuestionId(), questionImport);
					if(selectList.size() > 0) {
						for(ReviewAnswerEntity answerEntity : selectList) {
							this.save(answerEntity);
						}
					}
				} else {
					questionIds += questionImport.getQuestionNum() + ",";
					continue;
					
				}
			}
		//break; // 不支持多个文件导入？
		}
		return questionIds;
	}
	
	/**
	 * 封装每道题的选项内容
	 * @param questionId
	 * @param questionImport
	 * @return
	 * @throws Exception
	 */
	private List<ReviewAnswerEntity> getSelectInfo(Integer questionId, QuestionImportEntity questionImport) throws Exception {
		Field[] fieldArr = QuestionImportEntity.class.getDeclaredFields();
		Field field = null;
		String filedName = "";
		String getMethodName = "";
		Method getMethod = null;
		List<ReviewAnswerEntity> selectList = new ArrayList<ReviewAnswerEntity>();
		Map<String, ReviewAnswerEntity> map = new HashMap<String, ReviewAnswerEntity>();
		for(int i = 0; i < fieldArr.length; i++) {
			field = fieldArr[i];
			filedName = field.getName();
			if(filedName.contains("select") || filedName.contains("grade")) {
				getMethodName = "get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
				getMethod = QuestionImportEntity.class.getMethod(getMethodName);
				Object obj = getMethod.invoke(questionImport);
				if(obj == null || "".equals(StringUtils.trimToEmpty(obj.toString()))) {
					continue;
				} else {
						String selCode = filedName.substring(filedName.length()-1);
						if(map.get(selCode) != null) {
							if(field.getType().getName().equals("java.lang.String")) {
								map.get(selCode).setAnswerContent(obj.toString());
							} else if(field.getType().getName().equals("java.math.BigDecimal")) {
								map.get(selCode).setGrade(Double.valueOf(obj.toString()));
							}
						} else {
							map.put(selCode, new ReviewAnswerEntity());
							map.get(selCode).setAnswerCode(selCode);
							map.get(selCode).setQuestionId(questionId);
							if(field.getType().getName().equals("java.lang.String")) {
								map.get(selCode).setAnswerContent(obj.toString());
							} else if(field.getType().getName().equals("java.math.BigDecimal")) {
								map.get(selCode).setGrade(Double.valueOf(obj.toString()));
							}
						}
					}
				}
			}
		for(Entry<String, ReviewAnswerEntity> entry : map.entrySet()) {
			if(entry.getValue() != null) {
				selectList.add(entry.getValue());
			}
			
		}	
		return selectList;
	}

	@Override
	public Integer getMaxQuestionId(String classId) {
		String sql = "SELECT MAX(q.`question_num`) questionNum FROM review_question q,review_question_class qc WHERE q.`question_id` = qc.`question_id` and qc.class_id=?";
		Map<String, Object> map = this.findOneForJdbc(sql,new Object[]{classId});
		if(map.get("questionNum") == null) {
			return 0;
		} else {
			return Integer.parseInt(map.get("questionNum").toString());
		}
	}

	@Override
	public ReviewQuestionEntity getQuestionByQnum(String classId,
			Integer questionNum) {
		ReviewQuestionEntity questionEntity = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT    ");
		sb.append("  q.`question_num` questionNum,");
		sb.append("  q.`content` content,");
		sb.append("  q.`create_by` createBy,");
		sb.append("  q.`question_id` questionId");
		sb.append(" FROM     ");
		sb.append("  review_question q,");
		sb.append("  review_question_class qc ");
		sb.append(" WHERE q.`question_id` = qc.`question_id` ");
		sb.append("  AND qc.`class_id` =?");
		sb.append("  AND q.`question_num` =?");
		Map<String, Object> map = this.findOneForJdbc(sb.toString(), new Object[]{classId,questionNum});
		if(map != null) {
			if(map.get("questionId") != null) {
				questionEntity = new ReviewQuestionEntity();
				questionEntity.setQuestionId(Integer.parseInt(map.get("questionId").toString()));
			}
		}
		
		return questionEntity;
	}
}
