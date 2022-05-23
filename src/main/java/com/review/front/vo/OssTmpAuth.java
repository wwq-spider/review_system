package com.review.front.vo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * oss临时授权结果
 */
public class OssTmpAuth implements Serializable {

    private Boolean ret;

    private AuthResult result;

    private String token;

    private Long tokenExpiryTime;

    public static class AuthResult  implements Serializable{
        private String ossEndPoint;
        private String ossBucket;
        private String ossObjName;

        private JSONObject ossCredentials;

        public String getOssEndPoint() {
            return ossEndPoint;
        }

        public void setOssEndPoint(String ossEndPoint) {
            this.ossEndPoint = ossEndPoint;
        }

        public String getOssBucket() {
            return ossBucket;
        }

        public void setOssBucket(String ossBucket) {
            this.ossBucket = ossBucket;
        }

        public String getOssObjName() {
            return ossObjName;
        }

        public void setOssObjName(String ossObjName) {
            this.ossObjName = ossObjName;
        }

        public JSONObject getOssCredentials() {
            return ossCredentials;
        }

        public void setOssCredentials(JSONObject ossCredentials) {
            this.ossCredentials = ossCredentials;
        }
    }

    public Boolean getRet() {
        return ret;
    }

    public void setRet(Boolean ret) {
        this.ret = ret;
    }

    public AuthResult getResult() {
        return result;
    }

    public void setResult(AuthResult result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTokenExpiryTime() {
        return tokenExpiryTime;
    }

    public void setTokenExpiryTime(Long tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }
}
