package xyz.funnyboy.ggkt.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-20 15:19:45
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务注册发现
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "xyz.funnyboy")
public class ServiceWechatApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceWechatApplication.class, args);
    }
}
