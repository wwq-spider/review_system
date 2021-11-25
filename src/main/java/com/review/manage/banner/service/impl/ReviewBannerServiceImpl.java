package com.review.manage.banner.service.impl;

import cn.hutool.core.date.DateUtil;
import com.review.manage.banner.service.ReviewBannerServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reviewBannerService")
@Transactional
public class ReviewBannerServiceImpl extends CommonServiceImpl implements ReviewBannerServiceI {
    @Override
    public void publish(Long id, String pubType) {
        String sql = "update review_banner set status=?,operate_time=? where id=?";
        this.executeSql(sql, new Object[]{Integer.parseInt(pubType), DateUtil.now(), id});
    }
}