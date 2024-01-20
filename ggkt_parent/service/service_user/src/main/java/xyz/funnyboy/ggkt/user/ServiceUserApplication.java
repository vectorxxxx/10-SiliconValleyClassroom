package xyz.funnyboy.ggkt.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-20 13:36:16
 */
@SpringBootApplication(scanBasePackages = "xyz.funnyboy")
// 开启服务注册发现
@EnableDiscoveryClient
public class ServiceUserApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}
