package com.review.manage.question.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.CommonService;

public interface IReviewResultService extends CommonService {

    /**
     * 导出测评记录
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    Workbook exportReviewResult(Long projectId, String startTime, String endTime);
}
