package com.review.manage.order.service.impl;

import com.review.manage.order.service.ReviewPayLogServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewPayLogService")
@Transactional
public class ReviewPayLogServiceImpl extends CommonServiceImpl implements ReviewPayLogServiceI {
	
}