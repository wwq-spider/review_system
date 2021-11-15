package com.review.manage.subject.service;

import com.review.manage.subject.vo.ReviewSubjectVO;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

public interface ReviewSubjectServiceI extends CommonService{

    /**
     * 新增/更新测评主题
     * @param reviewSubject
     */
    String saveOrUpdate(ReviewSubjectVO reviewSubject);

    /**
     * 查询测评专题及专题量表
     * @param reviewSubject
     * @return
     */
    List<ReviewSubjectVO> getReviewSubjectClass(ReviewSubjectVO reviewSubject);
}
