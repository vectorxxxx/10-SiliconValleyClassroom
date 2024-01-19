package xyz.funnyboy.ggkt.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 跨域配置类
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 23:54:55
 */
@Component
public class CorsConfig
{
    @Bean
    public CorsWebFilter corsWebFilter() {
        // 设置跨域配置
        CorsConfiguration config = new CorsConfiguration();
        // 允许所有请求源
        config.addAllowedOrigin("*");
        // 允许所有请求方法
        config.addAllowedMethod("*");
        // 允许所有请求头
        config.addAllowedHeader("*");

        // 跨域允许重定向
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
