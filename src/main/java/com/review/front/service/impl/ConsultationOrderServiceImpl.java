package com.review.front.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.review.common.Constants;
import com.review.common.WxAppletsUtils;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.service.impl.ReviewExpertServiceImpl;
import com.review.manage.order.entity.ReviewOrderEntity;
import com.review.manage.order.service.ReviewOrderServiceI;
import com.review.manage.order.service.ReviewPayLogServiceI;
import com.review.manage.order.vo.ReviewOrderVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import org.jeecgframework.core.util.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author javabage
 * @date 2022/6/9
 */
@Service
public class ConsultationOrderServiceImpl extends OrderServiceImpl implements IOrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewOrderServiceI reviewOrderService;

    @Autowired
    private ReviewPayLogServiceI reviewPayLogService;

    @Autowired
    private ReviewExpertServiceI reviewExpertService;

    @Override
    public PreOrderVO createPrePayOrder(ReviewOrderVO reviewOrder) {

        if (StrUtil.isBlank(reviewOrder.getClassId()) || StrUtil.isBlank(reviewOrder.getUserId())) {
            logger.warn("classId or userID is null");
            return null;
        }

        //判断订单是否已存在
        ReviewOrderVO reviewOrderVO = reviewOrderService.findOneOrder(reviewOrder.getClassId(), reviewOrder.getUserId());
        if (reviewOrderVO != null && StrUtil.isNotBlank(reviewOrderVO.getPayId()) && reviewOrderVO.getStatus() != Constants.OrderStatus.PAY_EXPIRED) {
            PreOrderVO preOrder = new PreOrderVO();
            preOrder.setPrePayID(reviewOrderVO.getPayId());
            preOrder.setPackageStr("prepay_id=" + preOrder.getPrePayID());
            if (Constants.OrderStatus.SUCCESS == reviewOrderVO.getStatus() || Constants.OrderStatus.PRE_SUCCESS == reviewOrderVO.getStatus()) {
                preOrder.setReturnCode("FAIL");
                preOrder.setReturnMsg("订单已支付，无需重复创建");
                return preOrder;
            } else {
                Date now = new Date();
                Date date = new Date();
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH).parse(reviewOrderVO.getCreateTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Date createTime = DateUtil.parse(reviewOrderVO.getCreateTime());
                long diffMinutes = DateUtil.between(date, now, DateUnit.MINUTE);
                if (diffMinutes >= 60) { //超时1个小时 该订单就已过期 重新创建订单
                    //更新订单状态为已过期
                    StringBuilder updSql = new StringBuilder("update review_order set status=?, operate_time=? where id=?");
                    reviewOrderService.executeSql(updSql.toString(), new Object[]{Constants.OrderStatus.PAY_EXPIRED, now, reviewOrderVO.getId()});
                } else {
                    String nonceStr = IdUtil.simpleUUID();
                    preOrder.setNonceStr(nonceStr);
                    long timstamp = System.currentTimeMillis() / 1000;
                    preOrder.setTimeStamp(timstamp + "");
                    preOrder.setPaySign(WxAppletsUtils.paySign(nonceStr, preOrder.getPrePayID(), timstamp));
                    preOrder.setReturnCode("SUCCESS");
                    return preOrder;
                }
            }
        }
        //查询专家详情
        ReviewExpertEntity reviewExpertEntity = reviewExpertService.get(ReviewExpertEntity.class, reviewOrder.getExpertId());
        if (reviewExpertEntity == null) {
            logger.warn("expertId {} is not exists", reviewOrder.getExpertId());
            return null;
        }
        //生成订单号
        long orderNo = IdUtil.getSnowflake(0, 0).nextId();
        String key = reviewOrder.getClassId() + reviewOrder.getUserId();
        synchronized (key.intern()) {
            try {
                //封装订单信息
                ReviewOrderEntity orderEntity = new ReviewOrderEntity();
                MyBeanUtils.copyBeanNotNull2Bean(reviewOrder, orderEntity);
                orderEntity.setOrderNo(orderNo);
                orderEntity.setClassName("预约专家订单");
                orderEntity.setCreateTime(new Date());
                orderEntity.setOperateTime(orderEntity.getCreateTime());
                orderEntity.setOrgAmount(reviewExpertEntity.getOrgPrice());
                orderEntity.setOrderAmount(reviewExpertEntity.getOrgPrice().subtract(reviewExpertEntity.getDicountPrice()));
                //如果金额为0
                if (orderEntity.getOrderAmount() == null || orderEntity.getOrderAmount().doubleValue() == 0) {
                    orderEntity.setStatus(Constants.OrderStatus.SUCCESS);
                    orderEntity.setPayId("000");
                    reviewOrderService.save(orderEntity);
                    PreOrderVO preOrderVO = new PreOrderVO();
                    preOrderVO.setPrePayID("000");
                    return preOrderVO;
                }
                orderEntity.setStatus(Constants.OrderStatus.PRE_PAY);
                //创建微信预支付订单
                PreOrderVO preOrder = this.generatePrePayOrder(reviewOrder.getOpenid(), reviewOrder.getIpAddr(),
                        orderEntity.getOrderNo(), orderEntity.getOrderAmount(), orderEntity.getClassName());
                //preOrder.setOrderNO(orderEntity.getOrderNo());
                if (Constants.WX_PAY_STATUS_SUCCESS.equals(preOrder.getResultCode()) && Constants.WX_PAY_STATUS_SUCCESS.equals(preOrder.getReturnCode())) {
                    orderEntity.setPayId(preOrder.getPrePayID());
                    reviewOrderService.save(orderEntity);
                    //记录支付日志
                    preOrder.getReviewPayLog().setOrderId(orderEntity.getId());
                    preOrder.getReviewPayLog().setBroswer(reviewOrder.getBroswer());
                    preOrder.getReviewPayLog().setUserId(reviewOrder.getUserId());
                    preOrder.getReviewPayLog().setOperator(reviewOrder.getOperator());
                    reviewPayLogService.save(preOrder.getReviewPayLog());
                    return preOrder;
                }
            } catch (Exception e) {
                logger.error("createPrePayOrder error, ", e);
            }
        }
        return null;
    }

    @Override
    public Integer updateStatusByPayId(Long orderNo, String payId, Integer status, String transactionId, String payResultCode,
                                       String payResultMsg, Integer totalFee) {
        return null;
    }

    @Override
    public List<ReviewOrderVO> list(ReviewOrderVO reviewOrder) {
        return null;
    }
}
