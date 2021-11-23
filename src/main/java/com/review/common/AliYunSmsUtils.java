package com.review.common;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyuncs.profile.DefaultProfile;


public class AliYunSmsUtils {

    public final static String accessKey = "LTAI5tGirtTdcZkqxrVGrJQX";

    public final static String accessKeySecert = "UiIouJAMsliQi72rCBmPmN4rwQM4Hv";

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dysmsapi20170525.Client client = AliYunSmsUtils.createClient(accessKey, accessKeySecert);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("18810284375")
                .setSignName("筑心康")
                .setTemplateCode("SMS_228360174")
                .setTemplateParam("{\"code\":\"1234\"}");
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse smsResponse = client.sendSms(sendSmsRequest);
        System.out.println(smsResponse);
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
