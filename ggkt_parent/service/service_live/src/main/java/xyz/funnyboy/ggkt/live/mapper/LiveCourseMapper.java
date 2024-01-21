package xyz.funnyboy.ggkt.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.funnyboy.ggkt.model.live.LiveCourse;
import xyz.funnyboy.ggkt.vo.live.LiveCourseVo;

import java.util.List;

/**
 * <p>
 * 直播课程表 Mapper 接口
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
public interface LiveCourseMapper extends BaseMapper<LiveCourse>
{

    /**
     * 获取最近的直播
     *
     * @return {@link List}<{@link LiveCourseVo}>
     */
    List<LiveCourseVo> getLatelyList();
}
