package com.review.front.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.review.common.CommonUtils;
import com.review.common.VideoAnalysisConfig;
import com.review.common.httpclient.HttpClientUtils;
import com.review.front.vo.AnalysisResult;
import com.review.front.vo.AnalysisStatus;
import com.review.front.vo.AnalysisType;
import com.review.front.vo.OssTmpAuth;
import com.review.manage.videoAnalysis.entity.ReviewVideoAnalysisEntity;
import com.review.manage.videoAnalysis.service.ReviewVideoAnalysisServiceI;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 心理健康分析
 */
@Service
public class AnalysisService {

    private static Logger logger = LoggerFactory.getLogger(AnalysisService.class);

    @Autowired
    private ReviewVideoAnalysisServiceI videoAnalysisService;

    private Map<String, String> getBaseParam() {
        Map<String, String> param = new TreeMap<>();
        param.put("pid", VideoAnalysisConfig.PID);
        param.put("appid", VideoAnalysisConfig.APP_ID);
        param.put("timestamp", System.currentTimeMillis()+"");
        return param;
    }

    /**
     * 获取oss临时授权
     * @return
     */
    public OssTmpAuth getOssAuth() {
        Map<String, String> param = getBaseParam();
        param.put("sign", CommonUtils.getSign(CommonUtils.getSignContent(param), VideoAnalysisConfig.SECURE_KEY));
        try {
            String result = HttpClientUtils.doPost(VideoAnalysisConfig.OSS_AUTH_URL, param);
            logger.info("OssAuth result: {}", result);
            OssTmpAuth ossTmpAuth = JSONObject.toJavaObject(JSONObject.parseObject(result), OssTmpAuth.class);

            if (ossTmpAuth == null || !ossTmpAuth.getRet()) {
                logger.warn("OssAuth get fail....");
                throw new RuntimeException("OssAuth get fail");
            }
            return ossTmpAuth;
        } catch (Exception e) {
            throw new RuntimeException("getOssAuth error, ", e);
        }
    }

    public String uploadVideoToOss(OssTmpAuth ossTmpAuth, InputStream in) {
        OSS ossClient = null;
        try{
            OssTmpAuth.AuthResult authResult = ossTmpAuth.getResult();
            JSONObject ossCredentials = authResult.getOssCredentials();

            ossClient = new OSSClientBuilder().build(authResult.getOssEndPoint(), ossCredentials.getString("AccessKeyId"),
                    ossCredentials.getString("AccessKeySecret"), ossCredentials.getString("SecurityToken"));
            PutObjectResult putObjectResult = ossClient.putObject(authResult.getOssBucket(), authResult.getOssObjName(), in);
            logger.info("PutObjectResult:{}", JSONObject.toJSONString(putObjectResult));
            return authResult.getOssObjName();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * 心理健康分析
     * @param ossObjName
     * @param token
     * @param analysisType
     * @return
     */
    public String getAnalysisResult(String ossObjName, String token, AnalysisType analysisType) {
        //3.调用视频生理分析接口
        String result = "";
        Map<String, String> params = getBaseParam();
        if (analysisType.getValue() == AnalysisType.Health.getValue()) { //心理健康分析
            params.put("obj_name", ossObjName);
            params.put("sign", CommonUtils.getSign(CommonUtils.getSignContent(params), VideoAnalysisConfig.SECURE_KEY));
            params.put("token", token);
            result = HttpClientUtils.doPost(VideoAnalysisConfig.HEALTH_ANALYSIS_URL, params);
        } else {
            params.put("obj_names", ossObjName);
            params.put("callback_uri", VideoAnalysisConfig.CALLBACK_URI);
            params.put("sign", CommonUtils.getSign(CommonUtils.getSignContent(params), VideoAnalysisConfig.SECURE_KEY));
            params.put("token", token);
            result = HttpClientUtils.doPost(VideoAnalysisConfig.EMO_ANALYSIS_URL, params);
        }
        logger.info("type: {}, analysis result:{}", analysisType, result);
        //JSONObject.toJavaObject(JSONObject.parseObject(result), AnslysisResult.class);
        return result;
    }

    /**
     * 奇点视频分析(心理/表情)
     * @param videoBytes
     * @param classID
     * @param userID
     * @param projectId
     * @return
     */
    public ReviewVideoAnalysisEntity analysis(byte[] videoBytes, String classID, String userID, Long projectId) {
        //1.分析结果
        //1.获取oss临时授权
        OssTmpAuth ossTmpAuth = getOssAuth();

        //2.上传视频到OSS
        String ossObjName = uploadVideoToOss(ossTmpAuth, new ByteArrayInputStream(videoBytes));

        if (StrUtil.isBlank(ossObjName)) {
            throw new RuntimeException("Analysis failed: uploadVideoToOss failed");
        }

        //心理分析
        String healthResult = this.getAnalysisResult(ossObjName, ossTmpAuth.getToken(), AnalysisType.Health);
        JSONObject healthObj = JSONObject.parseObject(healthResult);
        AnalysisResult healthAnslysisResult = JSONObject.toJavaObject(healthObj, AnalysisResult.class);

        //表情分析
       /* String emoResult = this.getAnalysisResult(ossObjName, ossTmpAuth.getToken(), AnalysisType.Emo);
        JSONObject emoObj = JSONObject.parseObject(emoResult);
        AnalysisResult emoAnslysisResult = JSONObject.toJavaObject(emoObj, AnalysisResult.class);*/

        //保存结果
        ReviewVideoAnalysisEntity videoAnalysis = new ReviewVideoAnalysisEntity();
        videoAnalysis.setHealthAnalysisResult(healthResult);
        videoAnalysis.setCreateTime(new Date());
        videoAnalysis.setClassId(classID);
        videoAnalysis.setUserId(userID);
        videoAnalysis.setProjectId(projectId);
        //videoAnalysis.setEmoAnalysisResult(emoResult);
        videoAnalysis.setVideoPath(ossObjName);

        if (healthAnslysisResult.isRet()) {
            videoAnalysis.setHealthStatus(AnalysisStatus.Success.getValue());
        } else {
            videoAnalysis.setHealthStatus(AnalysisStatus.Failed.getValue());
            videoAnalysis.setHealthMsg(healthObj.getString("ErrorMessage"));
        }

        /*if (emoAnslysisResult.isRet()) {
            videoAnalysis.setEmoStatus(AnalysisStatus.Analysising.getValue());
        } else {
            videoAnalysis.setEmoStatus(AnalysisStatus.Failed.getValue());
            videoAnalysis.setEmoMsg(emoObj.getString("ErrorMessage"));
        }*/
        videoAnalysisService.save(videoAnalysis);
        return videoAnalysis;
    }

    public static void main(String[] args) {

        AnalysisService analysisService = new AnalysisService();

        analysisService.analysis(FileUtil.readBytes("/Users/bytedance/Desktop/1644468469137904.mp4"), "1", "", 1l);

    }
}
