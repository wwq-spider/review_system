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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int pageSize = dataGrid.getRows() > 0 ? dataGrid.getRows() : 1;
        JSONObject json = new JSONObject();

        Map<String, String> param = new HashMap<>();
        param.put("status", Constants.StatusPublish+"");

        StringBuilder sql = new StringBuilder("select id, notice_name noticeName from ");
        sql.append(" review_notice where status=:status ");
        sql.append(" order by update_time desc limit ");
        sql.append((page-1) * pageSize);
        sql.append(",");
        sql.append(pageSize);

        List<ReviewNoticeEntity> reviewNoticeList = reviewNoticeService.getObjectList(sql.toString(), param, ReviewNoticeEntity.class);
        json.put("code", 200);
        json.put("rows", reviewNoticeList);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 公告详情查询
     * @param response
     * @param reviewNotice
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void list(HttpServletResponse response, @RequestBody ReviewNoticeEntity reviewNotice) {
        JSONObject json = new JSONObject();
        if (reviewNotice.getId() != null && reviewNotice.getId() > 0) {
            json.put("code", 200);
            json.put("data", reviewNoticeService.get(ReviewNoticeEntity.class, reviewNotice.getId()));
            json.put("msg", "查询成功");
        } else {
            json.put("code", 300);
            json.put("msg", "公告id为空");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
