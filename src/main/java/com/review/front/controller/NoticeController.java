package com.review.front.controller;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.notice.entity.ReviewNoticeEntity;
import com.review.manage.notice.service.ReviewNoticeServiceI;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公告前端controller
 */
@RequestMapping("/reviewFront/notice")
@Controller
public class NoticeController extends BaseController {

    @Autowired
    private ReviewNoticeServiceI reviewNoticeService;

    /**
     * 公告列表查询
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void list(HttpServletResponse response, @RequestBody DataGrid dataGrid) {
        int page = dataGrid.getPage() > 0 ? dataGrid.getPage() : 1;
        int pageSize = dataGrid.getPage() > 0 ? dataGrid.getPage() : 1;
        JSONObject json = new JSONObject();
        List<ReviewNoticeEntity> reviewNoticeList = reviewNoticeService.findHql("from ReviewNotice where status=? limit ?,?",
                Constants.StatusPublish, (page-1) * pageSize, pageSize);
        json.put("code", 200);
        json.put("rows", reviewNoticeList);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
