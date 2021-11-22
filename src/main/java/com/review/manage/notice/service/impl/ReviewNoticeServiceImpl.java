package com.review.manage.notice.service.impl;

import cn.hutool.core.date.DateUtil;
import com.review.manage.notice.service.ReviewNoticeServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewNoticeService")
@Transactional
public class ReviewNoticeServiceImpl extends CommonServiceImpl implements ReviewNoticeServiceI {
    @Override
    public void publish(Long id, String pubType) {
        String sql = "update review_notice set status=?,update_time=? where id=?";
        this.executeSql(sql, new Object[]{Integer.parseInt(pubType), DateUtil.now(), id});
    }
}