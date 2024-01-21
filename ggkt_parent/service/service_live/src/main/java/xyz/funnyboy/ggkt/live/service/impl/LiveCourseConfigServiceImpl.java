package xyz.funnyboy.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.live.mapper.LiveCourseConfigMapper;
import xyz.funnyboy.ggkt.live.service.LiveCourseConfigService;
import xyz.funnyboy.ggkt.model.live.LiveCourseConfig;

/**
 * <p>
 * 直播课程配置表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
@Service
public class LiveCourseConfigServiceImpl extends ServiceImpl<LiveCourseConfigMapper, LiveCourseConfig> implements LiveCourseConfigService
{

    /**
     * 按 ID 获取课程配置课程
     *
     * @param id 编号
     * @return {@link LiveCourseConfig}
     */
    @Override
    public LiveCourseConfig getCourseConfigCourseById(Long id) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LiveCourseConfig>().eq(LiveCourseConfig::getLiveCourseId, id));
    }
}
