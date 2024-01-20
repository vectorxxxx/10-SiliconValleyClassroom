package xyz.funnyboy.ggkt.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 23:52:02
 */
@SpringBootApplication
// 开启服务注册发现
@EnableDiscoveryClient
public class ApiGatewayApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
