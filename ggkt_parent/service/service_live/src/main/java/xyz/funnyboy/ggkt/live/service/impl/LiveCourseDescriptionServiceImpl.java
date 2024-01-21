package xyz.funnyboy.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.live.mapper.LiveCourseDescriptionMapper;
import xyz.funnyboy.ggkt.live.service.LiveCourseDescriptionService;
import xyz.funnyboy.ggkt.model.live.LiveCourseDescription;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
@Service
public class LiveCourseDescriptionServiceImpl extends ServiceImpl<LiveCourseDescriptionMapper, LiveCourseDescription> implements LiveCourseDescriptionService
{

    /**
     * 按 ID 获取直播课程
     *
     * @param courseId 课程编号
     * @return {@link LiveCourseDescription}
     */
    @Override
    public LiveCourseDescription getLiveCourseById(Long courseId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<LiveCourseDescription>().eq(LiveCourseDescription::getLiveCourseId, courseId));
    }
}
