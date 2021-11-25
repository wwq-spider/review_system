package com.review.manage.notice.service;

import org.jeecgframework.core.common.service.CommonService;

public interface ReviewNoticeServiceI extends CommonService{

    /**
     * 发布/停止公告
     * @param id
     * @param pubType
     */
    void publish(Long id, String pubType);
}
