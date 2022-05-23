package com.review.manage.reviewClass.service.impl;

import cn.hutool.core.util.StrUtil;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.entity.ReviewReportTemplateEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.reviewClass.service.ReviewReportTemplateServiceI;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import org.apache.commons.collections.CollectionUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("reviewReportTemplateService")
@Transactional
public class ReviewReportTemplateServiceImpl extends CommonServiceImpl implements ReviewReportTemplateServiceI {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewClassService reviewClassService;

    @Override
    public ReviewClassVO getByClassId(String classId) {
        ReviewClassVO reviewClassVO = new ReviewClassVO();
        List<ReviewReportTemplateEntity> list = this.findByQueryString(String.format("from ReviewReportTemplateEntity where classId='%s' order by orderNum asc", classId));
        reviewClassVO.setReportTemplateList(list);
        reviewClassVO.setClassId(classId);
        reviewClassVO.setReportTips(reviewClassService.get(ReviewClassEntity.class, classId).getReportTips());
        return reviewClassVO;
    }

    @Override
    public void save(ReviewClassVO reviewClass) {

        if(StrUtil.isBlank(reviewClass.getClassId()) || CollectionUtils.isEmpty(reviewClass.getReportTemplateList())) {
            logger.warn("save ReportTemplate classId and ReportTemplateList can not null");
            return;
        }

        //1.先删除旧模板
        this.executeSql("delete from review_report_template where class_id=?", reviewClass.getClassId());

        //2.更新报告提示语
        this.executeSql("update review_class set report_tips=? where class_id=?", reviewClass.getReportTips(), reviewClass.getClassId());

        Date now = new Date();
        //3.新增新模板
        for (int i=0; i < reviewClass.getReportTemplateList().size(); i++) {
            ReviewReportTemplateEntity reportTemplateEntity = reviewClass.getReportTemplateList().get(i);
            reportTemplateEntity.setClassId(reviewClass.getClassId());
            reportTemplateEntity.setOrderNum(i);
            reportTemplateEntity.setOperateTime(now);
            reportTemplateEntity.setOperator(ContextHolderUtils.getLoginUserName());
        }
        this.batchSave(reviewClass.getReportTemplateList());
    }
}