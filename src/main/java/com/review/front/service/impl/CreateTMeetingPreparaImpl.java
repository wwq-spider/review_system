package com.review.front.service.impl;

import com.review.front.service.CreateTMeetingPrepara;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author javabage
 * @date 2022/6/29
 */
@Service
public class CreateTMeetingPreparaImpl implements CreateTMeetingPrepara {

    private static Logger logger = LoggerFactory.getLogger(CreateTMeetingPreparaImpl.class);

    private static String HMAC_ALGORITHM = "HmacSHA256";

    private static char[] HEX_CHAR = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    @Override
    public String sign(String secretId, String secretKey, String httpMethod, String headerNonce, String headerTimestamp, String requestUrl, String requestBody) throws NoSuchAlgorithmException, InvalidKeyException{
        String tobeSig = httpMethod + "\nX-TC-Key=" + secretId
                + "&X-TC-Nonce=" + headerNonce
                + "&X-TC-Timestamp=" + headerTimestamp
                + "\n" + requestUrl + "\n" + requestBody;
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(tobeSig.getBytes(StandardCharsets.UTF_8));
        String hexHash = bytesToHex(hash);
        return new String(Base64.getEncoder().encode(hexHash.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public Map<String, String> getHeader(String httpMethod, String requestUrl, String requestBody) {

        HashMap<String,String> header = new HashMap<>(8);
        //请求随机数
        String headerNonce = String.valueOf(new Random().nextInt(999999));
        //当前时间的UNIX时间戳
        String headerTimestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = null;
        try {
            signature = sign("secretId","secretKey",httpMethod,headerNonce,headerTimestamp,requestUrl,requestBody);
        }catch (Exception e){
            logger.info("签名生成异常",e);
        }

        header.put("Content-Type","application/json");
        header.put("X-TC-Key","secretId");
        header.put("X-TC-Timestamp",headerTimestamp);
        header.put("X-TC-Nonce",headerNonce);
        header.put("AppId","AppId");
        header.put("X-TC-Version","1.0");
        header.put("X-TC-Signature",signature);
        header.put("SdkId","SdkId");
        header.put("X-TC-Registered","1");

        return header;
    }

    @Override
    public String sendGet(String address, String Url) {

        Map<String,String> header = getHeader("GET",Url,"");
        String result = "";
        String logInfo = "";
        GetMethod getMethod = null;
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
            getMethod = new GetMethod(address);
            getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,60000);
            if (header != null){
                for (Map.Entry<String,String> entry : header.entrySet()){
                    getMethod.addRequestHeader(entry.getKey(),entry.getValue());
                }
            }
            logInfo = "HTTP调用接口：" + address;
            httpClient.executeMethod(getMethod);
            result = getMethod.getResponseBodyAsString();
        }catch (Exception e){
            logger.info("HTTP调用接口出错：" + logInfo + e,e);
        }finally {
            if (null != getMethod){
                getMethod.releaseConnection();
            }
        }
        return result;
    }

    @Override
    public String sendPost(String address, String Url, String requestBody) {

        Map<String,String> headerMap = getHeader("POST",Url,requestBody);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(address);
        String jsonStr = "";
        try {
            for (Map.Entry<String,String> header : headerMap.entrySet()){
                httpPost.setHeader(header.getKey(),header.getValue());
            }
            httpPost.setEntity(new StringEntity(requestBody));
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null){
                jsonStr = EntityUtils.toString(httpEntity,"UTF-8");
            }
            logger.info("腾讯会议post请求响应信息：",jsonStr);
        }catch (IOException e){
            logger.info("腾讯会议post请求异常",e);
        }finally {
            httpPost.releaseConnection();
            try {
                httpClient.close();
            }catch (IOException e){
                logger.error("httpClient关闭异常",e);
            }
        }
        return jsonStr;
    }

    @Override
    public String bytesToHex(byte[] bytes) {
        char[] buf = new char[bytes.length*2];
        int index = 0;
        for (byte b : bytes){
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
        return new String(buf);
    }

    @Override
    public String getUnicode(String str) {
        try {
            StringBuilder out = new StringBuilder("");
            byte[] bytes = str.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String s = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = s.length(); j < 2; j++) {
                    out.append("0");
                }
                String s1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(s1);
                out.append(s);
            }
            return out.toString();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        }
    }
}
