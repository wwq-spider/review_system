package com.review.front.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.review.common.AliYunSmsUtils;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.front.entity.ReviewResultEntity;
import com.review.front.vo.ConsultationVO;
import com.review.manage.expert.entity.ReviewExpertCalendarEntity;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.entity.ReviewExpertReserveEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import com.review.manage.question.vo.QuestionVO;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reviewFront/expert")
public class ExpertController extends BaseController {

    @Autowired
    private ReviewExpertServiceI reviewExpertService;

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
        reviewExpertService.saveOoderInfo(reviewExpertReserveEntity);
        json.put("code", 200);
        json.put("getPatientName", reviewExpertReserveEntity[0].getPatientName());
        json.put("msg", "预约成功");
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
    public void queryMyConsultationDetail(HttpServletResponse response, @RequestBody ConsultationVO consultationVO) {
        JSONObject json = new JSONObject();
        List<ConsultationVO> reviewExpertReserveList = reviewExpertService.getMyConsultationDetail(consultationVO);

        json.put("code", 200);
        json.put("result", reviewExpertReserveList);
        json.put("msg", "查询成功");
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
        reviewExpertService.cancelReservation(consultationVO.getId());
        json.put("code", 200);
        json.put("id", consultationVO.getId());
        json.put("msg", "取消预约成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
