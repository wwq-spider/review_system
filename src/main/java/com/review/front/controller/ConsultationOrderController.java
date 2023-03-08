package com.review.front.controller;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.vo.ReviewOrderVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;


/**
 * 问诊订单
 * @date 2022/6/9
 */
@RequestMapping("reviewFront/consultationOrder")
@Controller
public class ConsultationOrderController extends BaseController {

    private static final String REFUND_SUCCESS = "SUCCESS";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Qualifier("consultationOrderServiceImpl")
    @Autowired
    private IOrderService consultationOrderService;

    @Autowired
    private static WxPayService wxPayService;


    /**
     * 创建预支付订单
     * @param response
     * @param reviewOrder
     */
    @RequestMapping(value = "createPrePayConsultationOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createPrePayConsultationOrder(HttpServletResponse response, @RequestBody ReviewOrderVO reviewOrder) throws Exception{

        JSONObject json = new JSONObject();
        ReviewUserEntity reviewUser = (ReviewUserEntity) ContextHolderUtils.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
        reviewOrder.setUserId(reviewUser.getUserId());
        reviewOrder.setOperator(reviewUser.getUserName());
        reviewOrder.setGroupId(reviewUser.getGroupId());
        reviewOrder.setOpenid(reviewUser.getOpenid());
        reviewOrder.setIpAddr(IpUtil.getIpAddr(ContextHolderUtils.getRequest()));
        reviewOrder.setBroswer(BrowserUtils.checkBrowse(ContextHolderUtils.getRequest()));
        reviewOrder.setMobilePhone(reviewUser.getMobilePhone());

        //创建预支付订单
        PreOrderVO preOrderVO = consultationOrderService.createPrePayOrder(reviewOrder);
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

    /*@RequestMapping(value = "weChatRefund",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void refund(HttpServletResponse response,String orderNo, BigDecimal amount) {
        JSONObject json = new JSONObject();
        WxPayRefundRequest refundInfo = WxPayRefundRequest.newBuilder()
                .outTradeNo("1631581508777017344")
                .outRefundNo("1631581508777017344")
                .totalFee(yuanTofee(new BigDecimal(0.01)))
                .refundFee(yuanTofee(new BigDecimal(0.01)))
                .notifyUrl("http://xinzhaitongxing/reviewFront/consultationOrder/refundNotify")
                .build();
        WxPayRefundResult wxPayRefundResult;
        try {
            wxPayRefundResult = wxPayService.refund(refundInfo);
            if (REFUND_SUCCESS.equals(wxPayRefundResult.getReturnCode())
                && REFUND_SUCCESS.equals(wxPayRefundResult.getResultCode())){
                json.put("code", 200);
                json.put("msg", "退款成功");
            }else {
                json.put("code", 400);
                json.put("msg", "退款失败");
            }
        }catch (WxPayException e) {
            logger.error("微信退款接口错误信息= {}",e);
            json.put("code", 400);
            json.put("msg", "退款失败");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }*/

    /**
     * 仅支持一次性退款，多次退款需要修改逻辑
     * @param xmlData 微信返回的流数据
     * @return
     */
    /*@RequestMapping(value = "refundNotify",method = {RequestMethod.GET,RequestMethod.POST})
    public String refundNotify(@RequestBody String xmlData) {

        WxPayRefundNotifyResult wxPayRefundNotifyResult;
        try {
            wxPayRefundNotifyResult = wxPayService.parseRefundNotifyResult(xmlData);
        } catch (WxPayException e) {
            logger.error("退款失败，失败信息:{}", e);
            return WxPayNotifyResponse.fail("退款失败");
        }
        //判断你返回状态信息是否正确
        if (REFUND_SUCCESS.equals(wxPayRefundNotifyResult.getReturnCode())) {
            WxPayRefundNotifyResult.ReqInfo reqInfo = wxPayRefundNotifyResult.getReqInfo();
            //判断退款状态
            if (REFUND_SUCCESS.equals(reqInfo.getRefundStatus())) {
                //内部订单号
                String outTradeNo = reqInfo.getOutTradeNo();
                *//**
                 * 一、可能会重复回调，需要做防重判断
                 * 二、处理我们系统内部业务，做修改订单状态，释放资源等！
                 *//*
                return WxPayNotifyResponse.success("退款成功！");
            }
        }
        return WxPayNotifyResponse.fail("回调有误!");
    }*/

    /*private int yuanTofee(BigDecimal bigDecimal) {
        return bigDecimal.multiply(new BigDecimal(100)).intValue();
    }*/

    /*public static void main(String[] args){
        JSONObject json = new JSONObject();
        WxPayRefundRequest refundInfo = WxPayRefundRequest.newBuilder()
                .outTradeNo("1631581508777017344")
                .outRefundNo("1631581508777017344")
                .totalFee(1)
                .refundFee(1)
                .notifyUrl("http://xinzhaitongxing/reviewFront/consultationOrder/refundNotify")
                .build();
        WxPayRefundResult wxPayRefundResult;
        try {
            wxPayRefundResult = wxPayService.refund(refundInfo);
            if (REFUND_SUCCESS.equals(wxPayRefundResult.getReturnCode())
                    && REFUND_SUCCESS.equals(wxPayRefundResult.getResultCode())){
                json.put("code", 200);
                json.put("msg", "退款成功");
            }else {
                json.put("code", 400);
                json.put("msg", "退款失败");
            }
        }catch (WxPayException e) {
            //logger.error("微信退款接口错误信息= {}",e);
            json.put("code", 400);
            json.put("msg", "退款失败");
        }
        //CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }*/
}
