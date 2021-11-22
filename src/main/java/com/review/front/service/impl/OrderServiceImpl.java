package com.review.front.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.entity.ReviewOrderEntity;
import com.review.manage.order.service.ReviewOrderServiceI;
import com.review.manage.order.vo.ReviewOrderVO;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ReviewOrderServiceI reviewOrderService;

    @Override
    public PreOrderVO createPrePayOrder(ReviewOrderVO reviewOrder) {
        String key = reviewOrder.getClassId() + reviewOrder.getUserId();
        synchronized (key.intern()) {
            try{

                ReviewOrderEntity orderEntity = new ReviewOrderEntity();
                MyBeanUtils.copyBeanNotNull2Bean(reviewOrder, orderEntity);
               // orderEntity.setOrderNo();


                reviewOrderService.save(orderEntity);
            } catch (Exception e) {

            }
        }
        return null;
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