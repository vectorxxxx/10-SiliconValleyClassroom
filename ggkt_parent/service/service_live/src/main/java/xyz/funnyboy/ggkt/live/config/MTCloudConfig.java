package xyz.funnyboy.ggkt.live.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.funnyboy.ggkt.live.mtcloud.MTCloud;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-22 00:08:14
 */
@Component
public class MTCloudConfig
{
    @Autowired
    private MTCloudAccountConfig mtCloudAccountConfig;

    @Bean
    public MTCloud mtCloudClient() {
        return new MTCloud(mtCloudAccountConfig.getOpenId(), mtCloudAccountConfig.getOpenToken());
    }
}
