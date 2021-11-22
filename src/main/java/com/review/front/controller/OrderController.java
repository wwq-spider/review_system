package com.review.front.controller;
import com.review.common.CommonUtils;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.vo.ReviewOrderVO;
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

/**
 * 测评订单
 */
@RequestMapping("reviewFront/order")
@Controller
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    /**
     * 创建预支付订单
     * @param response
     * @param reviewOrder
     */
    @RequestMapping(value = "createPrePayOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createPrePayOrder(HttpServletResponse response, @RequestBody ReviewOrderVO reviewOrder) {
        JSONObject json = new JSONObject();
        PreOrderVO preOrderVO = orderService.createPrePayOrder(reviewOrder);
        json.put("code", 200);
        json.put("data", preOrderVO);
        json.put("msg", "创建成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}