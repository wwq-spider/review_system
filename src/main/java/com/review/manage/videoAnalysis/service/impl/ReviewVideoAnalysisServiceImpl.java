package com.review.manage.videoAnalysis.service.impl;

import com.review.manage.videoAnalysis.service.ReviewVideoAnalysisServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewVideoAnalysisService")
@Transactional
public class ReviewVideoAnalysisServiceImpl extends CommonServiceImpl implements ReviewVideoAnalysisServiceI {
	
}