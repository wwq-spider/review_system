package com.review.front.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.review.common.CommonUtils;
import com.review.common.httpclient.HttpClientUtils;
import com.review.front.entity.DongliangTestQuestionVO;
import com.review.front.entity.EvalCodeEntity;
import com.review.front.entity.TestRecord;
import com.review.front.service.DongLiangTestService;
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
import java.util.ArrayList;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DongLiangTestService dongLiangTestService;

    /**
     * 完成测试，提交调用栋梁接口
     * @param response
     */
    @RequestMapping(value = "commitTest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void commitTest(HttpServletResponse response, @RequestBody DongliangTestQuestionVO[] dongliangTestQuestionVO){

        /*
        * 1、调用栋梁测评码加密接口
        * 2、调用答题提交接口
        * 3、提交成功后变更测评码状态为已使用
        */
        //questNo及第二套试题处理
        int I = 1;//第一套试题题号
        int M = 1;//第二套试题题号
        int N = 1;
        int A = 1;//第三套试题题号
        int P = 1;//第四套试题题号
        List<TestRecord> testRecordList = new ArrayList<>();
        List<TestRecord> testRecordListOld = dongliangTestQuestionVO[0].getTestRecord();
        for (int i = 0; i < testRecordListOld.size(); i++) {
            if (i < 162){
                TestRecord testRecord = new TestRecord();
                testRecord.setQuesNo("I" + I);
                testRecord.setAnswer(testRecordListOld.get(i).getAnswer());
                testRecord.setScoreA(testRecordListOld.get(i).getScoreA());
                testRecord.setScoreB(testRecordListOld.get(i).getScoreB());
                testRecordList.add(testRecord);
                I++;
            } else if (i >= 162 && i < 204){
                dongLiangTestService.handleQuestNo(testRecordListOld,i,testRecordList,M,"M");
                M++;
            } else if (i >= 204 && i < 246){
                dongLiangTestService.handleQuestNo(testRecordListOld,i,testRecordList,N,"N");
                N++;
            } else if (i >= 246 && i < 336 ){
                TestRecord testRecord = new TestRecord();
                testRecord.setQuesNo("A" + A);
                testRecord.setAnswer(testRecordListOld.get(i).getAnswer());
                testRecord.setScoreA(testRecordListOld.get(i).getScoreA());
                testRecord.setScoreB(testRecordListOld.get(i).getScoreB());
                testRecordList.add(testRecord);
                A++;
            } else if ( i >= 336 ){
                TestRecord testRecord = new TestRecord();
                testRecord.setQuesNo("P" + P);
                testRecord.setAnswer(testRecordListOld.get(i).getAnswer());
                testRecord.setScoreA(testRecordListOld.get(i).getScoreA());
                testRecord.setScoreB(testRecordListOld.get(i).getScoreB());
                testRecordList.add(testRecord);
                P++;
            }
        }
        dongliangTestQuestionVO[0].setTestRecord(testRecordList);
        String param = JSON.toJSONString(dongliangTestQuestionVO);
        String paramSub = param.substring(1,param.length()-1);
        String resultJson = HttpClientUtils.doPost(dongLiangApiurl,JSONObject.parseObject(paramSub),"utf-8");

        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        json.put("code", 200);
        json.put("msg", "查询成功");
        //net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(resultJson);
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
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
}
