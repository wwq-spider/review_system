package com.review.manage.reviewClass.service;

import com.review.manage.reviewClass.vo.ReviewClassVO;
import org.jeecgframework.core.common.service.CommonService;

public interface ReviewReportTemplateServiceI extends CommonService{

    /**
     * 根据量表ID查询报告模板
     * @param classId
     * @return
     */
    ReviewClassVO getByClassId(String classId);

    /**
     * 保存报告模本列表
     * @param reviewClass
     */
    void save(ReviewClassVO reviewClass);
}
