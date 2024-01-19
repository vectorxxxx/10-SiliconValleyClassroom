package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.ggkt.model.vod.Video;
import xyz.funnyboy.ggkt.vod.mapper.VideoMapper;
import xyz.funnyboy.ggkt.vod.service.VideoService;
import xyz.funnyboy.ggkt.vod.service.VodService;

import java.util.List;

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
    @Autowired
    private VodService vodService;

    /**
     * 按课程 ID 删除
     *
     * @param id 编号
     */
    @Override
    public void removeVideoByCourseId(Long id) {
        final LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<Video>().eq(Video::getCourseId, id);
        final List<Video> videoList = baseMapper.selectList(queryWrapper);
        videoList.forEach(video -> {
            final String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                vodService.removeVideo(videoSourceId);
            }
        });
        baseMapper.delete(queryWrapper);
    }

    /**
     * 按 ID 移除视频
     *
     * @param id 编号
     */
    @Override
    public void removeVideoById(Long id) {
        final LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<Video>().eq(Video::getId, id);
        final Video video = baseMapper.selectOne(queryWrapper);
        final String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodService.removeVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }
}
