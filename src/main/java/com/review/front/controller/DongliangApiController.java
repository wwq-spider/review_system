package com.review.front.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.review.common.CommonUtils;
import com.review.common.Constants;
import com.review.common.httpclient.HttpClientUtils;
import com.review.front.entity.DongliangTestQuestionVO;
import com.review.front.entity.EvalCodeEntity;
import com.review.front.entity.TestRecord;
import com.review.front.service.DongLiangTestService;
import com.review.manage.userManage.entity.ReviewUserEntity;
import org.jeecgframework.core.common.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author javabage
 * @date 2022/8/30
 */
@Controller
@RequestMapping("/reviewFront/dongLiang")
public class DongliangApiController extends BaseController {

    //栋梁测评提交接口地址
    private static final String dongLiangApiurl = "http://www.zhuxinkang.com:9999/api/commitTest";

    private static final String reportUrl = "https://www.zhuxinkang.com/review/upload2";

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DongLiangTestService dongLiangTestService;

    /**
     * 完成测试，提交调用栋梁接口
     * @param response
     */
    @RequestMapping(value = "commitTest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void commitTest(HttpServletResponse response, HttpServletRequest request,@RequestBody DongliangTestQuestionVO[] dongliangTestQuestionVO) throws Exception{

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ReviewUserEntity user = (ReviewUserEntity) request.getSession().getAttribute(Constants.REVIEW_LOGIN_USER);
        //接口入参处理
        dongLiangTestService.handleData(dongliangTestQuestionVO);
        String param = JSON.toJSONString(dongliangTestQuestionVO);
        String paramSub = param.substring(1,param.length()-1);
        //调用栋梁答题提交接口
        String resultJson = HttpClientUtils.doPost(dongLiangApiurl,JSONObject.parseObject(paramSub),"utf-8");
        if (resultJson != null && !"".equals(resultJson)){
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(resultJson);
            if ((Integer) json.get("code") == 200){
                String pdfUrl = json.getString("data");
                String pdfUrlView = reportUrl + pdfUrl;
                dongliangTestQuestionVO[0].setReportUrl(pdfUrlView);
                //业务数据处理
                dongLiangTestService.handleBusinessData(dongliangTestQuestionVO,new ReviewUserEntity());
                json.put("pdfUrl",pdfUrlView);
                CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
            }
        }else {
            net.sf.json.JSONObject json = new net.sf.json.JSONObject();
            json.put("code", 500);
            json.put("msg", "提交失败");
            CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
        }
    }

    /**
     * 验证测评码
     * @param response
     * @param evalCodeEntity
     */
    @RequestMapping(value = "verifyEvalCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void verifyEvalCode(HttpServletResponse response, @RequestBody EvalCodeEntity evalCodeEntity){

        List<EvalCodeEntity> list = dongLiangTestService.verifyEvalCode(evalCodeEntity);
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        if (list.size() != 0 && list != null){
            json.put("code",200);
            json.put("msg", "测评码有效");
        }else {
            json.put("code",0);
            json.put("msg", "测评码无效或不存在");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * 获取pdf文件流
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "getPDFStream",method = RequestMethod.GET)
    @ResponseBody
    public void getPDFStream(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String filePath = request.getParameter("url");
        File file = new File(filePath);
        byte[] data = null;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/pdf");
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            data = new byte[input.available()];
            input.read(data);
            response.getOutputStream().write(data);
        }catch (IOException e){
            input.close();
        }
    }
    @RequestMapping(value = "getEvalCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getEvalCode(HttpServletResponse response){

        List<EvalCodeEntity> list = dongLiangTestService.getEvalCode();
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        if (list.size() != 0 && list != null){
            json.put("code",200);
            json.put("evalCode",list.get(0).getEvalCode());
            json.put("msg", "获取测评码成功");
        }else {
            json.put("code",0);
            json.put("msg", "库存不足");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    @RequestMapping(value = "updateEvalCodeStock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void updateEvalCodeStock(HttpServletResponse response, @RequestBody EvalCodeEntity evalCodeEntity){

        String sql = "update review_eval_code set status=1 where eval_code = ?";
        Integer count = dongLiangTestService.executeSql(sql,evalCodeEntity.getEvalCode());
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        if (count != 0){
            json.put("code",200);
            json.put("msg", "该订单未成功支付");
        }else {
            json.put("code",0);
            json.put("msg", "支付异常");
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    @RequestMapping(value = "getEvalPrice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getEvalPrice(HttpServletResponse response, @RequestBody EvalCodeEntity evalCodeEntity){

        String price = dongLiangTestService.getEvalPrice(evalCodeEntity);
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        json.put("code",200);
        json.put("price",price);
        json.put("msg", "获取测评码价格成功");
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

}
