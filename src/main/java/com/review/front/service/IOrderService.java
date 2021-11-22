package com.review.front.service;

import com.review.front.vo.PreOrderVO;
import com.review.manage.order.vo.ReviewOrderVO;

public interface IOrderService {

    /**
     * 创建预支付订单
     * @param reviewOrder
     * @return
     */
    PreOrderVO createPrePayOrder(ReviewOrderVO reviewOrder);
}
