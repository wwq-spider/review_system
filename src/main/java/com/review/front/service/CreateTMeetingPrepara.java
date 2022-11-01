package com.review.front.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author javabage
 * @date 2022/6/29
 */
public interface CreateTMeetingPrepara {


    public String sign(String secretId,String secretKey,String httpMethod,String headerNonce,String headerTimestamp,
                String requestUrl,String requestBody) throws NoSuchAlgorithmException, InvalidKeyException;

    public Map<String,String> getHeader(String httpMethod, String requestUrl, String requestBody);

    public String sendGet(String address,String Url);

    public String sendPost(String address,String Url,String requestBody);

    public String bytesToHex(byte[] bytes);

    public String getUnicode(String str);

}
