package xyz.funnyboy.ggkt.user.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean
{

    @Value("${wechat.mpAppId}")
    private String appid;

    @Value("${wechat.mpAppSecret}")
    private String appsecret;

    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;

    @Value("${wechat.userInfoUrlOpenApi}")
    private String userInfoUrlOpenApi;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String USER_INFO_URL;
    public static String USER_INFO_URL_OPEN_API;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = appid;
        ACCESS_KEY_SECRET = appsecret;
        USER_INFO_URL = userInfoUrl;
        USER_INFO_URL_OPEN_API = userInfoUrlOpenApi;
    }
}
