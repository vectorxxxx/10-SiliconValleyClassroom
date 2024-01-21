package xyz.funnyboy.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.live.LiveCourseConfig;

/**
 * <p>
 * 直播课程配置表 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
public interface LiveCourseConfigService extends IService<LiveCourseConfig>
{

    /**
     * 按 ID 获取课程配置课程
     *
     * @param id 编号
     * @return {@link LiveCourseConfig}
     */
    LiveCourseConfig getCourseConfigCourseById(Long id);
}
