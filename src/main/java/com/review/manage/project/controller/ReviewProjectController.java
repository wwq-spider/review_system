package com.review.manage.project.controller;
import com.review.common.CommonUtils;
import com.review.common.WxAppletsUtils;
import com.review.manage.project.entity.ReviewProjectEntity;
import com.review.manage.project.service.IReviewProjectService;
import com.review.manage.project.vo.ReviewProjectVO;
import com.review.manage.report.service.ReportService;
import com.review.manage.reviewClass.entity.ReviewClassEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("reviewProject")
public class ReviewProjectController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IReviewProjectService reviewProjectService;

    @Autowired
    private ReportService reportService;

    /**
     * 项目列表列表
     * @return
     */
    @RequestMapping(params="toProjectList")
    public ModelAndView toProjectList() {
        return new ModelAndView("review/manage/project/projectList");
    }

    /**
     * 跳到项目添加/修改页面
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(params="toAdd")
    public ModelAndView toAdd(HttpServletRequest request) throws UnsupportedEncodingException {
        String projectId = request.getParameter("projectId");
        List<ReviewClassEntity> classList = reportService.findHql("from ReviewClassEntity order by createTime DESC");
        ModelAndView model = new ModelAndView("review/manage/project/projectAdd");
        if(!"".equals(StringUtils.trimToEmpty(projectId))) {
            ReviewProjectEntity project = reviewProjectService.get(Long.valueOf(projectId));
            model.addObject("project", project);
        }
        model.addObject("classList", classList);
        return model;
    }

    /**
     * 新增/修改项目
     * @param reviewProject
     * @return
     */
    @RequestMapping(params = "addorupdate")
    @ResponseBody
    public AjaxJson addOrUpdate(ReviewProjectEntity reviewProject) {
        boolean result;
        if (reviewProject.getId() != null && reviewProject.getId() > 0) {
            reviewProject.setUpdateTime(new Date());
            result = reviewProjectService.update(reviewProject);
        } else {
            TSUser tsUser = ClientManager.getInstance().getClient(ContextHolderUtils.getSession().getId()).getUser();
            reviewProject.setCreateTime(new Date());
            reviewProject.setUpdateTime(new Date());
            reviewProject.setCreator(tsUser.getUserName());
            result = reviewProjectService.add(reviewProject);
        }

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(result);
        ajaxJson.setObj(reviewProject.getId());
        if (result) {
            ajaxJson.setMsg("添加/修改成功");
        } else {
            ajaxJson.setMsg("操作失败");
        }
        return ajaxJson;
    }

    /**
     * 项目列表查询
     * @param response
     * @param reviewProject
     */
    @RequestMapping(params = "datagrid")
    @ResponseBody
    public void datagrid(HttpServletResponse response, DataGrid dataGrid, ReviewProjectEntity reviewProject) {
        List<ReviewProjectVO> list = reviewProjectService.getReviewProjectList(reviewProject, dataGrid);
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("rows", list);
        json.put("total", reviewProjectService.getReviewProjectCount(reviewProject));
        CommonUtils.responseDatagrid(response, json);
    }

    /**
     * 删除项目
     * @param projectId
     * @return
     */
    @RequestMapping(params="del")
    @ResponseBody
    public AjaxJson delete(Long projectId) {
        AjaxJson ajax =  new AjaxJson();
        try {
            reviewProjectService.delProject(projectId);
            ajax.setSuccess(true);
            ajax.setMsg("删除成功!");
        } catch (Exception e) {
            ajax.setMsg("删除失败!");
            ajax.setSuccess(false);
            logger.error("delete project error: " + ExceptionUtil.getExceptionMessage(e));
        }
        return ajax;
    }

    /**
     * 二维码预览
     * @param projectId
     * @param codelink
     * @return
     */
    @RequestMapping(params="previewQrCode")
    public ModelAndView previewQrCode(Long projectId, String codelink) {
        ModelAndView modelAndView = new ModelAndView("review/manage/project/codePreview");
        modelAndView.addObject("projectId", projectId);
        modelAndView.addObject("codelink", codelink);
        return modelAndView;
    }

    /**
     * 生成二维码
     * @param response
     * @param projectId
     */
    @RequestMapping(params = "generateAppletsQrCode")
    @ResponseBody
    public void generateAppletsQrCode(HttpServletResponse response, Long projectId, boolean refresh) {
        JSONObject resJson = new JSONObject();

        if (projectId == null) {
            resJson.put("code", 300);
            resJson.put("msg", "测评项目ID为空，无法生成二维码");
            CommonUtils.responseDatagrid(response, resJson, MediaType.APPLICATION_JSON_VALUE);
            return;
        }

        ReviewProjectEntity reviewProject = reviewProjectService.get(ReviewProjectEntity.class, projectId);
        if (reviewProject == null) {
            resJson.put("code", 301);
            resJson.put("msg", "测评项目不存在，无法生成二维码");
            CommonUtils.responseDatagrid(response, resJson, MediaType.APPLICATION_JSON_VALUE);
            return;
        }

        if (StringUtils.isNotBlank(reviewProject.getAppletsCrCodeLink()) && !refresh) {
            resJson.put("code", 200);
            resJson.put("result", reviewProject.getAppletsCrCodeLink());
            CommonUtils.responseDatagrid(response, resJson, MediaType.APPLICATION_JSON_VALUE);
            return;
        }

        //生成二维码
        String rootPath = ContextHolderUtils.getSession().getServletContext().getRealPath("/");
        String qrCodePath = WxAppletsUtils.geneAppletsQrCode("pages/index/index", "projectId=" + projectId, rootPath);

        reviewProject.setAppletsCrCodeLink(qrCodePath);
        reviewProject.setUpdateTime(new Date());
        reviewProjectService.saveOrUpdate(reviewProject);

        resJson.put("code", 200);
        resJson.put("msg", "生成成功");
        resJson.put("result", qrCodePath);

        CommonUtils.responseDatagrid(response, resJson, MediaType.APPLICATION_JSON_VALUE);
    }
}
