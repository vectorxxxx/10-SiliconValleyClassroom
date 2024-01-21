package xyz.funnyboy.ggkt.swagger.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/21
 * @see HandlerInterceptor
 */
public class UserLoginInterceptor implements HandlerInterceptor
{

    private RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.initUserLoginVo(request);
        return true;
    }

    private void initUserLoginVo(HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println(token);
        //String userId = request.getHeader("userId");
        if (StringUtils.isEmpty(token)) {
            AuthContextHolder.setUserId(1L);
            return;
        }

        Long userId = JwtHelper.getUserId(token);
        System.out.println("当前用户：" + userId);
        if (StringUtils.isEmpty(userId)) {
            AuthContextHolder.setUserId(1L);
            return;
        }

        AuthContextHolder.setUserId(userId);
    }

}
