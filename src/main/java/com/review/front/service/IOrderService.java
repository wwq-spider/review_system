package com.review.front.service;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.vo.ReviewOrderVO;
import java.math.BigDecimal;

public interface IOrderService {

    /**
     * 创建预支付订单
     * @param reviewOrder
     * @return
     */
    PreOrderVO createPrePayOrder(ReviewOrderVO reviewOrder);

    /**
     * 生成预付单
     * @param openid
     * @param ip
     * @param orderNo
     * @param orderAmount
     * @param body
     * @return
     */
    PreOrderVO generatePrePayOrder(String openid, String ip, Long orderNo, BigDecimal orderAmount, String body);

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
