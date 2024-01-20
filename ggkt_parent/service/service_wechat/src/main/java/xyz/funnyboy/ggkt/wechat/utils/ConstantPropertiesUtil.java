package xyz.funnyboy.ggkt.wechat.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类，读取配置文件application.properties中的配置
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/20
 * @see InitializingBean
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean
{

    @Value("${wechat.mpAppId}")
    private String appid;

    @Value("${wechat.mpAppSecret}")
    private String appsecret;

    @Value("${wechat.menuUrl}")
    private String menuUrl;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String MENU_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = appid;
        ACCESS_KEY_SECRET = appsecret;
        MENU_URL = menuUrl;
    }
}
