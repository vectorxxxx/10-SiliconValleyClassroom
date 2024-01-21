package xyz.funnyboy.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.live.LiveCourseDescription;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
public interface LiveCourseDescriptionService extends IService<LiveCourseDescription>
{

    /**
     * 按 ID 获取直播课程
     *
     * @param courseId 课程编号
     * @return {@link LiveCourseDescription}
     */
    LiveCourseDescription getLiveCourseById(Long courseId);
}
