package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.Chapter;
import xyz.funnyboy.ggkt.model.vod.Video;
import xyz.funnyboy.ggkt.vo.vod.ChapterVo;
import xyz.funnyboy.ggkt.vo.vod.VideoVo;
import xyz.funnyboy.ggkt.vod.mapper.ChapterMapper;
import xyz.funnyboy.ggkt.vod.service.ChapterService;
import xyz.funnyboy.ggkt.vod.service.VideoService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService
{

    @Autowired
    private VideoService videoService;

    /**
     * 获取嵌套树列表
     *
     * @param courseId 课程编号
     * @return {@link List}<{@link ChapterVo}>
     */
    @Override
    public List<ChapterVo> getNestedTreeList(Long courseId) {
        // 获取章节信息
        final List<Chapter> chapterList = baseMapper.selectList(new LambdaQueryWrapper<Chapter>()
                .eq(Chapter::getCourseId, courseId)
                .orderByAsc(Chapter::getSort));

        // 获取课时信息
        final List<Video> videoList = videoService.list(new LambdaQueryWrapper<Video>()
                .eq(Video::getCourseId, courseId)
                .orderByAsc(Video::getSort));

        // 组装嵌套树列表
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            // 组装章节信息
            final ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoList.add(chapterVo);

            // 组装课时信息
            List<VideoVo> videoVoList = new ArrayList<>();
            for (Video video : videoList) {
                if (video
                        .getChapterId()
                        .longValue() == chapter
                        .getId()
                        .longValue()) {
                    final VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }
}
