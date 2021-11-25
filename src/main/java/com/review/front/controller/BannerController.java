package com.review.front.controller;

import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.manage.banner.service.ReviewBannerServiceI;
import com.review.manage.banner.vo.ReviewBannerVO;
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
@RequestMapping("/reviewFront/banner")
@Controller
public class BannerController extends BaseController {

    @Autowired
    private ReviewBannerServiceI reviewBannerServiceI;

    /**
     * banner列表查询
     * @param response
     * @param dataGrid
     */
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void list(HttpServletResponse response, @RequestBody DataGrid dataGrid) {
        int page = dataGrid.getPage() > 0 ? dataGrid.getPage() : 1;
        int pageSize = dataGrid.getRows() > 0 ? dataGrid.getRows() : 1;
        JSONObject json = new JSONObject();

        Map<String, Object> param = new HashMap<>();
        param.put("status", Constants.StatusPublish);

        StringBuilder sql = new StringBuilder("select id, title, img_url imgUrl, target_url targetUrl, status from ");
        sql.append(" review_banner where status=:status ");
        sql.append(" order by operate_time desc limit ");
        sql.append((page-1) * pageSize);
        sql.append(",");
        sql.append(pageSize);

        List<ReviewBannerVO> reviewBannerList = reviewBannerServiceI.getObjectList(sql.toString(), param, ReviewBannerVO.class);
        json.put("code", 200);
        json.put("rows", reviewBannerList);
        json.put("msg", "查询成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
