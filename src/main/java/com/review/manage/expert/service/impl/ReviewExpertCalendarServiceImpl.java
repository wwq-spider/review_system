package com.review.manage.expert.service.impl;

import com.review.manage.expert.service.ReviewExpertCalendarServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewExpertCalendarService")
@Transactional
public class ReviewExpertCalendarServiceImpl extends CommonServiceImpl implements ReviewExpertCalendarServiceI {
	
}