package com.review.common;
import cn.hutool.core.util.IdUtil;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.entity.ReviewOrderEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序工具类
 */
public class WxAppletsUtils {

    public static Logger logger = LoggerFactory.getLogger(WxAppletsUtils.class);

    public final static String appId = "wxe1e9802d7d62c6d4";

    public final static String appSecret = "d061ed674d5557bb9a862c7d1aea9bf8";

    public final static String mchID = "1616597518";

    public final static String payKey = "Zxkkj111111111111111111111111111";

    public final static String tradeType = "JSAPI";

    public final static String notifyUrl = ResourceUtil.getConfigByName("wx_pay_notify_url");

    public final static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public final static String qrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

    public final static String openidUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&grant_type=authorization_code&&js_code=%s";

    public final static String prePayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public final static String prePayParamFormat = "<xml version='1.0' encoding='gbk'><appid>%s</appid>"
                                                    + "<body><![CDATA[%s]]></body>"
                                                    + "<mch_id>%s</mch_id>"
                                                    + "<nonce_str>%s</nonce_str>"
                                                    + "<notify_url>%s</notify_url>"
                                                    + "<openid>%s</openid>"
                                                    + "<out_trade_no>%s</out_trade_no>"
                                                    + "<spbill_create_ip>%s</spbill_create_ip>"
                                                    + "<total_fee>%s</total_fee>"
                                                    + "<trade_type>%s</trade_type>"
                                                    + "<sign>%s</sign>"
                                                    + "</xml>";

    /**
     * 获取access_token
     * @return
     */
    public static String geneAccessToken() {
        String tokenStr = HttpUtils.postString(String.format(accessTokenUrl, appId, appSecret), "");
        JSONObject tokenObj = JSONObject.fromObject(tokenStr);
        if (tokenObj.containsKey("access_token")) {
            return tokenObj.getString("access_token");
        }
        return null;
    }

    public static String geneAppletsQrCode(String pagePath, String params) {
        String accessToken = geneAccessToken();
        JSONObject paramObj = new JSONObject();
        paramObj.put("scene", params);
        paramObj.put("page", pagePath);
        paramObj.put("width", 200);
        paramObj.put("is_hyaline", true);
        paramObj.put("auto_color", true);

        String filePath = "qrcode/%s/" + params.split("=")[1] + "_" + System.currentTimeMillis() + ".jpg";
        return HttpUtils.postFile(String.format(qrCodeUrl, accessToken), paramObj.toString(), filePath);
    }

    /**
     * 生成预支付订单
     * @param openid
     * @param ip
     * @param orderEntity
     * @return
     */
    public static PreOrderVO generatePrePayOrder(String openid, String ip, ReviewOrderEntity orderEntity){
        PreOrderVO preOrder = new PreOrderVO();
        try{
            //生成的随机字符串
            String nonce_str = IdUtil.simpleUUID();
            //商品名称
            String body = new String(orderEntity.getClassName().getBytes("ISO-8859-1"),"UTF-8");
            //获取本机的ip地址
            long money = orderEntity.getOrderAmount().multiply(BigDecimal.valueOf(100)).longValue();//支付金额，单位：分，这边需要转成字符串类型，否则后面的签名会失败
            String orderNo = orderEntity.getOrderNo().toString();

            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", appId);
            packageParams.put("mch_id", mchID);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", orderNo);//商户订单号
            packageParams.put("total_fee", money+"");
            packageParams.put("spbill_create_ip", ip);
            packageParams.put("notify_url", notifyUrl);
            packageParams.put("trade_type", tradeType);
            packageParams.put("openid", openid);

            // 除去数组中的空值和签名参数
            packageParams = PayUtils.paraFilter(packageParams);
            String prestr = PayUtils.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtils.sign(prestr, payKey, "utf-8").toUpperCase();
            logger.info("=======================第一次签名：" + mysign + "=====================");


            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String paramXmlStr = String.format(prePayParamFormat,appId, body, mchID, nonce_str, notifyUrl, openid,
                    orderNo, ip, money, tradeType, mysign);

            logger.info("调试模式_统一下单接口 请求XML数据：{}", paramXmlStr);

            //调用统一下单接口，并接受返回的结果
            String result = HttpUtils.postString(prePayUrl, paramXmlStr);

            logger.info("调试模式_统一下单接口 返回XML数据：{}", result);

            // 将解析结果存储在HashMap中
            Map map = PayUtils.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码

            //返回给移动端需要的参数
            if(return_code == "SUCCESS" || return_code.equals(return_code)){
                // 业务结果
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                preOrder.setPrePayID(prepay_id);
                preOrder.setNonceStr(nonce_str);
                preOrder.setPackageStr("prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                preOrder.setTimeStamp(timeStamp.toString());

                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = paySign(nonce_str, prepay_id, timeStamp);
                logger.info("=======================第二次签名：" + paySign + "=====================");
                preOrder.setPaySign(paySign);
            }
        }catch(Exception e){
           throw new RuntimeException("generatePrePayOrder error, ", e);
        }
        return preOrder;
    }

    /**
     * 生成支付签名
     * @param nonce_str
     * @param prepay_id
     * @param timeStamp
     * @return
     */
    public static String paySign(String nonce_str, String prepay_id, long timeStamp) {
        String stringSignTemp = String.format("appId=%s&nonceStr=%s&package=prepay_id=%s&signType=MD5&timeStamp=%s",
                appId, nonce_str, prepay_id, timeStamp);
        //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
        return PayUtils.sign(stringSignTemp, payKey, "utf-8").toUpperCase();
    }

    /**
     * 获取openid
     * @param code
     * @return
     */
    public static String getOpenid(String code) {
        String responseStr = HttpUtils.postString(String.format(openidUrl, appId, appSecret, code), "");
        if (StringUtils.isNotBlank(responseStr)) {
            JSONObject resJson = JSONObject.fromObject(responseStr);
            if (resJson.containsKey("openid")) {
                return resJson.getString("openid");
            }
        }
        return null;
    }

    public static void main(String[] args) {
        /*String accessToken = geneAccessToken();
        String rootPath = "/Library/prek/project/review_system/target/review_system";
        String path = geneAppletsQrCode("pages/index/index", "projectId=5");
        System.out.println(accessToken);*/

        ReviewOrderEntity orderEntity = new ReviewOrderEntity();
        orderEntity.setOrderNo(IdUtil.getSnowflake(0,0 ).nextId());
        orderEntity.setClassName("测试");
        orderEntity.setOrderAmount(BigDecimal.valueOf(0.01));

        PreOrderVO preOrderVO = generatePrePayOrder("oE_EL5jr7oiD2sbr90bvxXd5e2zo", "120.245.88.231", orderEntity);
        logger.info("preOrderVO:{}", JSONObject.fromObject(preOrderVO));
    }
}
