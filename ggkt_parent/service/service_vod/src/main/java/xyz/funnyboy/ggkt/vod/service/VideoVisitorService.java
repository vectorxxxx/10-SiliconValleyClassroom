package xyz.funnyboy.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.vod.VideoVisitor;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
public interface VideoVisitorService extends IService<VideoVisitor>
{

    /**
     * 查询统计数据
     *
     * @param courseId  课程编号
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
