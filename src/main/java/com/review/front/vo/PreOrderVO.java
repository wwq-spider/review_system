package com.review.front.vo;

import com.review.manage.order.entity.ReviewPayLogEntity;

import java.io.Serializable;

/**
 * 预支付订单信息
 */
public class PreOrderVO implements Serializable {

    private String prePayID;

    private Long orderID;

    private Long orderNO;

    private String paySign;

    private String returnCode;

    private String resultCode;

    private String returnMsg;

    private String mchId;

    private String nonceStr;

    private String tradeType;

    private String appId;

    private String timeStamp;

    private String signType;

    private String packageStr;

    private ReviewPayLogEntity reviewPayLog;

    public String getPrePayID() {
        return prePayID;
    }

    public void setPrePayID(String prePayID) {
        this.prePayID = prePayID;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(Long orderNO) {
        this.orderNO = orderNO;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPackageStr() {
        return packageStr;
    }

    public PreOrderVO setPackageStr(String packageStr) {
        this.packageStr = packageStr;
        return this;
    }

    public ReviewPayLogEntity getReviewPayLog() {
        return reviewPayLog;
    }

    public void setReviewPayLog(ReviewPayLogEntity reviewPayLog) {
        this.reviewPayLog = reviewPayLog;
    }
}
