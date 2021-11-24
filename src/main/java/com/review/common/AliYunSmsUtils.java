package com.review.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.dysmsapi20170525.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AliYunSmsUtils {

    public final static String accessKey = "xxx";

    public final static String accessKeySecert = "xxx";

    private static volatile Client smsClient;

    private static Logger logger = LoggerFactory.getLogger(AliYunSmsUtils.class);

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static Client getClient(String accessKeyId, String accessKeySecret) throws Exception {
        if (smsClient == null) {
            synchronized (Client.class) {
                if(smsClient == null) {
                    Config config = new Config()
                            // 您的AccessKey ID
                            .setAccessKeyId(accessKeyId)
                            // 您的AccessKey Secret
                            .setAccessKeySecret(accessKeySecret);
                    // 访问的域名
                    config.endpoint = "dysmsapi.aliyuncs.com";
                    smsClient = new Client(config);
                }
            }
        }
        return smsClient;
    }

    /**
     * 发送短信验证码
     * @param code
     * @param mobilePhone
     * @return
     * @throws Exception
     */
    public static SendSmsResponseBody sendMsg(String code, String mobilePhone) throws Exception {
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(mobilePhone)
                .setSignName("筑心康")
                .setTemplateCode("SMS_228360174")
                .setTemplateParam(String.format("{\"code\":\"%s\"}", code));
        SendSmsResponse smsResponse = getClient(accessKey, accessKeySecert).sendSms(sendSmsRequest);
        if (smsResponse != null) {
            logger.info("mobilePhone:{}, sendMsgResp:{}", mobilePhone, JSONObject.toJSONString(smsResponse));
            return smsResponse.getBody();
        }
        return null;
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dysmsapi20170525.Client client = AliYunSmsUtils.getClient(accessKey, accessKeySecert);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("xxx")
                .setSignName("xxx")
                .setTemplateCode("xxx")
                .setTemplateParam("{\"code\":\"1234\"}");
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse smsResponse = client.sendSms(sendSmsRequest);
        System.out.println(JSONObject.toJSONString(smsResponse));
    }
}
