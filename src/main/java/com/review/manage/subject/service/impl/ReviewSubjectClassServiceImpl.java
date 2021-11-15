package com.review.manage.subject.service.impl;

import com.review.manage.subject.entity.ReviewSubjectClassEntity;
import com.review.manage.subject.entity.ReviewSubjectEntity;
import com.review.manage.subject.service.ReviewSubjectClassServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("reviewSubjectClassService")
@Transactional
public class ReviewSubjectClassServiceImpl extends CommonServiceImpl implements ReviewSubjectClassServiceI {

    @Override
    public void saveOrUpdateSubjectClass(Long subjectId, List<ReviewSubjectClassEntity> subjectClassList) {
        if (subjectId != null && subjectId > 0) {
            this.delSubjectClassBySubId(subjectId);
        }
        if (CollectionUtils.isEmpty(subjectClassList)) {
            return;
        }
        this.batchSave(subjectClassList);
    }

    @Override
    public void delSubjectClassBySubId(Long subjectId) {
        this.executeSql("delete from review_subject_class where subject_id=?", new Object[]{subjectId});
    }
}