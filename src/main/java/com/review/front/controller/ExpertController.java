package com.review.front.controller;

import cn.hutool.core.util.StrUtil;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.review.common.AliYunSmsUtils;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.front.vo.ConsultationVO;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.entity.ReviewExpertReserveEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.ContextHolderUtils;
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
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/reviewFront/expert")
public class ExpertController extends BaseController {

    @Autowired
    private ReviewExpertServiceI reviewExpertService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 查询专家列表
     * @param response
     * @param reviewExpert
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void list(HttpServletResponse response, @RequestBody ReviewExpertVO reviewExpert) {
        JSONObject json = new JSONObject();
        List<ReviewExpertVO> reviewExpertList = reviewExpertService.getReviewExperts(reviewExpert);
        json.put("code", 200);
        json.put("rows", reviewExpertList);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 查询专家详情
     * @param response
     * @param reviewExpert
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void detail(HttpServletResponse response, @RequestBody ReviewExpertVO reviewExpert) {
        JSONObject json = new JSONObject();
        ReviewExpertEntity reviewExpertEntity = reviewExpertService.get(ReviewExpertEntity.class, reviewExpert.getId());
        json.put("code", 200);
        json.put("result", reviewExpertEntity);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 查询专家日历
     * @param response
     * @param reviewExpertCalendar
     */
    @RequestMapping(value = "listCalendar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void listCalendar(HttpServletResponse response, @RequestBody ReviewExpertCalendarVO reviewExpertCalendar) {
        JSONObject json = new JSONObject();
        List<ReviewExpertCalendarVO> reviewExpertCalendarList = reviewExpertService.getReviewExpertCalendars(reviewExpertCalendar);
        //时间格式处理
        reviewExpertService.handleCalendarTime(reviewExpertCalendarList);
        json.put("code", 200);
        json.put("rows", reviewExpertCalendarList);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 预约专家
     * @param response
     * @param reviewExpertCalendar
     */
    @RequestMapping(value = "orderExpert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void orderExpert(HttpServletResponse response, @RequestBody ReviewExpertCalendarVO reviewExpertCalendar){
        JSONObject json = new JSONObject();
        reviewExpertService.orderExpert(reviewExpertCalendar.getId());
        json.put("code", 200);
        json.put("id", reviewExpertCalendar.getId());
        json.put("msg", "预约成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 保存预约人信息
     * @param response
     * @param reviewExpertReserveEntity
     */
    @RequestMapping(value = "saveOoderInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveOoderInfo(HttpServletRequest request,HttpServletResponse response, @RequestBody ReviewExpertReserveEntity[] reviewExpertReserveEntity){
        JSONObject json = new JSONObject();
        long id = reviewExpertService.saveOoderInfo(reviewExpertReserveEntity);
        json.put("code", 200);
        json.put("getPatientName", reviewExpertReserveEntity[0].getPatientName());
        json.put("msg", "预约成功");
        json.put("id",id);
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 我的问诊列表
     * @param response
     * @param consultationVO
     */
    @RequestMapping(value = "queryMyConsultation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void queryMyConsultation(HttpServletResponse response, @RequestBody ConsultationVO consultationVO) {
        JSONObject json = new JSONObject();
        List<ConsultationVO> reviewExpertReserveList = reviewExpertService.getMyConsultation(consultationVO);
        json.put("code", 200);
        json.put("result", reviewExpertReserveList);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 我的问诊详情
     * @param response
     * @param consultationVO
     */
    @RequestMapping(value = "queryMyConsultationDetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void queryMyConsultationDetail(HttpServletResponse response, @RequestBody ConsultationVO consultationVO) throws ParseException {
        JSONObject json = new JSONObject();
        List<ConsultationVO> reviewExpertReserveList = reviewExpertService.getMyConsultationDetail(consultationVO);
        //判断当前时间是否可发起视频咨询
        String videoConsultcondition = reviewExpertService.videoConsultcondition(reviewExpertReserveList);
        json.put("code", 200);
        json.put("result", reviewExpertReserveList);
        json.put("msg", "查询成功");
        json.put("videoConsult",videoConsultcondition);
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 取消预约
     * @param response
     * @param consultationVO
     */
    @RequestMapping(value = "cancelReservation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void cancelReservation(HttpServletResponse response, @RequestBody ConsultationVO consultationVO){
        JSONObject json = new JSONObject();
        //取消预约-将预约人信息status置为3：取消预约
        reviewExpertService.cancelReservation(consultationVO.getId());
        //取消预约-将专家日历状态status置为1：可预约
        reviewExpertService.updateCalendarStatus(consultationVO.getCalendarId());
        //预约短信提醒：to-预约人&专家
        logger.info(consultationVO.toString());
        json.put("code", 200);
        json.put("id", consultationVO.getId());
        json.put("msg", "取消预约成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 判断是否为专家
     * @param response
     * @param reviewUser
     */
    @RequestMapping(value = "isExpert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void isExpert(HttpServletResponse response, @RequestBody ReviewUserEntity reviewUser){
        JSONObject json = new JSONObject();

        json.put("code", 200);
        json.put("isExpert", true);
        json.put("msg", "取消预约成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 咨客发起视频咨询，给专家发送房间号
     * @param response
     * @param request
     */
    @RequestMapping(value = "sendRoomId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void sendRoomId(HttpServletResponse response, HttpServletRequest request,@RequestBody ConsultationVO consultationVO){
        JSONObject json = new JSONObject();
        if (StrUtil.isBlank(consultationVO.getExpertPhone())) {
            json.put("code", 300);
            json.put("msg", "手机号不能为空");
        } else {
            try {
                SendSmsResponseBody body = AliYunSmsUtils.sendMsg(consultationVO.getRoomId(), consultationVO.getExpertPhone());
                if (body != null && "ok".equalsIgnoreCase(body.getCode())) {
                    ContextHolderUtils.getSession().setAttribute(consultationVO.getExpertPhone() + Constants.MSG_CODE_KEY, consultationVO.getRoomId());
                    json.put("code", 200);
                    json.put("msg", "验证码发送成功");
                } else {
                    json.put("code", 400);
                    json.put("msg", "验证码发送失败");
                }
            } catch (Exception e) {
                logger.error("sendMsg error, ", e);
                json.put("code", 500);
                json.put("msg", "验证码发送异常，请联系管理员");
            }
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
