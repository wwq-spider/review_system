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

    /**
     * 更新订单状态
     * @param payId
     * @param status
     * @param transactionId
     * @param payResultCode
     * @param payResultMsg
     * @param totalFee
     * @return
     */
    Integer updateStatusByPayId(String payId, Integer status, String transactionId, String payResultCode,
                                String payResultMsg, Integer totalFee);
}
