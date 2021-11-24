package com.review.front.service.impl;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.review.common.Constants;
import com.review.common.HttpUtils;
import com.review.common.PayUtils;
import com.review.common.WxAppletsUtils;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.entity.ReviewOrderEntity;
import com.review.manage.order.entity.ReviewPayLogEntity;
import com.review.manage.order.service.ReviewOrderServiceI;
import com.review.manage.order.service.ReviewPayLogServiceI;
import com.review.manage.order.vo.ReviewOrderVO;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import com.review.manage.reviewClass.service.ReviewClassService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewOrderServiceI reviewOrderService;

    @Autowired
    private ReviewClassService reviewClassService;

    @Autowired
    private ReviewPayLogServiceI reviewPayLogService;

    @Override
    @Transactional
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
            if (Constants.OrderStatus.SUCCESS == reviewOrderVO.getStatus() || Constants.OrderStatus.PRE_SUCCESS == reviewOrderVO.getStatus()) {
                preOrder.setReturnCode("FAIL");
                preOrder.setReturnMsg("订单已支付，无需重复创建");
            } else {
                String nonceStr = IdUtil.simpleUUID();
                preOrder.setNonceStr(nonceStr);
                long timstamp = System.currentTimeMillis() / 1000;
                preOrder.setTimeStamp(timstamp + "");
                preOrder.setPaySign(WxAppletsUtils.paySign(nonceStr, preOrder.getPrePayID(), timstamp));
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
                orderEntity.setClassName(reviewClass.getTitle());
                orderEntity.setCreateTime(new Date());
                orderEntity.setOperateTime(orderEntity.getCreateTime());
                orderEntity.setOrgAmount(reviewClass.getOrgPrice());
                orderEntity.setOrderAmount(reviewClass.getOrgPrice().subtract(reviewClass.getDicountPrice()));
                orderEntity.setStatus(Constants.OrderStatus.PRE_PAY);
                //创建微信预支付订单
                PreOrderVO preOrder = this.generatePrePayOrder(reviewOrder.getOpenid(), reviewOrder.getIpAddr(),
                        orderEntity.getOrderNo(), orderEntity.getOrderAmount(), orderEntity.getClassName());

                if (Constants.WX_PAY_STATUS_SUCCESS.equals(preOrder.getResultCode()) && Constants.WX_PAY_STATUS_SUCCESS.equals(preOrder.getResultCode())) {
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
    public PreOrderVO generatePrePayOrder(String openid, String ip, Long orderNo, BigDecimal orderAmount, String body) {
        PreOrderVO preOrder = new PreOrderVO();
        try{
            //生成的随机字符串
            String nonce_str = IdUtil.simpleUUID();
            //商品名称
            //获取本机的ip地址
            long money = orderAmount.multiply(BigDecimal.valueOf(100)).longValue();//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", WxAppletsUtils.appId);
            packageParams.put("mch_id", WxAppletsUtils.mchID);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo+"");//商户订单号
            packageParams.put("total_fee", money+"");
            packageParams.put("spbill_create_ip", ip);
            packageParams.put("notify_url", WxAppletsUtils.notifyUrl);
            packageParams.put("trade_type", WxAppletsUtils.tradeType);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtils.paraFilter(packageParams);
            String prestr = PayUtils.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtils.sign(prestr, WxAppletsUtils.payKey, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");


            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String paramXmlStr = String.format(WxAppletsUtils.prePayParamFormat, WxAppletsUtils.appId, body, WxAppletsUtils.mchID, nonce_str, WxAppletsUtils.notifyUrl, openid,
                    orderNo, ip, money, WxAppletsUtils.tradeType, mysign);

            logger.info("调试模式_统一下单接口 请求XML数据：{}", paramXmlStr);

            //调用统一下单接口，并接受返回的结果
            String result = HttpUtils.postString(WxAppletsUtils.prePayUrl, paramXmlStr);

            logger.info("调试模式_统一下单接口 返回XML数据：{}", result);

            // 将解析结果存储在HashMap中
            Map map = PayUtils.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码
            String result_code = (String) map.get("result_code");//返回状态码
            String result_msg = (String) map.get("result_msg");//返回状态码

            preOrder.setReturnCode(return_code);
            preOrder.setReturnMsg(result_msg);
            preOrder.setResultCode(result_code);

            //返回给移动端需要的参数
            if(Constants.WX_PAY_STATUS_SUCCESS.equals(return_code) && Constants.WX_PAY_STATUS_SUCCESS.equals(result_code)){
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                preOrder.setPrePayID(prepay_id);
                preOrder.setNonceStr(nonce_str);
                preOrder.setPackageStr("prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                preOrder.setTimeStamp(timeStamp.toString());
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = WxAppletsUtils.paySign(nonce_str, prepay_id, timeStamp);
                logger.info("=======================第二次签名：" + paySign + "=====================");
                preOrder.setPaySign(paySign);

                //这里记录支付日志
                ReviewPayLogEntity reviewPayLog = new ReviewPayLogEntity();
                reviewPayLog.setIpAddr(ip);
                reviewPayLog.setPrePayResp(JSONObject.toJSONString(map));
                reviewPayLog.setReqParam(JSONObject.toJSONString(packageParams));
                reviewPayLog.setOperateTime(new Date());
                reviewPayLog.setOrderNo(orderNo);
                reviewPayLog.setOperateType(Constants.OrderStatus.PRE_PAY);
                preOrder.setReviewPayLog(reviewPayLog);
            }
        }catch(Exception e){
            throw new RuntimeException("generatePrePayOrder error, ", e);
        }
        return preOrder;
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

        StringBuilder updSql = new StringBuilder("update review_order set status=?, operate_time=? ");
        List<Object> params = new ArrayList<>();
        String now = DateUtil.now();
        params.add(status);
        params.add(now);
        if (Constants.OrderStatus.SUCCESS == status || Constants.OrderStatus.PRE_SUCCESS == status) {
            updSql.append(", pay_time=?");
            params.add(now);
        }
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

    @Override
    public List<ReviewOrderVO> list(ReviewOrderVO reviewOrder) {
        StringBuilder sql = new StringBuilder("select o.id, " +
                "       o.order_no                                        orderNo, " +
                "       o.class_name                                      className, " +
                "       o.class_id                                        classId, " +
                "       DATE_FORMAT(o.operate_time, '%Y-%m-%e %H:%i:%S') as operateTime, " +
                "       c.banner_img                                      bannerImg " +
                " from review_order o " +
                "         inner join review_class c on o.class_id = c.class_id " +
                " where user_id = :userId ");
        sql.append("and o.status in (")
           .append(Constants.OrderStatus.PRE_SUCCESS).append(",")
           .append(Constants.OrderStatus.SUCCESS).append(")")
           .append(" order by o.operate_time desc");
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("userId", reviewOrder.getUserId());
        return reviewOrderService.getObjectList(sql.toString(), paramMap, ReviewOrderVO.class);
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