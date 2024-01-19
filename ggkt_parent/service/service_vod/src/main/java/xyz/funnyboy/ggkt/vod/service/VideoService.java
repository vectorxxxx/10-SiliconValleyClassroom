package xyz.funnyboy.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.vod.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
public interface VideoService extends IService<Video>
{

    /**
     * 按课程 ID 删除
     *
     * @param id 编号
     */
    void removeVideoByCourseId(Long id);

    /**
     * 按 ID 移除视频
     *
     * @param id 编号
     */
    void removeVideoById(Long id);
}
