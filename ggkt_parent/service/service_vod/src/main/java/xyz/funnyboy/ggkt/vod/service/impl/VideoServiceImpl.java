package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.Video;
import xyz.funnyboy.ggkt.vod.mapper.VideoMapper;
import xyz.funnyboy.ggkt.vod.service.VideoService;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService
{

    /**
     * 按课程 ID 删除
     *
     * @param id 编号
     */
    @Override
    public void removeByCourseId(Long id) {
        baseMapper.delete(new LambdaQueryWrapper<Video>().eq(Video::getCourseId, id));
    }
}
