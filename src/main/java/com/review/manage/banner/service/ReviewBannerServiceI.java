package com.review.manage.banner.service;

import org.jeecgframework.core.common.service.CommonService;

public interface ReviewBannerServiceI extends CommonService{

    /**
     * 发布/停止公告
     * @param id
     * @param pubType
     */
    void publish(Long id, String pubType);
}
