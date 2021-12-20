package com.review.manage.record.service.impl;

import com.review.manage.record.service.ReviewRecordServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewRecordService")
@Transactional
public class ReviewRecordServiceImpl extends CommonServiceImpl implements ReviewRecordServiceI {
	
}