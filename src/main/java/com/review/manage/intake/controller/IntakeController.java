package com.review.manage.intake.controller;

import com.review.manage.intake.service.IntakeService;
import com.review.manage.intake.vo.IntakeVo;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @date 2022/7/18
 */
@Controller
@RequestMapping("/intakeController")
public class IntakeController {

    @Autowired
    private IntakeService intakeService;

    private static final Logger logger = Logger.getLogger(IntakeController.class);

    /**
     * 进入intake录入页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "addIntake")
    public ModelAndView addIntake(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("review/manage/intake/addIntake2");
        //下拉框选项处理
        intakeService.handleOptions(modelAndView);
        return modelAndView;
    }

    /**
     * 保存intake信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveIntakeInfo")
    @ResponseBody
    public AjaxJson saveIntakeInfo(HttpServletRequest request, HttpServletResponse response, IntakeVo intakeVo){
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }

    /**
     * 咨询师录入咨询记录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "expertAddIntake")
    public ModelAndView expertAddIntake(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("review/manage/intake/expertAddIntake");
        //下拉框选项处理
        intakeService.handleOptions(modelAndView);
        return modelAndView;
    }
}
