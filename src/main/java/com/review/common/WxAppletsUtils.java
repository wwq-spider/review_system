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

    public final static String appId = "wx3d03d72c4fc3614f";

    public final static String appSecret = "f95e7676a11f1fbcd2be34f8ea01fed5";

    public final static String mchID = "1635457862";

    public final static String payKey = "xinzhaitongxing18510801311paykey";

    public final static String tradeType = "JSAPI";

    public final static String notifyUrl = ResourceUtil.getConfigByName("wx_pay_notify_url");

    public final static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public final static String qrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

    public final static String openidUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&grant_type=authorization_code&&js_code=%s";

    public final static String prePayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public final static String prePayParamFormat = "<xml><appid>%s</appid>"
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


    }
}
