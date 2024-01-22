package xyz.funnyboy.ggkt.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 23:54:41
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务注册发现
@EnableDiscoveryClient
// 开启Feign功能
@EnableFeignClients(basePackages = "xyz.funnyboy")
public class ServiceLiveApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceLiveApplication.class, args);
    }
}
