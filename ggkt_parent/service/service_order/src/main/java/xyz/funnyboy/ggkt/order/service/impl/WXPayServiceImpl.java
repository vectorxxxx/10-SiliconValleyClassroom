package xyz.funnyboy.ggkt.order.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.order.service.WXPayService;
import xyz.funnyboy.ggkt.order.utils.ConstantPropertiesUtil;
import xyz.funnyboy.ggkt.swagger.exception.GgktException;
import xyz.funnyboy.ggkt.swagger.utils.HttpClientUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 19:53:15
 */
@Service
@Slf4j
public class WXPayServiceImpl implements WXPayService
{
    /**
     * 微信支付
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> createJsapi(String orderNo) {
        //封装微信支付需要参数，使用map集合
        Map<String, String> paramMap = new HashMap<>();
        //正式服务号id  固定值
        paramMap.put("appid", ConstantPropertiesUtil.APP_ID);
        //服务号商户号  固定值
        paramMap.put("mch_id", ConstantPropertiesUtil.MCH_ID);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("body", "test");
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("total_fee", "1");//为了测试，支付0.01
        paramMap.put("spbill_create_ip", ConstantPropertiesUtil.SPBILL_CREATE_IP);
        paramMap.put("notify_url", ConstantPropertiesUtil.NOTIFY_URL);
        paramMap.put("trade_type", "JSAPI");//支付类型，按照生成固定金额支付
        /*
         * 设置参数值当前微信用户openid
         * 实现逻辑：第一步 根据订单号获取userid  第二步 根据userid获取openid
         *
         * 因为当前使用测试号，测试号不支持支付功能，为了使用正式服务号进行测试，使用下面写法
         * 获取 正式服务号微信openid
         *
         * 通过其他方式获取正式服务号openid，直接设置
         * */
        paramMap.put("openid", ConstantPropertiesUtil.OPEN_ID);

        try {
            //通过httpclient调用微信支付接口
            HttpClientUtils client = new HttpClientUtils(ConstantPropertiesUtil.UNIFIED_ORDER_URL);
            //设置请求参数
            String paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtil.SIGN_KEY);
            client.setXmlParam(paramXml);
            client.setHttps(true);
            //请求
            client.post();

            //微信支付接口返回数据
            String xml = client.getContent();
            log.info("【微信支付接口】xml: {}", xml);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //进行封装，最终返回
            // return_code（返回状态码）：SUCCESS/FAIL。此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
            if (null != resultMap.get("result_code") && !"SUCCESS".equals(resultMap.get("result_code"))) {
                throw new GgktException(20001, "支付失败");
            }

            //4、再次封装参数
            Map<String, String> parameterMap = new HashMap<>();
            // 预支付交易会话ID：微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时,针对H5支付此参数无特殊用途
            String prepayId = String.valueOf(resultMap.get("prepay_id"));
            log.info("【微信支付接口】prepay_id: {}", prepayId);
            String packages = "prepay_id=" + prepayId;
            // String packages = "prepay_id=oKIy_6T2dI0aaBPs8TkLcf6Lcae4";
            // String packages = "prepay_id=";
            parameterMap.put("appId", ConstantPropertiesUtil.APP_ID);
            parameterMap.put("nonceStr", resultMap.get("nonce_str"));
            // 扩展字段
            parameterMap.put("package", packages);
            // 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
            parameterMap.put("signType", "MD5");
            parameterMap.put("timeStamp", String.valueOf(new Date().getTime()));
            String sign = WXPayUtil.generateSignature(parameterMap, ConstantPropertiesUtil.SIGN_KEY);

            //返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("appId", ConstantPropertiesUtil.APP_ID);
            result.put("timeStamp", parameterMap.get("timeStamp"));
            result.put("nonceStr", parameterMap.get("nonceStr"));
            result.put("signType", "MD5");
            result.put("paySign", sign);
            result.put("package", packages);
            log.info("【微信支付接口】result: {}", result);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询支付状态
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link String}>
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            // 1、封装参数
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtil.APP_ID);
            paramMap.put("mch_id", ConstantPropertiesUtil.MCH_ID);
            paramMap.put("out_trade_no", orderNo);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

            // 2、设置请求
            HttpClientUtils client = new HttpClientUtils(ConstantPropertiesUtil.ORDER_QUERY);
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtil.SIGN_KEY));
            client.setHttps(true);
            client.post();

            // 3、返回第三方的数据
            String xml = client.getContent();
            log.info("【查询支付状态】xml: {}", xml);

            // 4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            log.info("【查询支付状态】result: {}", resultMap);

            // 5、返回
            return resultMap;
        }
        catch (Exception e) {
            log.error("【查询支付状态】查询支付状态异常：" + e.getMessage(), e);
        }
        return null;
    }
}
