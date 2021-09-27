package com.review.common;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class WxAppletsUtils {

    public static Logger logger = LoggerFactory.getLogger(WxAppletsUtils.class);

    public final static String appId = "wxe1e9802d7d62c6d4";
    public final static String appSecret = "d061ed674d5557bb9a862c7d1aea9bf8";

    public final static String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    public final static String qrCodeUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

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

    public static String geneAppletsQrCode(String pagePath, String params, String rootPath) {
        String accessToken = geneAccessToken();
        JSONObject paramObj = new JSONObject();
        paramObj.put("scene", params);
        paramObj.put("page", pagePath);
        paramObj.put("width", 200);
        paramObj.put("is_hyaline", true);
        paramObj.put("auto_color", true);

        File file = new File(rootPath + "/upload/qrcode/");
        if (!file.exists()) {
            file.mkdir();
        }
        String filePath = "/upload/qrcode/" + params.split("=")[1] + "_" + System.currentTimeMillis() + ".jpg";
        postFile(String.format(qrCodeUrl, accessToken), paramObj.toString(), rootPath + filePath);
        return filePath;
    }

    public static HttpURLConnection getConnection(String url, String params) throws IOException {
        HttpURLConnection connection = null;
        //初始化连接
        connection = (HttpURLConnection)new URL(url).openConnection();
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

    public static void postFile(String url, String params, String filePath) {
        BufferedInputStream inputStream = null;
        HttpURLConnection connection = null;
        BufferedOutputStream out = null;
        try {
            //初始化连接
            connection = getConnection(url, params);

            //读返回流
            inputStream = new BufferedInputStream(connection.getInputStream());
            out = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            logger.error("post error, ", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(out);
        }
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

    public static void main(String[] args) {
        String accessToken = geneAccessToken();
        String rootPath = "/Library/prek/project/ReviewSystem/target/ReviewSystem";
        String path = geneAppletsQrCode("pages/index/index", "projectId=5", rootPath);
        System.out.println(accessToken);
    }
}
