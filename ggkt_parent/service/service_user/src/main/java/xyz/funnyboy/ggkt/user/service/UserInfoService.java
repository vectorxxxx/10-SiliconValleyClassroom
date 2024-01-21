package xyz.funnyboy.ggkt.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.user.UserInfo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
public interface UserInfoService extends IService<UserInfo>
{

    /**
     * 通过 Open ID 获取用户信息
     *
     * @param openId 微信ID
     * @return {@link UserInfo}
     */
    UserInfo getByOpenId(String openId);
}
