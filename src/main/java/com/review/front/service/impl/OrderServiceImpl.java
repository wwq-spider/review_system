package com.review.front.service.impl;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.review.common.Constants;
import com.review.common.WxAppletsUtils;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.entity.ReviewOrderEntity;
import com.review.manage.order.service.ReviewOrderServiceI;
import com.review.manage.order.vo.ReviewOrderVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewOrderServiceI reviewOrderService;

    @Autowired
    private ReviewClassService reviewClassService;

    @Override
    public PreOrderVO createPrePayOrder(ReviewOrderVO reviewOrder) {

        if (StrUtil.isBlank(reviewOrder.getClassId()) || StrUtil.isBlank(reviewOrder.getUserId())) {
            logger.warn("classId or userID is null");
            return null;
        }

        //判断订单是否已存在
        ReviewOrderVO reviewOrderVO = reviewOrderService.findOneOrder(reviewOrder.getClassId(), reviewOrder.getUserId());
        if (reviewOrderVO != null && StrUtil.isNotBlank(reviewOrderVO.getPayId())) {
            PreOrderVO preOrder = new PreOrderVO();
            preOrder.setPrePayID(reviewOrderVO.getPayId());
            preOrder.setPackageStr("prepay_id=" + preOrder.getPrePayID());
            if (Constants.OrderStatus.SUCCESS == reviewOrderVO.getStatus()) {
                preOrder.setReturnCode("FAIL");
                preOrder.setReturnMsg("订单已支付，无需重复创建");
            } else {
                String nonceStr = IdUtil.simpleUUID();
                preOrder.setNonceStr(nonceStr);
                preOrder.setPaySign(WxAppletsUtils.paySign(nonceStr, preOrder.getPrePayID(), System.currentTimeMillis() / 1000));
                preOrder.setReturnCode("SUCCESS");
            }
            return preOrder;
        }

        //获取测评量表
        ReviewClassEntity reviewClass = reviewClassService.get(ReviewClassEntity.class, reviewOrder.getClassId());
        if (reviewClass == null) {
            logger.warn("classId {} is not exists", reviewClass.getClassId());
            return null;
        }
        //生成订单号
        long orderNo = IdUtil.getSnowflake(0, 0).nextId();
        String key = reviewOrder.getClassId() + reviewOrder.getUserId();
        synchronized (key.intern()) {
            try{
                //封装订单信息
                ReviewOrderEntity orderEntity = new ReviewOrderEntity();
                MyBeanUtils.copyBeanNotNull2Bean(reviewOrder, orderEntity);
                orderEntity.setOrderNo(orderNo);
                orderEntity.setCreateTime(new Date());
                orderEntity.setOperateTime(orderEntity.getCreateTime());
                orderEntity.setOrgAmount(reviewClass.getOrgPrice());
                orderEntity.setOrderAmount(reviewClass.getOrgPrice().subtract(reviewClass.getDicountPrice()));
                orderEntity.setStatus(Constants.OrderStatus.PRE_PAY);

                //创建微信预支付订单
                PreOrderVO preOrder = WxAppletsUtils.generatePrePayOrder(reviewOrder.getOpenid(), reviewOrder.getIpAddr(),
                        orderEntity);
                orderEntity.setPayId(preOrder.getPrePayID());
                reviewOrderService.save(orderEntity);

                preOrder.setReturnCode("SUCCESS");
                return preOrder;
            } catch (Exception e) {
                logger.error("createPrePayOrder error, ", e);
            }
        }
        return null;
    }

    @Override
    public Integer updateStatusByPayId(String payId, Integer status, String transactionId, String payResultCode,
                                       String payResultMsg, Integer totalFee) {
        //check
        Map<String, Object> map = reviewOrderService.findOneForJdbc("select order_amount, status, order_no from review_order where pay_id=?",
                new Object[]{payId});
        if (map == null || map.isEmpty()) {
            logger.warn("prepay:{} is not exist", payId);
            return -1;
        }

        //处理成功的订单 不再重复处理
        Integer orderStatus = (Integer)map.get("status");
        if (orderStatus == Constants.OrderStatus.SUCCESS) {
            logger.warn("order_no:{},payId:{} had process success", map.get("order_no"), payId);
            return -2;
        }

        //check支付金额
        int orderAmount = BigDecimal.valueOf(Double.valueOf(map.get("order_amount").toString())).multiply(BigDecimal.valueOf(100)).intValue();
        if (Constants.OrderStatus.SUCCESS == status && (totalFee == null || totalFee != orderAmount)) {
            logger.warn("totalFee:{} not equals orderAmount:{} ", totalFee == null ? "null" : totalFee, orderAmount);
            return -3;
        }

        StringBuilder updSql = new StringBuilder("update review_order set status=?");
        List<Object> params = new ArrayList<>();
        if (StrUtil.isNotBlank(transactionId)) {
            updSql.append(", transaction_id=?");
            params.add(transactionId);
        }
        if (StrUtil.isNotBlank(payResultCode)) {
            updSql.append(", pay_result_code=?");
            params.add(payResultCode);
        }
        if (StrUtil.isNotBlank(payResultMsg)) {
            updSql.append(", pay_result_msg=?");
            params.add(payResultMsg);
        }
        updSql.append("where pay_id=? and status != ?");
        params.add(payId);
        params.add(Constants.OrderStatus.SUCCESS);

        //采用cas 自旋锁 保证成功状态不被更新
        Integer updNum = reviewOrderService.executeSql(updSql.toString(), params.toArray(new Object[params.size()]));
        logger.info("updateStatusByPayId result: {}", updNum);
        return updNum;
    }

    public static void main(String[] args) {
        //System.out.println(IdUtil.fastSimpleUUID());
        //System.out.println(IdUtil.fastUUID());
       // System.out.println(IdUtil.randomUUID());
        Snowflake snowflake = IdUtil.getSnowflake(0, 0 );
        for (int i=0; i<1000; i++) {
            System.out.println(snowflake.nextId());
        }
    }
}