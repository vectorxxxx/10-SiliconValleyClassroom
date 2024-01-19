package xyz.funnyboy.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.funnyboy.ggkt.model.vod.VideoVisitor;
import xyz.funnyboy.ggkt.vo.vod.VideoVisitorCountVo;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor>
{

    /**
     * 查询统计数据
     *
     * @param courseId  课程编号
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return {@link List}<{@link VideoVisitorCountVo}>
     */
    List<VideoVisitorCountVo> findCount(Long courseId, String startDate, String endDate);
}
