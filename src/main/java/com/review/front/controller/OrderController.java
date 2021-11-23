package com.review.front.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import com.review.common.*;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.service.ReviewPayLogServiceI;
import com.review.manage.order.vo.ReviewOrderVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 测评订单
 */
@RequestMapping("reviewFront/order")
@Controller
public class OrderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ReviewPayLogServiceI reviewPayLogService;

    /**
     * 创建预支付订单
     * @param response
     * @param reviewOrder
     */
    @RequestMapping(value = "createPrePayOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createPrePayOrder(HttpServletResponse response, @RequestBody ReviewOrderVO reviewOrder) {

        JSONObject json = new JSONObject();

        ReviewUserEntity reviewUser = (ReviewUserEntity) ContextHolderUtils.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
        reviewOrder.setUserId(reviewUser.getUserId());
        reviewOrder.setOperator(reviewUser.getUserName());
        reviewOrder.setGroupId(reviewUser.getGroupId());
        reviewOrder.setOpenid(reviewUser.getOpenid());
        reviewOrder.setIpAddr(IpUtil.getIpAddr(ContextHolderUtils.getRequest()));
        reviewOrder.setBroswer(BrowserUtils.checkBrowse(ContextHolderUtils.getRequest()));

        //创建预支付订单
        PreOrderVO preOrderVO = orderService.createPrePayOrder(reviewOrder);
        if (preOrderVO == null) {
            json.put("code", 400);
            json.put("msg", "创建失败");
        } else {
            json.put("code", 200);
            json.put("data", preOrderVO);
            json.put("msg", "创建成功");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 更新订单状态
     * @param response
     * @param reviewOrder
     */
    @RequestMapping(value = "updateOrderStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void updateOrderStatus(HttpServletResponse response, @RequestBody ReviewOrderVO reviewOrder) {

        JSONObject json = new JSONObject();

        Integer total_fee = BigDecimal.valueOf(Double.valueOf(reviewOrder.getOrderAmount())).multiply(BigDecimal.valueOf(100)).intValue();

        int updNum = orderService.updateStatusByPayId(reviewOrder.getPayId(), Constants.OrderStatus.PRE_SUCCESS, "",
                "", "", total_fee);

        if (updNum > 0) {
            json.put("code", 200);
            json.put("msg", "更新成功");
        } else {
            json.put("code", 400);
            json.put("msg", "更新状态失败");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "wxPayNotify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(response.getOutputStream());

            String notityXml = IoUtil.read(request.getInputStream(), Charset.defaultCharset().name());
            String resXml = "";

            logger.info("接收到的报文：" + notityXml);

            Map map = PayUtils.doXMLParse(notityXml);
            String returnCode = (String) map.get("return_code");
            String err_code = (String) map.get("err_code");
            String err_code_des = (String) map.get("err_code_des");
            String out_trade_no = (String) map.get("out_trade_no");

            if(Constants.WX_PAY_STATUS_SUCCESS.equals(returnCode)){
                String resultCode = (String) map.get("result_code");
                //验证签名是否正确
                if(PayUtils.verify(PayUtils.createLinkString(map), (String)map.get("sign"), WxAppletsUtils.payKey, Charset.defaultCharset().name())){

                    /**此处添加自己的业务逻辑代码start**/
                    String prepay_id = map.get("prepay_id") == null ? "" : map.get("prepay_id").toString();
                    String transaction_id = map.get("transaction_id") == null ? "" : map.get("transaction_id").toString();

                    //支付费用
                    Integer total_fee = (Integer) map.get("total_fee");
                    Integer status = Constants.WX_PAY_STATUS_SUCCESS.equals(resultCode) ? Constants.OrderStatus.SUCCESS : Constants.OrderStatus.PAY_FAIL;

                    //更新顶单状态
                    int updNum = orderService.updateStatusByPayId(prepay_id, status, transaction_id, err_code, err_code_des, total_fee);
                    if (updNum > 0) {
                        //通知微信服务器已经支付成功
                        resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg></xml> ";
                    } else {
                        resXml = "<xml><return_code><![CDATA[FAIL]]></return_code>"
                                + "<return_msg><![CDATA[订单状态更新失败]]></return_msg></xml> ";
                    }

                    //更新支付日志
                    reviewPayLogService.executeSql("update review_pay_log set callback_resp=?, operate_time=?, operate_type=? where order_no=?",
                            new Object[]{com.alibaba.fastjson.JSONObject.toJSONString(map), DateUtil.now(), Constants.OrderStatus.SUCCESS, out_trade_no});
                    /**此处添加自己的业务逻辑代码end**/
                }
            } else{
                resXml = "<xml><return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg></xml> ";
            }
            logger.info(resXml);
            logger.info("微信支付回调数据结束");

            out.write(resXml.getBytes());
            out.flush();
        } catch (Exception e) {
            out.write("<xml><return_code><![CDATA[ERROR]]></return_code><return_msg><![CDATA[订单状态更新异常]]></return_msg></xml>".getBytes());
            out.flush();
            logger.error("wxPayNotify error, ", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
}