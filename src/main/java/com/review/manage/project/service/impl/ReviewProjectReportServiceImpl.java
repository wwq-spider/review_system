package com.review.manage.project.service.impl;

import com.review.manage.project.service.ReviewProjectReportServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewProjectReportService")
@Transactional
public class ReviewProjectReportServiceImpl extends CommonServiceImpl implements ReviewProjectReportServiceI {
	
}