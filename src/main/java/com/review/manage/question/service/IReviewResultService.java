package com.review.manage.question.service;

import com.review.front.vo.ReviewResultVO;
import com.review.manage.project.vo.ProjectResultVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

public interface IReviewResultService extends CommonService {

    /**
     * 导出测评结果
     * @param groupId
     * @param projectId
     * @param startTime
     * @param endTime
     * @param operator
     * @return
     */
    Workbook exportReviewResult(String groupId, Long projectId, String startTime, String endTime, String operator);

    /**
     * 获取测评结果
     * @param groupId
     * @param projectId
     * @param startTime
     * @param endTime
     * @return
     */
    List<ReviewResultVO> getListByProjectId(String groupId, Long projectId, String startTime, String endTime);

    /**
     * 计算项目维度的测评结果
     * @param userIds
     * @param projectId
     * @return
     */
    List<ProjectResultVO> calReviewResult(List<String> userIds, Long projectId);
}
