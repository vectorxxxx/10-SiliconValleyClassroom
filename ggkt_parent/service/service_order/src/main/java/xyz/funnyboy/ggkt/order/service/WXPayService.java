package xyz.funnyboy.ggkt.order.service;

import java.util.Map;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 19:53:00
 */
public interface WXPayService
{
    /**
     * 微信支付
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> createJsapi(String orderNo);

    /**
     * 查询支付状态
     *
     * @param orderNo 订单号
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> queryPayStatus(String orderNo);
}
