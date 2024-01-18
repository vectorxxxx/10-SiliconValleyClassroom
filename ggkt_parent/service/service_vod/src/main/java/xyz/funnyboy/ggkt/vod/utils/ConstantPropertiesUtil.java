package xyz.funnyboy.ggkt.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类，读取配置文件application.properties中的配置
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/18
 * @see InitializingBean
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean
{

    @Value("${tencent.cos.file.region}")
    private String region;

    @Value("${tencent.cos.file.secretid}")
    private String secretId;

    @Value("${tencent.cos.file.secretkey}")
    private String secretKey;

    @Value("${tencent.cos.file.bucketname}")
    private String bucketName;

    public static String REGION;
    public static String ACCESS_SECRET_ID;
    public static String ACCESS_SECRET_KEY;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        REGION = region;
        ACCESS_SECRET_ID = secretId;
        ACCESS_SECRET_KEY = secretKey;
        BUCKET_NAME = bucketName;
    }
}
