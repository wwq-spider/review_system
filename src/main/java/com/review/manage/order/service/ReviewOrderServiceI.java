package com.review.manage.order.service;

import com.review.manage.order.vo.ReviewOrderVO;
import org.jeecgframework.core.common.service.CommonService;

public interface ReviewOrderServiceI extends CommonService{

    /**
     * 获取订单信息
     * @param classId
     * @param userId
     * @return
     */
    ReviewOrderVO findOneOrder(String classId, String userId);

    /**
     * 判断用户是否已经购买测评量表
     * @param classId
     * @param userId
     * @return
     */
    boolean userBuy(String classId, String userId);
}
