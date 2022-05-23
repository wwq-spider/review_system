package com.review.front.controller;

import cn.hutool.core.util.StrUtil;
import com.review.common.CommonUtils;
import com.review.manage.reviewClass.service.ReviewClassService;
import com.review.manage.reviewClass.service.ReviewReportTemplateServiceI;
import com.review.manage.reviewClass.vo.ReviewClassVO;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("reviewFront/report")
@Controller
public class ReviewReportControlller extends BaseController {

    @Autowired
    private ReviewReportTemplateServiceI reviewReportTemplateService;

    @Autowired
    private ReviewClassService reviewClassService;

    /**
     * 查询量表对应的报告模板
     * @param response
     * @param reviewClass
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void list(HttpServletResponse response, @RequestBody ReviewClassVO reviewClass) {
        JSONObject json = new JSONObject();
        if (StrUtil.isBlank(reviewClass.getClassId())) {
            json.put("code", 300);
            json.put("msg", "量表ID不能为空");
        } else {
            json.put("code", 200);
            json.put("data", reviewReportTemplateService.getByClassId(reviewClass.getClassId()));
            json.put("msg", "查询成功");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
