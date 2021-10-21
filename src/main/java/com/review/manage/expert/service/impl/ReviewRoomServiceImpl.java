package com.review.manage.expert.service.impl;

import com.review.manage.expert.service.ReviewRoomServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewRoomService")
@Transactional
public class ReviewRoomServiceImpl extends CommonServiceImpl implements ReviewRoomServiceI {
	
}