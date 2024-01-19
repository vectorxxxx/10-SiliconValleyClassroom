package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.VideoVisitor;
import xyz.funnyboy.ggkt.vo.vod.VideoVisitorCountVo;
import xyz.funnyboy.ggkt.vod.mapper.VideoVisitorMapper;
import xyz.funnyboy.ggkt.vod.service.VideoVisitorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService
{

    /**
     * 查询统计数据
     *
     * @param courseId  课程编号
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        // 查询数据
        List<VideoVisitorCountVo> videoVisitorList = baseMapper.findCount(courseId, startDate, endDate);

        // 解析数据
        final List<String> joinTimeList = videoVisitorList
                .stream()
                .map(VideoVisitorCountVo::getJoinTime)
                .collect(Collectors.toList());
        final List<Integer> userCountList = videoVisitorList
                .stream()
                .map(VideoVisitorCountVo::getUserCount)
                .collect(Collectors.toList());

        // 组装数据
        Map<String, Object> map = new HashMap<>();
        map.put("xData", joinTimeList);
        map.put("yData", userCountList);
        return map;
    }
}
