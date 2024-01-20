package xyz.funnyboy.ggkt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.user.UserLoginLog;
import xyz.funnyboy.ggkt.user.mapper.UserLoginLogMapper;
import xyz.funnyboy.ggkt.user.service.UserLoginLogService;

/**
 * <p>
 * 用户登陆记录表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService
{

}
