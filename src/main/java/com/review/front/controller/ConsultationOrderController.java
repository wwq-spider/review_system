package com.review.front.controller;

import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.front.service.IOrderService;
import com.review.front.vo.PreOrderVO;
import com.review.manage.order.vo.ReviewOrderVO;
import com.review.manage.userManage.entity.ReviewUserEntity;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;


/**
 * 问诊订单
 * @date 2022/6/9
 */
@RequestMapping("reviewFront/consultationOrder")
@Controller
public class ConsultationOrderController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Qualifier("consultationOrderServiceImpl")
    @Autowired
    private IOrderService consultationOrderService;


    /**
     * 创建预支付订单
     * @param response
     * @param reviewOrder
     */
    @RequestMapping(value = "createPrePayConsultationOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createPrePayConsultationOrder(HttpServletResponse response, @RequestBody ReviewOrderVO reviewOrder) throws Exception{

        JSONObject json = new JSONObject();
        ReviewUserEntity reviewUser = (ReviewUserEntity) ContextHolderUtils.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
        reviewOrder.setUserId(reviewUser.getUserId());
        reviewOrder.setOperator(reviewUser.getUserName());
        reviewOrder.setGroupId(reviewUser.getGroupId());
        reviewOrder.setOpenid(reviewUser.getOpenid());
        reviewOrder.setIpAddr(IpUtil.getIpAddr(ContextHolderUtils.getRequest()));
        reviewOrder.setBroswer(BrowserUtils.checkBrowse(ContextHolderUtils.getRequest()));
        reviewOrder.setMobilePhone(reviewUser.getMobilePhone());

        //创建预支付订单
        PreOrderVO preOrderVO = consultationOrderService.createPrePayOrder(reviewOrder);
        if (preOrderVO == null) {
            json.put("code", 400);
            json.put("msg", "创建失败");
        } else {
            json.put("code", 200);
            json.put("data", preOrderVO);
            json.put("msg", "创建成功");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }
}
