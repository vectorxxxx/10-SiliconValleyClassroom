package xyz.funnyboy.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.vo.vod.CourseQueryVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
public interface CourseService extends IService<Course>
{

    /**
     * 分页查询
     *
     * @param pageParam     页面参数
     * @param courseQueryVo 课程查询 VO
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);
}
