package com.review.front.vo;

import java.io.Serializable;

/**
 * 心理健康分析结果
 */
public class AnalysisResult implements Serializable {

    private boolean ret;

    private String token;

    private Long tokenExpiryTime;

    private ResultObj result;

    private String ErrorCode;

    public static class ResultObj implements Serializable {
        private String checkId;
        private double hr;
        private double sbp;
        private double dbp;
        private double br;
        private double spo2;
        private double bmi;
        private double score;

        public String getCheckId() {
            return checkId;
        }

        public void setCheckId(String checkId) {
            this.checkId = checkId;
        }

        public double getHr() {
            return hr;
        }

        public void setHr(double hr) {
            this.hr = hr;
        }

        public double getSbp() {
            return sbp;
        }

        public void setSbp(double sbp) {
            this.sbp = sbp;
        }

        public double getDbp() {
            return dbp;
        }

        public void setDbp(double dbp) {
            this.dbp = dbp;
        }

        public double getBr() {
            return br;
        }

        public void setBr(double br) {
            this.br = br;
        }

        public double getSpo2() {
            return spo2;
        }

        public void setSpo2(double spo2) {
            this.spo2 = spo2;
        }

        public double getBmi() {
            return bmi;
        }

        public void setBmi(double bmi) {
            this.bmi = bmi;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
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

    public ResultObj getResult() {
        return result;
    }

    public void setResult(ResultObj result) {
        this.result = result;
    }
}
