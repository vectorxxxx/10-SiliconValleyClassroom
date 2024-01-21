package xyz.funnyboy.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.vo.vod.CourseFormVo;
import xyz.funnyboy.ggkt.vo.vod.CoursePublishVo;
import xyz.funnyboy.ggkt.vo.vod.CourseQueryVo;

import java.util.List;
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

    /**
     * 保存课程信息
     *
     * @param courseFormVo 课程表单 VO
     * @return {@link Long}
     */
    Long saveCourseInfo(CourseFormVo courseFormVo);

    /**
     * 按 ID 获取课程信息
     *
     * @param id 编号
     * @return {@link CourseFormVo}
     */
    CourseFormVo getCourseInfoById(Long id);

    /**
     * 更新课程信息
     *
     * @param courseFormVo 课程表单 VO
     */
    void updateCourseInfo(CourseFormVo courseFormVo);

    /**
     * 获取课程发布信息
     *
     * @param id 编号
     * @return {@link CoursePublishVo}
     */
    CoursePublishVo getCoursePublishInfo(Long id);

    /**
     * 发布课程
     *
     * @param id 编号
     */
    void publishCourseInfo(Long id);

    /**
     * 按 ID 删除课程信息
     *
     * @param id 编号
     */
    void removeCourseInfoById(Long id);

    /**
     * 按关键字查找
     *
     * @param keyword 关键词
     * @return {@link List}<{@link Course}>
     */
    List<Course> findByKeyword(String keyword);

    /**
     * 按 ID 获取信息
     *
     * @param courseId 课程编号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getInfoById(Long courseId);
}
