package com.review.front.entity;

import java.io.Serializable;

/**
 * @author javabage
 * @date 2022/8/31
 */
public class EvalCodeEntity implements Serializable {

    private String userId;
    private String evalCode;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEvalCode() {
        return evalCode;
    }

    public void setEvalCode(String evalCode) {
        this.evalCode = evalCode;
    }

}
