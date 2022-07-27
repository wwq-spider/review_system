package com.review.front.entity;

import lombok.Data;

/**
 * @author javabage
 * @date 2022/7/22
 */
@Data
public class WxUserInfo {

    private String openid;

    private String session_key;

    private String unionid;

    private int errcode;

    private String errmsg;
}
