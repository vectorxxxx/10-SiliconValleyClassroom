package xyz.funnyboy.ggkt.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-20 11:47:20
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务注册发现
@EnableDiscoveryClient
public class ServiceOrderApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
