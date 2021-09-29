package com.review.common;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.ResourceBundle;

public class OssUtils {

    public static Logger logger = LoggerFactory.getLogger(OssUtils.class);

    private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("oosconfig");

    private static String bucketName = bundle.getString("IMG_BUCHETNAME");

    private static volatile OSS ossClient;

    public static OSS getOssClient() {
        if (ossClient == null) {
            synchronized (OssUtils.class) {
                if (ossClient == null) {
                     ossClient = new OSSClientBuilder().build(bundle.getString("ENDPOINT"), bundle.getString("ACCESS_ID"),
                             bundle.getString("ACCESS_KEY"));
                }
            }
        }
        return ossClient;
    }

    /**
     * 上传文件
     * @param fileName
     * @param bytes
     * @return
     */
    public static String uploadFile(String fileName, byte[] bytes) {
        ByteArrayInputStream is = null;
        try {
            is = new ByteArrayInputStream(bytes);
            String filePath = String.format(fileName, DateUtil.getCurrentDate());
            getOssClient().putObject(bucketName, filePath, is);
            return filePath;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
