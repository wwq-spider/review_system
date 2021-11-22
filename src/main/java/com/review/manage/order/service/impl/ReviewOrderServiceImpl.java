package com.review.manage.order.service.impl;

import com.review.front.vo.PreOrderVO;
import com.review.manage.order.service.ReviewOrderServiceI;
import com.review.manage.order.vo.ReviewOrderVO;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("reviewOrderService")
@Transactional
public class ReviewOrderServiceImpl extends CommonServiceImpl implements ReviewOrderServiceI {
    @Override
    public ReviewOrderVO findOneOrder(String classId, String userId) {
        Map<String, Object> resMap = this.findOneForJdbc("select pay_id payId from review_order where class_id=? and user_id=?",
                new Object[]{classId, userId});
        if (resMap != null && !resMap.isEmpty()) {
            ReviewOrderVO orderVO = new ReviewOrderVO();
            orderVO.setPayId(resMap.get("payId").toString());
            return orderVO;
        }
        return null;
    }
}