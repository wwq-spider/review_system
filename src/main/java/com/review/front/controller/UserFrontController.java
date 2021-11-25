package com.review.front.controller;

import cn.hutool.core.util.StrUtil;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.userManage.entity.ReviewUserEntity;
import com.review.manage.userManage.service.ReviewUserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RequestMapping("/reviewFront/user")
@Controller
public class UserFrontController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewUserService reviewUserService;

    /**
     * 修改用户信息
     * @param response
     * @param reviewUser
     */
    @RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void updUserInfo(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser) {
        JSONObject json = new JSONObject();
        if (reviewUser == null || StrUtil.isBlank(reviewUser.getUserId())) {
            json.put("code", 300);
            json.put("msg", "用户信息为空");
            CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
            return;
        }
        if (!reviewUser.getUserId().equals(ContextHolderUtils.getLoginFrontUserID())) {
            json.put("code", 400);
            json.put("msg", "非本人登陆，不允许修改");
            CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
            return;
        }
        try {
            ReviewUserEntity reviewUserOld = reviewUserService.get(ReviewUserEntity.class, reviewUser.getUserId());
            if (reviewUserOld == null) {
                json.put("code", 404);
                json.put("msg", "用户不存在");
                CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
                return;
            }

            if (!reviewUserOld.getOpenid().equals(reviewUser.getOpenid())) {
                json.put("code", 400);
                json.put("msg", "用户openid不可修改");
                CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
                return;
            }

            if(!reviewUserOld.getMobilePhone().equals(reviewUser.getMobilePhone())) { //需要修改手机号
                //check 验证码
                String msgCode = (String) ContextHolderUtils.getSession().getAttribute(reviewUser.getMobilePhone() + Constants.MSG_CODE_KEY);
                if (StringUtils.isBlank(msgCode) || !msgCode.equals(reviewUser.getMsgCode())) {
                    json.put("code", 301);
                    json.put("msg", "短信验证码不正确或已过期");
                    CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
                    return;
                }
            }
            MyBeanUtils.copyBeanNotNull2Bean(reviewUser, reviewUserOld);
            reviewUserOld.setUpdateTime(new Date());
            reviewUserService.saveOrUpdate(reviewUserOld);
            ContextHolderUtils.getSession().removeAttribute(reviewUser.getMobilePhone() + Constants.MSG_CODE_KEY);
            json.put("code", 200);
            json.put("msg", "用户信息修改成功");
            json.put("result", reviewUserOld.getUserId());
        } catch (Exception e) {
            json.put("code", 302);
            json.put("msg", "用户信息修改失败，");
            logger.error("register error, ", e);
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}