package xyz.funnyboy.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.live.mapper.LiveCourseAccountMapper;
import xyz.funnyboy.ggkt.live.service.LiveCourseAccountService;
import xyz.funnyboy.ggkt.model.live.LiveCourseAccount;

/**
 * <p>
 * 直播课程账号表（受保护信息） 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
@Service
public class LiveCourseAccountServiceImpl extends ServiceImpl<LiveCourseAccountMapper, LiveCourseAccount> implements LiveCourseAccountService
{

    /**
     * 按ID获取直播课程帐户课程
     *
     * @param id 编号
     * @return {@link LiveCourseAccount}
     */
    @Override
    public LiveCourseAccount getLiveCourseAccountCourseById(Long id) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LiveCourseAccount>().eq(LiveCourseAccount::getLiveCourseId, id));
    }
}
