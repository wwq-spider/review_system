package com.review.manage.order.service.impl;
import com.review.common.Constants;
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
        Map<String, Object> resMap = this.findOneForJdbc("select id, pay_id payId, status,DATE_FORMAT(create_time, '%Y-%m-%e %H:%i:%S') as createTime " +
                        "from review_order where class_id=? and user_id=? and status in(?,?,?,?,?)",
                new Object[]{classId, userId, Constants.OrderStatus.CREATE, Constants.OrderStatus.PRE_PAY, Constants.OrderStatus.PRE_SUCCESS,
                        Constants.OrderStatus.SUCCESS, Constants.OrderStatus.PAY_FAIL});
        if (resMap != null && !resMap.isEmpty()) {
            ReviewOrderVO orderVO = new ReviewOrderVO();
            orderVO.setId((Long)resMap.get("id"));
            orderVO.setPayId(resMap.get("payId").toString());
            orderVO.setStatus((Integer) resMap.get("status"));
            orderVO.setCreateTime((String) resMap.get("createTime"));
            return orderVO;
        }
        return null;
    }

    @Override
    public boolean userBuy(String classId, String userId) {
        ReviewOrderVO reviewOrder = this.findOneOrder(classId, userId);
        if (reviewOrder != null && (reviewOrder.getStatus() == Constants.OrderStatus.PRE_SUCCESS || reviewOrder.getStatus() == Constants.OrderStatus.SUCCESS)) {
            return true;
        }
        return false;
    }
}