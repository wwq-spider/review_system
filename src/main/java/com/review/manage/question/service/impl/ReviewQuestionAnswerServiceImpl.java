package com.review.manage.question.service.impl;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.beust.jcommander.internal.Lists;
import com.review.manage.question.entity.ReviewQuestionAnswerEntity;
import com.review.manage.question.service.ReviewQuestionAnswerServiceI;
import com.review.manage.question.vo.ReviewQuestionAnswerVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.entity.ExcelTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("reviewQuestionAnswerService")
@Transactional
public class ReviewQuestionAnswerServiceImpl extends CommonServiceImpl implements ReviewQuestionAnswerServiceI {

    @Autowired
    private ReviewClassService reviewClassService;

    @Override
    public Workbook getExportWorkbook(String groupId) {


        List<ReviewQuestionAnswerVO> list = this.getListByGroupId(groupId);


        Map<String, Map<String, Map<String, Object>>> classMap = new HashMap<>();

        TreeMap<String, String> headerAlias = new TreeMap<>();
        headerAlias.put("0_姓名", "姓名");
        headerAlias.put("0_性别", "性别");
        headerAlias.put("0_年龄", "年龄");
        headerAlias.put("0_手机号", "手机号");

        Map<String, String> nameMap = new HashMap<>();

        StringBuilder sortStr = new StringBuilder("0");
        //封装导出列表数据
        for (int i=0; i < list.size(); i++) {
            ReviewQuestionAnswerVO answerQuestion = list.get(i);
            String classId = answerQuestion.getClassId();

            Map<String, Map<String, Object>> singleClassMap = null;
            String qNum = sortStr.append("a").toString();
            headerAlias.put(qNum, answerQuestion.getContent());

            Map<String, Object> userClassMap = null;
            if (classMap.containsKey(classId)) { //是否包含量表id
                singleClassMap = classMap.get(classId);
                if(singleClassMap.get(answerQuestion.getUserId()) != null) {
                    userClassMap = singleClassMap.get(answerQuestion.getUserId());
                } else {
                    userClassMap = new HashMap<>();
                    userClassMap.put("0_姓名", answerQuestion.getUserName());
                    userClassMap.put("0_性别", "1".equals(answerQuestion.getSex()) ? "男":"女");
                    userClassMap.put("0_年龄", answerQuestion.getAge());
                    userClassMap.put("0_手机号", answerQuestion.getMobilePhone());
                    singleClassMap.put(answerQuestion.getUserId(), userClassMap);
                }
            } else {
                singleClassMap = new HashMap<>();
                classMap.put(classId, singleClassMap);
                ReviewClassEntity reviewClass = reviewClassService.get(ReviewClassEntity.class, classId);
                nameMap.put(classId, reviewClass.getTitle());

                userClassMap = new HashMap<>();
                userClassMap.put("0_姓名", answerQuestion.getUserName());
                userClassMap.put("0_性别", "1".equals(answerQuestion.getSex()) ? "男":"女");
                userClassMap.put("0_年龄", answerQuestion.getAge());
                userClassMap.put("0_手机号", answerQuestion.getMobilePhone());
                singleClassMap.put(answerQuestion.getUserId(), userClassMap);
            }
            userClassMap.put(qNum, answerQuestion.getSelectGrade());
        }

        ExcelWriter excelWriter = ExcelUtil.getWriter();
        excelWriter.setHeaderAlias(headerAlias);
        excelWriter.getSheets().clear();
        for (String classId : classMap.keySet()) {
            excelWriter.setSheet(nameMap.get(classId));
            excelWriter.setColumnWidth(3, 40);
            for (int i = 4; i<headerAlias.size(); i++) {
                excelWriter.setColumnWidth(i, 60);
            }
            excelWriter.write(classMap.get(classId).values(), true);
        }
        excelWriter.getWorkbook().removeSheetAt(0);
        return excelWriter.getWorkbook();
    }

    @Override
    public List<ReviewQuestionAnswerVO> getListByGroupId(String groupId) {

        StringBuilder sql = new StringBuilder(
                "select class_id classId,\n" +
                "       user_id                            userID,\n" +
                "       user_name                 userName,\n" +
                "       sex                       sex,\n" +
                "       age                       age,\n" +
                "       mobile_phone              mobilePhone,\n" +
                "       group_id                                           groupId,\n" +
                "       question_num                                       questionNum,\n" +
                "       concat(question_num, '.', content)              as content,\n" +
                "       sel_code                                           selCode,\n" +
                "       select_grade                                       selectGrade,\n" +
                "       DATE_FORMAT(`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime\n" +
                " from review_question_answer\n" +
                " where group_id = :groupId" +
                " order by class_id, user_id, create_time asc");

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);
        return this.getObjectList(sql.toString(), paramMap, ReviewQuestionAnswerVO.class);
    }

    public static void main(String[] args) {
        ExcelWriter excelWriter = ExcelUtil.getWriter();
        System.out.println(excelWriter);
    }
}