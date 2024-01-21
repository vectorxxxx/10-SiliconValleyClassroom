package xyz.funnyboy.ggkt.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean
{

    @Value("${wechat.pay.appid}")
    private String appid;

    @Value("${wechat.pay.mchid}")
    private String mchid;

    @Value("${wechat.pay.openid}")
    private String openid;

    @Value("${wechat.pay.signkey}")
    private String signkey;

    @Value("${wechat.pay.spbillcreateip}")
    private String spbillcreateip;

    @Value("${wechat.pay.notifyurl}")
    private String notifyurl;

    @Value("${wechat.pay.unifiedorderurl}")
    private String unifiedorderurl;

    @Value("${wechat.pay.orderquery}")
    private String orderquery;

    public static String APP_ID;
    public static String MCH_ID;
    public static String OPEN_ID;
    public static String SIGN_KEY;
    public static String SPBILL_CREATE_IP;
    public static String NOTIFY_URL;
    public static String UNIFIED_ORDER_URL;
    public static String ORDER_QUERY;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appid;
        MCH_ID = mchid;
        OPEN_ID = openid;
        SIGN_KEY = signkey;
        SPBILL_CREATE_IP = spbillcreateip;
        NOTIFY_URL = notifyurl;
        UNIFIED_ORDER_URL = unifiedorderurl;
        ORDER_QUERY = orderquery;
    }
}
