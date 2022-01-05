package com.review.front.controller;

import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("project")
public class ProjectController extends BaseController {

    /**
     * 生成测评项目报告
     * @param projectId
     */
    @RequestMapping("")
    public void geneReport(Long projectId) {

    }
}
