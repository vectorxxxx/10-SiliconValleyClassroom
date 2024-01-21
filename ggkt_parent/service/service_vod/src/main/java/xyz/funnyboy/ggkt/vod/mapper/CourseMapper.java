package xyz.funnyboy.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.vo.vod.CoursePublishVo;
import xyz.funnyboy.ggkt.vo.vod.CourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
public interface CourseMapper extends BaseMapper<Course>
{

    /**
     * 根据课程ID查询课程发布VO
     *
     * @param id 编号
     * @return {@link CoursePublishVo}
     */
    CoursePublishVo selectCoursePublishVoById(
            @Param("id")
                    Long id);

    /**
     * 按 ID 查询课程 VO
     *
     * @param id 编号
     * @return {@link CourseVo}
     */
    CourseVo selectCourseVoById(
            @Param("id")
                    Long id);
}
