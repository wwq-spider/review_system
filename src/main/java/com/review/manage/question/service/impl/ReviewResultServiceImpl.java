package com.review.manage.question.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.review.front.vo.ReviewResultVO;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.project.vo.ProjectResultVO;
import com.review.manage.question.service.IReviewResultService;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewResultServiceImpl extends CommonServiceImpl implements IReviewResultService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IReviewProjectService reviewProjectService;

    @Autowired
    private ReviewClassService reviewClassService;

    public Map<String,Integer> MBTI_MAP = new HashMap<String, Integer>(){
        {
            put("EFNP", 1);
            put("FIJN", 1);
            put("IJNT", 1);
            put("IPST", 1);

            put("IJST", 2);
            put("FIJS", 2);
            put("ENPT", 2);
            put("INPT", 2);
            put("EJST", 2);
            put("EJNT", 2);
            put("FIPS", 2);
            put("FINP", 2);

            put("EFJN", 3);
            put("EFJS", 3);
            put("EPST", 3);
            put("EFPS", 3);
        }
    };

    private TreeMap<String, String> getBaseHeader(String projectName) {
        TreeMap<String, String> headerAlias = new TreeMap<>();
        headerAlias.put("00_姓名", "姓名");
        headerAlias.put("01_身份证号", "身份证号");
        headerAlias.put("02_" + projectName, projectName);
        headerAlias.put("03_登记人", "登记人");
        headerAlias.put("04_登记日期", "登记日期");


        return headerAlias;
    }

    @Override
    public Workbook exportReviewResult(String groupId, Long projectId, String startTime, String endTime, String operator) {

        ReviewProjectEntity reviewProject = reviewProjectService.get(ReviewProjectEntity.class, projectId);

        if (reviewProject == null) {
            logger.warn("exportReviewResult failed, projectId:%d is not exist ", projectId);
            return null;
        }

        //按项目查询结果列表
        List<ReviewResultVO> resultList = this.getListByProjectId(groupId, projectId, startTime, endTime);
        //生成项目维度测评结果
        Map<String, ProjectResultVO> userMap = geneProjectReview(resultList, false);

        //数据写出
        ExcelWriter excelWriter = ExcelUtil.getWriter();
        excelWriter.setSheet(reviewProject.getProjectName());
        String pAliasName = reviewProject.getProjectName() + "结论分数";
        Map<String, String> headerAliasMap = getBaseHeader(pAliasName);
        excelWriter.setHeaderAlias(headerAliasMap);

        Collection<ProjectResultVO> colls = userMap.values();
        for (int i = 4; i< colls.size(); i++) {
            excelWriter.setColumnWidth(i, 60);
        }
        for (ProjectResultVO projectResult : colls) {
            excelWriter.writeRow(geneRow(projectResult, pAliasName, operator), true);
        }
        excelWriter.getWorkbook().removeSheetAt(0);
        return excelWriter.getWorkbook();
    }

    private Map<String, ProjectResultVO> geneProjectReview(List<ReviewResultVO> resultList, boolean containClass) {

        String configStr = FileUtil.readUtf8String(ResourceUtil.getClassPath() + "report_config.json");
        JSONObject configJson = JSONObject.parseObject(configStr);

        Integer levelGradeAdd = 0;

        Integer levelGradeMul = 0;

        String curUserId = resultList.get(0).getUserId();

        Map<String, ProjectResultVO> userMap = new HashMap<>();

        Map<String, Integer> dupMap = new HashMap<>();

        Map<String, ReviewClassEntity> reviewClassMap = new HashMap<>();

        List<ReviewResultVO> childResult = new ArrayList<>();

        for (int i=0; i < resultList.size(); i++) {
            ReviewResultVO reviewResult = resultList.get(i);
            String dupKey = String.format("%s_%s", reviewResult.getUserId(), reviewResult.getClassId());
            if (dupMap.get(dupKey) != null) {
                continue;
            } else {
                dupMap.put(dupKey, 1);
            }
            if (reviewResult.getLevelGrade() != null && reviewResult.getLevelGrade() > 0) {
                if (!curUserId.equals(reviewResult.getUserId()) || i == resultList.size() - 1) {
                    ReviewResultVO preResult = resultList.get(i-1);
                    Integer totalLevelGrade = levelGradeAdd * levelGradeMul;
                    ProjectResultVO projectResult = new ProjectResultVO();
                    try {
                        MyBeanUtils.copyBeanNotNull2Bean(preResult, projectResult);
                        projectResult.setLevelGrade(totalLevelGrade);
                    } catch (Exception e) {
                        throw new RuntimeException("geneProjectReview error, ", e);
                    }
                    if (childResult.size() > 0) {
                        JSONArray jsonArray = configJson.getJSONArray("AdaptVar");
                        for (int a=0; a<jsonArray.size(); a++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(a);
                            Integer scoreSmall = jsonObject.getInteger("scoreSmall");
                            Integer scoreBig = jsonObject.getInteger("scoreBig");
                            if (projectResult.getLevelGrade() >= scoreSmall && projectResult.getLevelGrade()<= scoreBig) {
                                projectResult.setAllCompletion(jsonObject.getString("result"));
                                projectResult.setSuggestion(jsonObject.getString("explain"));
                                break;
                            }
                        }
                        projectResult.setResultList(new ArrayList<>(childResult));
                        childResult.clear();
                    }
                    levelGradeAdd = 0;
                    levelGradeMul = 0;
                    //用户维度统计数据
                    userMap.put(curUserId, projectResult);
                    curUserId = reviewResult.getUserId();
                }
                String combineRes = reviewResult.getCombineVarResult();
                if(StrUtil.isNotEmpty(combineRes)) {
                    //获得MBTI组合
                    List<String> varCombine = Arrays.asList(combineRes.split(","));
                    //根据MBTI获取等级分数
                    Collections.sort(varCombine);
                    String varCombineStr = StringUtils.join(varCombine, "");
                    if(MBTI_MAP.get(varCombineStr) != null) {
                        reviewResult.setLevelGrade(MBTI_MAP.get(varCombineStr));
                        levelGradeMul = reviewResult.getLevelGrade();
                    }
                    reviewResult.setReportResult(varCombineStr);
                    if (containClass) {
                        JSONArray jsonArray = configJson.getJSONArray("MBTI");
                        for (int a=0; a<jsonArray.size(); a++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(a);
                            if (jsonObject.getInteger("levelGrade") == reviewResult.getLevelGrade()) {
                                reviewResult.setReportResult(jsonObject.getString("result"));
                                reviewResult.setCombineVarResult(jsonObject.getString("explain"));
                                break;
                            }
                        }
                    }
                } else {
                    levelGradeAdd += reviewResult.getLevelGrade();
                }
                if (containClass) {
                    if (reviewClassMap.get(reviewResult.getClassId()) == null) {
                        reviewClassMap.put(reviewResult.getClassId(), reviewClassService.get(ReviewClassEntity.class, reviewResult.getClassId()));
                    }
                    reviewResult.setClassTitle(reviewClassMap.get(reviewResult.getClassId()).getTitle());

                    childResult.add(reviewResult);
                }
            }
        }
        return userMap;
    }


    private TreeMap<String, Object> geneRow(ProjectResultVO projectResult, String projectName, String operator) {
        TreeMap<String, Object> rowMap = new TreeMap<>();
        rowMap.put("00_姓名", projectResult.getUserName());
        rowMap.put("01_身份证号", projectResult.getIdCard());
        rowMap.put("02_" + projectName, projectResult.getLevelGrade());
        rowMap.put("03_登记人", operator);
        rowMap.put("04_登记日期", projectResult.getCreateTime());
        return rowMap;
    }

    @Override
    public List<ReviewResultVO> getListByProjectId(String groupId, Long projectId, String startTime, String endTime) {
        StringBuilder sql = new StringBuilder("select u.user_name   userName,\n" +
                "       u.real_name realName,\n" +
                "       u.id_card     idCard,\n" +
                "       r.result_id   resultId,\n" +
                "       r.class_id    classId,\n" +
                "       r.grade_total gradeTotal,\n" +
                "       r.level_grade levelGrade,\n" +
                "       r.combine_result combineResult,\n" +
                "       r.project_id projectId,\n" +
                "       DATE_FORMAT(u.`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime\n" +
                "from review_result r\n" +
                "         inner join review_user u on r.user_id = u.user_id and r.project_id=:projectId\n" +
                "where r.create_time between ':startTime' and ':endTime'\n" +
                "order by r.create_time desc");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("projectId", projectId);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);

        return this.getObjectList(sql.toString(), paramMap, ReviewResultVO.class);
    }

    @Override
    public List<ProjectResultVO> calReviewResult(List<String> userIds, Long projectId) {

        StringBuilder sql = new StringBuilder("select u.user_name   userName,\n" +
                "       u.real_name realName,\n" +
                "       u.id_card     idCard,\n" +
                "       r.result_id   resultId,\n" +
                "       r.class_id    classId,\n" +
                "       r.grade_total gradeTotal,\n" +
                "       r.level_grade levelGrade,\n" +
                "       r.combine_result combineResult,\n" +
                "       r.project_id projectId,\n" +
                "       DATE_FORMAT(u.`create_time`, '%Y-%m-%e %H:%i:%S') AS createTime\n" +
                "from review_result r\n" +
                "         inner join review_user u on r.user_id = u.user_id and u.user_id in('" +
                StringUtils.join(userIds, "','") + "')" +
              //  " and r.project_id=:projectId\n" +
                "order by r.create_time desc");
        Map<String, Object> paramMap = new HashMap<>();

        //paramMap.put("projectId", projectId);

        List<ReviewResultVO> reviewResultList = this.getObjectList(sql.toString(), paramMap, ReviewResultVO.class);

        Map<String, ProjectResultVO> userMap = geneProjectReview(reviewResultList, true);

        return new ArrayList<>(userMap.values());
    }
}
