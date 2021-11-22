package com.review.manage.order.service.impl;

import com.review.manage.order.service.ReviewOrderServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewOrderService")
@Transactional
public class ReviewOrderServiceImpl extends CommonServiceImpl implements ReviewOrderServiceI {
	
}