package xyz.funnyboy.ggkt.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.user.UserInfo;
import xyz.funnyboy.ggkt.user.mapper.UserInfoMapper;
import xyz.funnyboy.ggkt.user.service.UserInfoService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService
{

    /**
     * 通过 Open ID 获取用户信息
     *
     * @param openId 微信ID
     * @return {@link UserInfo}
     */
    @Override
    public UserInfo getByOpenId(String openId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getOpenId, openId));
    }
}
