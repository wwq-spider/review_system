package com.review.manage.subject.service;

import com.review.manage.subject.entity.ReviewSubjectClassEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

public interface ReviewSubjectClassServiceI extends CommonService{

    /**
     * 保存或修改测评专题关联量表
     * @param subjectClassList
     */
    void saveOrUpdateSubjectClass(Long subjectId, List<ReviewSubjectClassEntity> subjectClassList);

    /**
     * 删除测评专题
     * @param subjectId
     */
    void delSubjectClassBySubId(Long subjectId);
}
