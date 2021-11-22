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
import java.util.Date;

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
    public Integer updateStatusByPayId(String payId, Integer status, String payResultCode, String payResultMsg) {
        Integer updNum = reviewOrderService.executeSql("update review_order set status=?, pay_result_code=?, pay_result_msg=? where pay_id=? and status != ?",
                new Object[]{status, payId, payResultCode, payResultMsg, status});
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