package com.review.manage.question.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.review.front.entity.ReviewReportResultEntity;
import com.review.manage.question.service.ReviewQuestionAnswerServiceI;
import com.review.manage.question.vo.ReviewQuestionAnswerVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.userManage.service.ReviewUserService;
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

    @Autowired
    private ReviewUserService reviewUserService;

    private TreeMap<String, String> getBaseHeader() {
        TreeMap<String, String> headerAlias = new TreeMap<>();
        headerAlias.put("00_姓名", "姓名");
        headerAlias.put("00_真实姓名", "真实姓名");
        headerAlias.put("00_性别", "性别");
        headerAlias.put("00_年龄", "年龄");
        headerAlias.put("00_手机号", "手机号");
        headerAlias.put("00_完成时间", "完成时间");
        return headerAlias;
    }

    @Override
    public Workbook getExportWorkbook(String groupId, Long projectId, String startTime, String endTime) {

        List<ReviewQuestionAnswerVO> list = this.getListByGroupId(groupId, projectId, startTime, endTime);

        Map<String, TreeMap<String, Map<String, Object>>> classMap = new HashMap<>();

        Map<String, TreeMap<String, String>> headerClassMap = new HashMap<>();

        Map<String, String> nameMap = new HashMap<>();
        Map<String, StringBuilder> prefixCLassMap = new HashMap<>();

        //StringBuilder sortStr = new StringBuilder("0");
        //封装导出列表数据
        for (int i=0; i < list.size(); i++) {
            ReviewQuestionAnswerVO answerQuestion = list.get(i);
            String classId = answerQuestion.getClassId();

            TreeMap<String, Map<String, Object>> singleClassMap = null;

            StringBuilder sortStr = prefixCLassMap.get(classId);
            if (sortStr == null) {
                sortStr = new StringBuilder("0");
                prefixCLassMap.put(classId, sortStr);
            }

            String qNum = sortStr.append("a").toString();

            TreeMap<String, String> headerAlias = headerClassMap.get(classId);
            if (headerAlias == null) {
                headerAlias = getBaseHeader();
                headerClassMap.put(classId, headerAlias);
            }
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

        excelWriter.getSheets().clear();
        for (String classId : classMap.keySet()) {
            excelWriter.setSheet(nameMap.get(classId));
            excelWriter.setHeaderAlias(headerClassMap.get(classId));
            excelWriter.setColumnWidth(1, 40);
            for (int i = 4; i< headerClassMap.get(classId).size(); i++) {
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
        userClassMap.put("00_姓名", answerQuestion.getUserName());
        userClassMap.put("00_真实姓名", answerQuestion.getRealName());
        userClassMap.put("00_性别", "1".equals(answerQuestion.getSex()) ? "男":"女");
        userClassMap.put("00_年龄", answerQuestion.getAge());
        userClassMap.put("00_手机号", answerQuestion.getMobilePhone());
        userClassMap.put("00_完成时间", answerQuestion.getCreateTime());
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
    public List<ReviewQuestionAnswerVO> getListByGroupId(String groupId, Long projectId, String startTime, String endTime) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);

        StringBuilder sql = new StringBuilder(
                "select class_id classId,\n" +
                        "       q.user_id                   userId,\n" +
                        "       q.result_id                 resultId,\n" +
                        "       u.user_name                 userName,\n" +
                        "       u.real_name                 realName,\n" +
                        "       u.sex                       sex,\n" +
                        "       u.age                       age,\n" +
                        "       u.mobile_phone              mobilePhone,\n" +
                        "       u.group_id                  groupId,\n" +
                        "       q.question_num              questionNum,\n" +
                        "       concat(q.question_num, '.', q.content)              as content,\n" +
                        "       q.sel_code                                           selCode,\n" +
                        "       q.select_grade                                       selectGrade,\n" +
                        "       DATE_FORMAT(q.`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime\n" +
                        " from review_question_answer q, review_user u" +
                        " where q.user_id=u.user_id and q.group_id = :groupId ");
        if(projectId != null && projectId > 0) {
            sql.append(" and q.project_id=:projectId");
            paramMap.put("projectId", projectId.toString());
        }
        if(StringUtils.isNotBlank(startTime)) {
            sql.append(" and q.create_time >= :startTime");
            paramMap.put("startTime", startTime);
        }
        if(StringUtils.isNotBlank(endTime)) {
            sql.append(" and q.create_time <= :endTime");
            paramMap.put("endTime", endTime);
        }
        sql.append(" order by q.class_id, q.user_id, q.create_time asc");
        return this.getObjectList(sql.toString(), paramMap, ReviewQuestionAnswerVO.class);
    }

    public static void main(String[] args) {
        ExcelWriter excelWriter = ExcelUtil.getWriter();
        System.out.println(excelWriter);
    }
}