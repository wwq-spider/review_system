package com.review.front.controller;

import com.review.common.CommonUtils;
import com.review.manage.expert.entity.ReviewExpertCalendarEntity;
import com.review.manage.expert.entity.ReviewExpertEntity;
import com.review.manage.expert.service.ReviewExpertServiceI;
import com.review.manage.expert.vo.ReviewExpertCalendarVO;
import com.review.manage.expert.vo.ReviewExpertVO;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/reviewFront/expert")
public class ReviewExpertController extends BaseController {

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
        json.put("code", 200);
        json.put("rows", reviewExpertCalendarList);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
