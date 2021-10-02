package com.review.manage.question.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.review.front.entity.ReviewReportResultEntity;
import com.review.manage.question.service.ReviewQuestionAnswerServiceI;
import com.review.manage.question.vo.ReviewQuestionAnswerVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service("reviewQuestionAnswerService")
@Transactional
public class ReviewQuestionAnswerServiceImpl extends CommonServiceImpl implements ReviewQuestionAnswerServiceI {

    @Autowired
    private ReviewClassService reviewClassService;

    @Override
    public Workbook getExportWorkbook(String groupId, String startTime) {

        List<ReviewQuestionAnswerVO> list = this.getListByGroupId(groupId, startTime);

        Map<String, TreeMap<String, Map<String, Object>>> classMap = new HashMap<>();

        TreeMap<String, String> headerAlias = new TreeMap<>();
        headerAlias.put("0_姓名", "姓名");
        headerAlias.put("0_性别", "性别");
        headerAlias.put("0_年龄", "年龄");
        headerAlias.put("0_手机号", "手机号");
        headerAlias.put("0_完成时间", "完成时间");

        Map<String, String> nameMap = new HashMap<>();

        StringBuilder sortStr = new StringBuilder("0");
        //封装导出列表数据
        for (int i=0; i < list.size(); i++) {
            ReviewQuestionAnswerVO answerQuestion = list.get(i);
            String classId = answerQuestion.getClassId();

            TreeMap<String, Map<String, Object>> singleClassMap = null;
            String qNum = sortStr.append("a").toString();
            headerAlias.put(qNum, answerQuestion.getContent());

            String userKey = answerQuestion.getUserId() + answerQuestion.getCreateTime();
            Map<String, Object> userClassMap = null;
            if (classMap.containsKey(classId)) { //是否包含量表id
                singleClassMap = classMap.get(classId);
                if(singleClassMap.get(userKey) != null) {
                    userClassMap = singleClassMap.get(userKey);
                } else {
                    userClassMap = geneUserRow(answerQuestion, headerAlias); //生成用户行
                    singleClassMap.put(userKey, userClassMap);
                }
            } else {
                singleClassMap = new TreeMap<>();
                classMap.put(classId, singleClassMap);
                ReviewClassEntity reviewClass = reviewClassService.get(ReviewClassEntity.class, classId);
                nameMap.put(classId, reviewClass.getTitle());

                userClassMap = geneUserRow(answerQuestion, headerAlias); //生成用户行
                singleClassMap.put(userKey, userClassMap);
            }
            userClassMap.put(qNum, answerQuestion.getSelectGrade());
        }

        ExcelWriter excelWriter = ExcelUtil.getWriter();
        excelWriter.setHeaderAlias(headerAlias);
        excelWriter.getSheets().clear();
        for (String classId : classMap.keySet()) {
            excelWriter.setSheet(nameMap.get(classId));
            excelWriter.setColumnWidth(1, 40);
            for (int i = 4; i<headerAlias.size(); i++) {
                excelWriter.setColumnWidth(i, 60);
            }
            excelWriter.write(classMap.get(classId).values(), true);
        }
        if (classMap.size() > 0) {
            excelWriter.getWorkbook().removeSheetAt(0);
        }
        return excelWriter.getWorkbook();
    }

    /**
     * 获取单个
     * @param answerQuestion
     * @return
     */
    private Map<String, Object> geneUserRow(ReviewQuestionAnswerVO answerQuestion, TreeMap<String, String> headerAlias) {
        Map<String, Object> userClassMap = new HashMap<>();
        userClassMap.put("0_姓名", answerQuestion.getUserName());
        userClassMap.put("0_性别", "1".equals(answerQuestion.getSex()) ? "男":"女");
        userClassMap.put("0_年龄", answerQuestion.getAge());
        userClassMap.put("0_手机号", answerQuestion.getMobilePhone());
        userClassMap.put("0_完成时间", answerQuestion.getCreateTime());

        List<ReviewReportResultEntity> reportResultList = reviewClassService.findHql("from ReviewReportResultEntity where resultId=? order by resultType asc",
                new Object[]{answerQuestion.getResultId()});
        //导出报告因子得分
        if(CollectionUtils.isNotEmpty(reportResultList)) {
            for (ReviewReportResultEntity reviewReportResult : reportResultList) {
                userClassMap.put("0_" + reviewReportResult.getReportName(), reviewReportResult.getGrade());
                headerAlias.put("0_" + reviewReportResult.getReportName(), reviewReportResult.getReportName());
            }
        }
        return userClassMap;
    }

    @Override
    public List<ReviewQuestionAnswerVO> getListByGroupId(String groupId, String startTime) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);

        StringBuilder sql = new StringBuilder(
                "select class_id classId,\n" +
                        "       user_id                   userID,\n" +
                        "       result_id                 resultId,\n" +
                        "       user_name                 userName,\n" +
                        "       sex                       sex,\n" +
                        "       age                       age,\n" +
                        "       mobile_phone              mobilePhone,\n" +
                        "       group_id                  groupId,\n" +
                        "       question_num              questionNum,\n" +
                        "       concat(question_num, '.', content)              as content,\n" +
                        "       sel_code                                           selCode,\n" +
                        "       select_grade                                       selectGrade,\n" +
                        "       DATE_FORMAT(`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime\n" +
                        " from review_question_answer\n" +
                        " where group_id = :groupId");
        if(StringUtils.isNotBlank(startTime)) {
            sql.append(" and create_time >= :startTime");
            paramMap.put("startTime", startTime);
        }
        sql.append(" order by class_id, user_id, create_time asc");
        return this.getObjectList(sql.toString(), paramMap, ReviewQuestionAnswerVO.class);
    }

    public static void main(String[] args) {
        ExcelWriter excelWriter = ExcelUtil.getWriter();
        System.out.println(excelWriter);
    }
}