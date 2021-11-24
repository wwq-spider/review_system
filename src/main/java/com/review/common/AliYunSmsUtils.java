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

    public final static String accessKey = "LTAI5tGirtTdcZkqxrVGrJQX";

    public final static String accessKeySecert = "UiIouJAMsliQi72rCBmPmN4rwQM4Hv";

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
                .setPhoneNumbers("18810284375")
                .setSignName("筑心康")
                .setTemplateCode("SMS_228360174")
                .setTemplateParam("{\"code\":\"1234\"}");
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse smsResponse = client.sendSms(sendSmsRequest);
        System.out.println(JSONObject.toJSONString(smsResponse));
    }

    /*public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing", "LTAI5tBFDDUA1QD64hngDjLW", "ly4MQxWR7u2MOUmKUAKPqbkfjT0QiN");//自己账号的AccessKey信息
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
        request.setSysVersion("2017-05-25");//API的版本号
        request.setSysAction("SendSms");//API的名称
        request.putQueryParameter("PhoneNumbers", "18810284375");//接收短信的手机号码
        request.putQueryParameter("SignName", "筑心康");//短信签名名称
        request.putQueryParameter("TemplateCode", "SMS_228360174");//短信模板ID
        request.putQueryParameter("TemplateParam", "{\"code\":\"1234\"}");//短信模板变量对应的实际值
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }*/
}
