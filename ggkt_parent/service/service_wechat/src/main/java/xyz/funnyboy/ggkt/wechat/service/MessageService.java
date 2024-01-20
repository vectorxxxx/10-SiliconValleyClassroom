package xyz.funnyboy.ggkt.wechat.service;

import java.util.Map;

/**
 * <p>
 * 消息服务
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
public interface MessageService
{
    /**
     * 接收消息
     *
     * @param param 参数
     * @return {@link String}
     */
    String receiveMessage(Map<String, String> param);
}
