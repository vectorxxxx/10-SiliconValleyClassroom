package xyz.funnyboy.ggkt.swagger.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * 登录 MVC 配置器适配器
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/21
 * @see WebMvcConfigurerAdapter
 */
@Configuration
public class LoginMvcConfigurerAdapter extends WebMvcConfigurerAdapter
{

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加自定义拦截器，设置路径
        registry
                .addInterceptor(new UserLoginInterceptor(redisTemplate))
                .addPathPatterns("/api/**");
        registry
                .addInterceptor(new AdminLoginInterceptor(redisTemplate))
                .addPathPatterns("/admin/**");
        super.addInterceptors(registry);
    }
}
