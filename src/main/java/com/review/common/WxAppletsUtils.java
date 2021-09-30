package com.review.common;

import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WxAppletsUtils {

    public static Logger logger = LoggerFactory.getLogger(WxAppletsUtils.class);

    public final static String appId = "wxe1e9802d7d62c6d4";

    public final static String appSecret = "d061ed674d5557bb9a862c7d1aea9bf8";

    public final static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public final static String qrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

    public final static String openidUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&grant_type=authorization_code&&js_code=%s";

    /**
     * 获取access_token
     * @return
     */
    public static String geneAccessToken() {
        String tokenStr = postString(String.format(accessTokenUrl, appId, appSecret), "");
        JSONObject tokenObj = JSONObject.fromObject(tokenStr);
        if (tokenObj.containsKey("access_token")) {
            return tokenObj.getString("access_token");
        }
        return null;
    }

    public static String geneAppletsQrCode(String pagePath, String params) {
        String accessToken = geneAccessToken();
        JSONObject paramObj = new JSONObject();
        paramObj.put("scene", params);
        paramObj.put("page", pagePath);
        paramObj.put("width", 200);
        paramObj.put("is_hyaline", true);
        paramObj.put("auto_color", true);

        String filePath = "qrcode/%s/" + params.split("=")[1] + "_" + System.currentTimeMillis() + ".jpg";
        return postFile(String.format(qrCodeUrl, accessToken), paramObj.toString(), filePath);
    }

    public static HttpURLConnection getConnection(String url, String params) throws IOException {
        //初始化连接
        HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        //参数输出
        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
        printWriter.write(params);
        printWriter.flush();
        printWriter.close();
        return connection;
    }

    public static String postFile(String url, String params, String filePath) {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        //BufferedOutputStream out = null;
        try {
            //初始化连接
            connection = getConnection(url, params);

            //读返回流
            inputStream = connection.getInputStream();
            return OssUtils.uploadFile(String.format(filePath, DateUtil.getCurrentDateStr()), inputStream);
//            out = new BufferedOutputStream(new FileOutputStream(filePath));
//            byte[] bytes = new byte[1024];
//            int length;
//            while ((length = inputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, length);
//            }
//            out.flush();
        } catch (Exception e) {
            logger.error("post error, ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    public static String postString(String url, String params) {
        BufferedInputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            //初始化连接
            connection = getConnection(url, params);

            //读返回流
            inputStream = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
            return out.toString();
        } catch (Exception e) {
            logger.error("post error, ", e);
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 获取openid
     * @param code
     * @return
     */
    public static String getOpenid(String code) {
        String responseStr = postString(String.format(openidUrl, appId, appSecret, code), "");
        if (StringUtils.isNotBlank(responseStr)) {
            JSONObject resJson = JSONObject.fromObject(responseStr);
            if (resJson.containsKey("openid")) {
                return resJson.getString("openid");
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String accessToken = geneAccessToken();
        String rootPath = "/Library/prek/project/review_system/target/review_system";
        String path = geneAppletsQrCode("pages/index/index", "projectId=5");
        System.out.println(accessToken);
    }
}
