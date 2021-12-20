package com.review.manage.question.service.impl;
import cn.hutool.core.util.StrUtil;
import com.review.front.entity.ReviewReportResultEntity;
import com.review.front.entity.ReviewResultEntity;
import com.review.manage.question.service.IReviewResultService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewResultServiceImpl extends CommonServiceImpl implements IReviewResultService {

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

    @Override
    public Workbook exportReviewResult(Long projectId, String startTime, String endTime) {

        List<ReviewResultEntity> resultList = this.findHql("select * from ReviewResultEntity where projectId=? order by userId asc", new Object[]{projectId});

        Integer levelGradeAdd = 0;

        Integer levelGradeMul = 0;

        String curUserId = resultList.get(0).getUserId();

        for (int i=0; i < resultList.size(); i++) {
            ReviewResultEntity reviewResult = resultList.get(i);
            if (!curUserId.equals(reviewResult.getUserId()) || i == resultList.size() - 1) {
                Integer totalLevelGrade = levelGradeAdd * levelGradeMul;
                resultList.get(i-1).setLevelGrade(totalLevelGrade);
                levelGradeAdd = 0;
                levelGradeMul = 0;
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
                reviewResult.setCombineVarResult(varCombineStr);
            } else {
                levelGradeAdd += reviewResult.getLevelGrade();
            }
        }


        return null;
    }
}
