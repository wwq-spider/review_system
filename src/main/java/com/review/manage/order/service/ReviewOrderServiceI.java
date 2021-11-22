package com.review.manage.order.service;

import com.review.manage.order.vo.ReviewOrderVO;
import org.jeecgframework.core.common.service.CommonService;

public interface ReviewOrderServiceI extends CommonService{

    /**
     * 获取订单信息
     * @param classId
     * @param userId
     * @param status
     * @return
     */
    ReviewOrderVO findOneOrder(String classId, String userId);
}
