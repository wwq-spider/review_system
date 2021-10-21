package com.review.manage.expert.service.impl;

import com.review.manage.expert.service.ReviewExpertReserveServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewExpertReserveService")
@Transactional
public class ReviewExpertReserveServiceImpl extends CommonServiceImpl implements ReviewExpertReserveServiceI {
	
}