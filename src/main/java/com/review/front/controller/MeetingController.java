/*
package com.review.front.controller;


import com.alibaba.fastjson.JSONObject;
import com.review.common.CommonUtils;
import com.review.front.service.CreateTMeetingPrepara;
import com.review.front.vo.TecentMeetingVO;
import freemarker.template.SimpleDate;
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

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @author javabage
 * @date 2022/6/29
 *//*

@Controller
@RequestMapping("/reviewFront/meeting")
public class MeetingController extends BaseController {

    @Autowired
    private CreateTMeetingPrepara createTMeetingPrepara;

    private static String MEETING_DOMAIN_URL = "https://api.meeting.qq.com";
    private static Logger logger = LoggerFactory.getLogger(MeetingController.class);
    private static String UserId = "";
    private static String subject = "专家视频咨询";//会议主题

    */
/**
     * 创建预约腾讯会议
     * @return
     *//*

    @RequestMapping(value = "creatMeeting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void creatMeeting(HttpServletResponse response, @RequestBody TecentMeetingVO tecentMeetingVO) throws ParseException {

        //日期转换为Unix时间戳/秒
        String startSplit = tecentMeetingVO.getVisitDate() + " " + tecentMeetingVO.getStartTime() + ":00";
        String endSplit = tecentMeetingVO.getVisitDate() + " " + tecentMeetingVO.getEndTime() + ":00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String unixStartTime = String.valueOf(sdf.parse(startSplit).getTime() / 1000);
        String unixEndTime = String.valueOf(sdf.parse(endSplit).getTime() / 1000);
        String resultBody = "{" +
            //会议结束时间
            "\"end_time\": \"" + unixEndTime + "\"" + "," +
            //会议开始时间戳/秒
            "\"start_time\": \"" + unixStartTime + "\"" + "," +
            //用户的终端设备类型 1：PC
            "\"instanceid\": " + "1" + "," +
            //会议类型：0：预约会议 1：快速会议
            "\"type\": " + "0" + "," +
            //腾讯会议用户唯一标识 不能为1-9内的数字
            "\"userid\": \"" + UserId + "\"," +
            //会议主题
            "\"subject\": \"" + createTMeetingPrepara.getUnicode(subject) + "\"" +
            "}";
        //创建会议url
        String url = "/v1/meetings";
        String address = MEETING_DOMAIN_URL + url;
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        try {
            String jsonStr = createTMeetingPrepara.sendPost(address,url,resultBody);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (null == jsonObject.getJSONObject("error_info")){
                JSONObject meetingInfo = jsonObject.getJSONArray("meeting_info_list").getJSONObject(0);
                json.put("code",200);
                json.put("msg","创建预约会议成功");
                json.put("meetingId",meetingInfo.getString("meeting_id"));
                json.put("meetingCode",meetingInfo.getString("meeting_code"));
                json.put("joinUrl",meetingInfo.getString("join_url"));
            }else {
                json.put("code",300);
                json.put("msg","创建预约会议失败");
                json.put("errorInfo",jsonObject.getJSONObject("error_info"));
            }
        }catch (Exception e){
            json.put("code",300);
            json.put("msg",e.getMessage());
            logger.info("创建预约会议发生异常",e);
        }
        CommonUtils.responseDatagrid(response, json, MediaType.APPLICATION_JSON_VALUE);
    }

    */
/**
     * 查询会议
     * @param meetingId
     * @param userId
     * @return
     *//*

    @RequestMapping(params = "queryMeetings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> queryMeetings(String meetingId,String userId){
        HashMap<String,Object> resultMap = new HashMap<>(8);
        try {
            String url = "/v1/meetings/" + meetingId + "?userid=" + userId + "&instanceid=1";
            String address = MEETING_DOMAIN_URL + url;
            String jsonStr = createTMeetingPrepara.sendGet(address,url);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            resultMap.put("message",jsonObject.getString("error_info"));
        }catch (Exception e){
            logger.info("获取会议信息异常",e);
            resultMap.put("success","false");
            resultMap.put("message",e.getMessage());
        }
        return resultMap;
    }

    */
/**
     * 取消会议
     * @param meetingId
     * @param userId
     * @return
     *//*

    @RequestMapping(params = "cancelMeeting", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> cancelMeeting(String meetingId,String userId){
        HashMap<String,Object> resultMap = new HashMap<>(8);
        String url = "/v1/meetings/" + meetingId + "/cancel";
        String address = MEETING_DOMAIN_URL + url;
        try {
            String resultBody = "{\n"
                    + "    \"meetingId\" : \"" + meetingId + "\",\n"
                    + "    \"userid\" : \"" + userId + "\",\n"
                    + "    \"instanceid\" : 1,\n"
                    + "    \"reason_code\" : 1\n"
                    + "}";
            String jsonStr = createTMeetingPrepara.sendPost(address,url,resultBody);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (null == jsonObject.getJSONObject("error_info")){
                resultMap.put("message","取消会议成功");
                resultMap.put("success","true");
            }else {
                resultMap.put("message","取消会议失败");
                resultMap.put("success","false");
                resultMap.put("errorInfo",jsonObject.getJSONObject("error_info"));
            }
        }catch (Exception e){
            resultMap.put("success","false");
            resultMap.put("message",e.getMessage());
            logger.info("取消会议异常：",e);
        }
        return resultMap;
    }
}
*/
