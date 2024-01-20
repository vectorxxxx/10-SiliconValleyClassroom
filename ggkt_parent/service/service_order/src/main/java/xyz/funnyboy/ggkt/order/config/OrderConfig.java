package xyz.funnyboy.ggkt.order.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-20 11:56:50
 */
@Configuration
@MapperScan("xyz.funnyboy.ggkt.order.mapper")
public class OrderConfig
{
    /**
     * 分页插件
     *
     * @return {@link PaginationInterceptor}
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
