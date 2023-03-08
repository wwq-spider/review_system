package com.review.front.controller;

import cn.hutool.core.util.RandomUtil;
import com.review.front.vo.ConsultationVO;
import com.review.manage.expert.service.ReviewExpertServiceI;
import net.sf.json.JSONObject;
import com.review.common.CommonUtils;
import com.review.common.WxAppletsUtils;
import com.review.common.httpclient.HttpClientUtils;
import com.review.front.entity.WxSubscribeMsg;
import com.review.front.entity.WxTemplateValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author javabage
 * @date 2022/7/22
*/
@Controller
@RequestMapping("/reviewFront/subscribeMessage")
public class SubscribeMessageController {

    @Autowired
    private ReviewExpertServiceI reviewExpertService;

    //模板id
    //private final static String templateId = "tz0qAaZq2v0s3dZbfPnOYwkFy7QOF82XVFNvpLZGTNQ";
    private final static String templateId = "4IVeiK2tYEmXqTcDJ7IVnXduD2CToUiV9Sz7ZHCObfs";
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final static String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";

    @RequestMapping(value = "sendSubscribeMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void sendSubscribeMessage(HttpServletRequest request, HttpServletResponse response, @RequestBody WxSubscribeMsg wxSubscribeMsg){

        wxSubscribeMsg.setTemplate_id(templateId);
        // 模板消息到小程序跳转页面
        //wxSubscribeMsg.setPage("pages/index/index");
        //int roomID = this.threadRandom();
        String roomID = RandomUtil.randomNumbers(4);
        //wxSubscribeMsg.setPage("pages/room/room?userID=oGvzT5cBpAiP48NzKwX7HeqlbI2Q&template=grid&roomID="+roomID+"&debugMode=false&cloudenv=PRO&consulId="+wxSubscribeMsg.getConsulId()+"&mobilePhone="+wxSubscribeMsg.getMobilePhone());
        wxSubscribeMsg.setPage("pages/room/room?userID=" + wxSubscribeMsg.getUserId() + "&template=grid&roomID=" + roomID+"&debugMode=false&cloudenv=PRO&consulId="+wxSubscribeMsg.getConsulId()+"&mobilePhone="+wxSubscribeMsg.getMobilePhone());
        //开发版
        //wxSubscribeMsg.setMiniprogram_state("developer");
        //跳转体验版
        //wxSubscribeMsg.setMiniprogram_state("trial");
        //跳转正式版
        wxSubscribeMsg.setMiniprogram_state("formal");
        //模板消息
        Map<String, WxTemplateValue> map = new HashMap<>();
        WxTemplateValue time6 = new WxTemplateValue();
        WxTemplateValue time7 = new WxTemplateValue();
        WxTemplateValue thing2 = new WxTemplateValue();
        //WxTemplateValue thing5 = new WxTemplateValue();
        WxTemplateValue thing6 = new WxTemplateValue();

        time6.setValue(wxSubscribeMsg.getVisitDate() + " " + wxSubscribeMsg.getBeginTime());
        map.put("time6", time6);
        time7.setValue(wxSubscribeMsg.getVisitDate() + " " + wxSubscribeMsg.getEndTime());
        map.put("time7", time7);
        /*thing4.setValue("1小时");
        map.put("thing4", thing4);
        thing5.setValue("视频咨询");*/
        thing2.setValue("视频咨询");
        map.put("thing2", thing2);
        thing6.setValue(wxSubscribeMsg.getExpertName());
        map.put("thing4", thing6);
        // 推送模板参数
        wxSubscribeMsg.setData(map);
        // 参数转json
        String json = JSONObject.fromObject(wxSubscribeMsg).toString();
        //获取AccessToken
        String accessToken = WxAppletsUtils.geneAccessToken();
        // 调用微信推送模板接口
        String doPostJson = HttpClientUtils.doPost(requestUrl+accessToken, com.alibaba.fastjson.JSONObject.parseObject(json),"utf-8");
        // 将获取到的数据进行判断进行日志写入
        JSONObject jsonObject = JSONObject.fromObject(doPostJson);
        logger.info("调用微信模板消息回调结果："+jsonObject);
        CommonUtils.responseDatagrid(response, jsonObject, MediaType.APPLICATION_JSON_VALUE);
    }
}
