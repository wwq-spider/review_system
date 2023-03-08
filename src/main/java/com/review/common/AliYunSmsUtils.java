package com.review.common;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.dysmsapi20170525.Client;
import com.review.front.vo.ConsultationVO;
import org.jeecgframework.core.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AliYunSmsUtils {

    public final static String accessKey = OssUtils.bundle.getString("ACCESS_ID");

    public final static String accessKeySecert = OssUtils.bundle.getString("ACCESS_KEY");

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
                .setSignName("心宅同行")
                .setTemplateCode("SMS_259430450")
                .setTemplateParam(String.format("{\"code\":\"%s\"}", code));
        SendSmsResponse smsResponse = getClient(accessKey, accessKeySecert).sendSms(sendSmsRequest);
        if (smsResponse != null) {
            logger.info("mobilePhone:{}, sendMsgResp:{}", mobilePhone, JSONObject.toJSONString(smsResponse));
            return smsResponse.getBody();
        }
        return null;
    }

    /**
     * 预约成功短信通知
     * @param expertPhone,expertName
     * @return
     * @throws Exception
     */
    public static SendSmsResponseBody sendAppointMsg(String expertName,String expertPhone) throws Exception{
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(expertPhone)
                .setSignName("心宅同行心理健康服务平台")
                .setTemplateCode("SMS_272080188")
                .setTemplateParam(String.format("{\"name\":\"%s\"}", expertName));;
        SendSmsResponse smsResponse = getClient(accessKey, accessKeySecert).sendSms(sendSmsRequest);
        if (smsResponse != null) {
            logger.info("mobilePhone:{}, sendMsgResp:{}", expertPhone, JSONObject.toJSONString(smsResponse));
            return smsResponse.getBody();
        }
        return null;
    }

    /**
     * 给咨询师发送房间号
     * @param consultationVO
     * @return
     * @throws Exception
     */
    public static SendSmsResponseBody sendRoomIdMsg(ConsultationVO consultationVO) throws Exception{
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(consultationVO.getExpertPhone())
                .setSignName("心宅同行心理健康服务平台")
                .setTemplateCode("SMS_272590072")
                .setTemplateParam(String.format("{\"name\":\"%s\",\"roomId\":\"%s\"}", consultationVO.getExpertName(),consultationVO.getRoomId()));
        SendSmsResponse smsResponse = getClient(accessKey, accessKeySecert).sendSms(sendSmsRequest);
        if (smsResponse != null) {
            logger.info("mobilePhone:{}, sendMsgResp:{}", consultationVO.getExpertPhone(), JSONObject.toJSONString(smsResponse));
            return smsResponse.getBody();
        }
        return null;
    }

    /**
     * 咨询师确认预约后给咨客发送短信提醒
     * @param patientName
     * @param userPhone
     * @return
     * @throws Exception
     */
    /*public static SendSmsResponseBody sendConfirmMsg(String patientName,String userPhone) throws Exception{
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(userPhone)
                .setSignName("心宅同行心理健康服务平台")
                .setTemplateCode("SMS_272410720")
                .setTemplateParam(String.format("{\"name\":\"%s\"}", patientName));;
        SendSmsResponse smsResponse = getClient(accessKey, accessKeySecert).sendSms(sendSmsRequest);
        if (smsResponse != null) {
            logger.info("mobilePhone:{}, sendMsgResp:{}", userPhone, JSONObject.toJSONString(smsResponse));
            return smsResponse.getBody();
        }
        return null;
    }*/

    public static SendSmsResponseBody sendConfirmMsg(
            String patientName,String userPhone,String txNumber
            ,String visitDate,String beginTime,String endTime) throws Exception{
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(userPhone)
                .setSignName("心宅同行心理健康服务平台")
                .setTemplateCode("SMS_272431129")
                .setTemplateParam(String.format("{\"name\":\"%s\",\"txNumber\":\"%s\",\"visitDate\":\"%s\",\"beginTime\":\"%s\"}",
                        patientName,txNumber,visitDate,beginTime));;
        SendSmsResponse smsResponse = getClient(accessKey, accessKeySecert).sendSms(sendSmsRequest);
        if (smsResponse != null) {
            logger.info("mobilePhone:{}, sendMsgResp:{}", userPhone, JSONObject.toJSONString(smsResponse));
            return smsResponse.getBody();
        }
        return null;
    }

    /**
     * 提醒咨客支付预约费用
     * @param userPhone
     * @param patientName
     * @return
     * @throws Exception
     */
    public static SendSmsResponseBody sendPayRemind(String userPhone,String patientName) throws Exception{
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(userPhone)
                .setSignName("心宅同行心理健康服务平台")
                .setTemplateCode("SMS_272615935")
                .setTemplateParam(String.format("{\"name\":\"%s\"}", patientName));;
        SendSmsResponse smsResponse = getClient(accessKey, accessKeySecert).sendSms(sendSmsRequest);
        if (smsResponse != null) {
            logger.info("mobilePhone:{}, sendMsgResp:{}", userPhone, JSONObject.toJSONString(smsResponse));
            return smsResponse.getBody();
        }
        return null;
    }

    public static void main(String[] args_) throws Exception {
        /*java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dysmsapi20170525.Client client = AliYunSmsUtils.getClient(accessKey, accessKeySecert);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("15201412349")
                .setSignName("心宅同行")
                .setTemplateCode("SMS_259430450")
                .setTemplateParam("{\"code\":\"1234\"}");
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse smsResponse = client.sendSms(sendSmsRequest);
        System.out.println(JSONObject.toJSONString(smsResponse));*/
        /*SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("15201412349")
                .setSignName("心宅同行心理健康服务平台")
                .setTemplateCode("SMS_272080188")
                .setTemplateParam("{\"name\":\"何毓琦\"}");
        SendSmsResponse smsResponse = getClient("LTAI5tD2NpYHjyifEH3AeJYf", "cXyibhYW9hjTv4M2dBvUNg48wHbyJ6").sendSms(sendSmsRequest);
        System.out.println(JSONObject.toJSONString(smsResponse));*/
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("15201412349")
                .setSignName("心宅同行心理健康服务平台")
                .setTemplateCode("SMS_272590072")
                .setTemplateParam("{\"name\":\"何毓琦\",\"roomId\":\"1234\"}");
        SendSmsResponse smsResponse = getClient("LTAI5tD2NpYHjyifEH3AeJYf", "cXyibhYW9hjTv4M2dBvUNg48wHbyJ6").sendSms(sendSmsRequest);
        System.out.println(JSONObject.toJSONString(smsResponse));
    }
}
