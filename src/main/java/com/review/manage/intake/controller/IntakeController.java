package com.review.manage.intake.controller;

import com.review.common.CommonUtils;
import com.review.manage.intake.entity.IntakeDetailEntity;
import com.review.manage.intake.service.IntakeService;
import com.review.manage.intake.vo.IntakeVo;
import com.review.manage.order.entity.ReviewOrderEntity;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.vo.ReviewProjectVO;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.RoletoJson;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @date 2022/7/18
 */
@Controller
@RequestMapping("/intakeController")
public class IntakeController {

    @Autowired
    private IntakeService intakeService;

    private static final Logger logger = Logger.getLogger(IntakeController.class);

    @RequestMapping(params="toIntakeList")
    public ModelAndView toIntakeList() {
        return new ModelAndView("review/manage/intake/intakeList");
    }

    /**
     * 进入intake录入页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "addIntake")
    public ModelAndView addIntake(IntakeVo intakeVo,HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("review/manage/intake/addIntake");
        if (StringUtil.isNotEmpty(intakeVo.getId())){
           /* tsDepart = intakeService.getEntity(TSDepart.class,tsDepart.getId());
            modelAndView.addObject("data",tsDepart);*/
            modelAndView.addObject("data",intakeVo);
        }
        //下拉框选项处理
        intakeService.handleOptions(modelAndView);
        return modelAndView;
    }

    /**
     * 员工信息验证
     * @param intakeVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "clientInfoVerificat")
    @ResponseBody
    public ModelAndView clientInfoVerificat(IntakeVo intakeVo,HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("review/manage/intake/clientInfoVerificat");
        modelAndView.addObject("employeePhone",intakeVo.getEmployeePhone());
        modelAndView.addObject("employeeName",intakeVo.getEmployeeName());
        return modelAndView;
    }

    /**
     * 获取验证信息
     * @param intakeVo
     * @param response
     */
    @RequestMapping(params = "getClientInfo")
    @ResponseBody
    public void getClientInfo(IntakeVo intakeVo,HttpServletResponse response) {
        List<IntakeVo> intakeVoList = intakeService.getIntakeInfo(intakeVo);
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("rows", intakeVoList);
        json.put("total", intakeVoList.size());
        CommonUtils.responseDatagrid(response, json);
    }

    /**
     * 保存intake信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "saveIntakeInfo")
    @ResponseBody
    public AjaxJson saveIntakeInfo(IntakeDetailEntity intakeDetail,HttpServletRequest request, HttpServletResponse response){
        AjaxJson ajaxJson = new AjaxJson();
        intakeService.save(intakeDetail);
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

    @RequestMapping(params = "queryIntakeRecord")
    @ResponseBody
    public void queryIntakeRecord(HttpServletResponse response) {

        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("rows", null);
        json.put("total", 1);
        CommonUtils.responseDatagrid(response, json);
    }
}
