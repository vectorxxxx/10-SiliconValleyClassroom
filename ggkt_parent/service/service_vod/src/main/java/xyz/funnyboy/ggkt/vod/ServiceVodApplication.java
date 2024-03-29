package xyz.funnyboy.ggkt.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "xyz.funnyboy")
@EnableDiscoveryClient
public class ServiceVodApplication
{
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class, args);
    }
}
