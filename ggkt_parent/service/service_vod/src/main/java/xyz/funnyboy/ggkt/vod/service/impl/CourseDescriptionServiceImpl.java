package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.CourseDescription;
import xyz.funnyboy.ggkt.vod.mapper.CourseDescriptionMapper;
import xyz.funnyboy.ggkt.vod.service.CourseDescriptionService;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService
{

    /**
     * 按课程 ID 删除
     *
     * @param id 编号
     */
    @Override
    public void removeByCourseId(Long id) {
        baseMapper.delete(new LambdaQueryWrapper<CourseDescription>().eq(CourseDescription::getCourseId, id));
    }

    /**
     * 按课程 ID 获取描述
     *
     * @param courseId
     * @return {@link String}
     */
    @Override
    public String getDescriptionByCourseId(Long courseId) {
        return baseMapper
                .selectOne(new LambdaQueryWrapper<CourseDescription>().eq(CourseDescription::getCourseId, courseId))
                .getDescription();
    }
}
