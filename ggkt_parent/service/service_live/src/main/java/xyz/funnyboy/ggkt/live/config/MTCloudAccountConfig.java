package xyz.funnyboy.ggkt.live.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-22 00:07:29
 */
@Data
@Component
@ConfigurationProperties(prefix = "mtcloud")
public class MTCloudAccountConfig
{
    private String openId;
    private String openToken;
}
