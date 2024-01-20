package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.model.vod.CourseDescription;
import xyz.funnyboy.ggkt.vo.vod.CourseFormVo;
import xyz.funnyboy.ggkt.vo.vod.CoursePublishVo;
import xyz.funnyboy.ggkt.vo.vod.CourseQueryVo;
import xyz.funnyboy.ggkt.vod.mapper.CourseMapper;
import xyz.funnyboy.ggkt.vod.service.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService
{

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    /**
     * 分页查询
     *
     * @param pageParam     页面参数
     * @param courseQueryVo 课程查询 VO
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        final String title = courseQueryVo.getTitle();
        final Long subjectParentId = courseQueryVo.getSubjectParentId();
        final Long subjectId = courseQueryVo.getSubjectId();
        final Long teacherId = courseQueryVo.getTeacherId();

        // 查询条件
        final LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<Course>()
                .like(!StringUtils.isEmpty(title), Course::getTitle, title)
                .eq(!StringUtils.isEmpty(subjectParentId), Course::getSubjectParentId, subjectParentId)
                .eq(!StringUtils.isEmpty(subjectId), Course::getSubjectId, subjectId)
                .eq(!StringUtils.isEmpty(teacherId), Course::getTeacherId, teacherId);

        // 分页查询
        final Page<Course> pageModel = baseMapper.selectPage(pageParam, queryWrapper);
        final long totalCount = pageModel.getTotal();
        final long totalPage = pageModel.getPages();
        final List<Course> records = pageModel.getRecords();

        // 封装结果
        records.forEach(this::getNameById);

        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", records);
        return map;
    }

    /**
     * 保存课程信息
     *
     * @param courseFormVo 课程表单 VO
     * @return {@link Long}
     */
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        // 保存课程信息
        final Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.insert(course);

        // 保存课程描述信息
        final String description = courseFormVo.getDescription();
        final CourseDescription courseDescription = new CourseDescription();
        courseDescription.setCourseId(course.getId());
        courseDescription.setDescription(description);
        courseDescriptionService.save(courseDescription);

        return course.getId();
    }

    /**
     * 按 ID 获取课程信息
     *
     * @param id 编号
     * @return {@link CourseFormVo}
     */
    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        // 获取课程信息
        final Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        // 获取课程简介信息
        final CourseDescription courseDescription = courseDescriptionService.getById(id);

        // 组装结果
        final CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);
        if (courseDescription != null) {
            final String description = courseDescription.getDescription();
            courseFormVo.setDescription(description);
        }
        return courseFormVo;
    }

    /**
     * 更新课程信息
     *
     * @param courseFormVo 课程表单 VO
     */
    @Override
    public void updateCourseInfo(CourseFormVo courseFormVo) {
        // 更新课程基本信息
        final Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        // 更新课程简介信息
        final CourseDescription courseDescription = new CourseDescription();
        courseDescription.setCourseId(course.getId());
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    /**
     * 获取课程发布信息
     *
     * @param id 编号
     * @return {@link CoursePublishVo}
     */
    @Override
    public CoursePublishVo getCoursePublishInfo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    /**
     * 发布课程
     *
     * @param id 编号
     */
    @Override
    public void publishCourseInfo(Long id) {
        final Course course = new Course();
        course.setId(id);
        course.setPublishTime(new Date());
        course.setStatus(1);
        baseMapper.updateById(course);
    }

    /**
     * 按 ID 删除课程信息
     *
     * @param id 编号
     */
    @Override
    public void removeCourseInfoById(Long id) {
        // 删除小节信息
        videoService.removeVideoByCourseId(id);
        // 删除章节信息
        chapterService.removeByCourseId(id);
        // 删除课程简介信息
        courseDescriptionService.removeByCourseId(id);
        // 删除课程基本信息
        baseMapper.deleteById(id);
    }

    /**
     * 按关键字查找
     *
     * @param keyword 关键词
     * @return {@link List}<{@link Course}>
     */
    @Override
    public List<Course> findByKeyword(String keyword) {
        final List<Course> courseList = baseMapper.selectList(new LambdaQueryWrapper<Course>().like(Course::getTitle, keyword));
        courseList.forEach(course -> course
                .getParam()
                .put("description", courseDescriptionService.getDescriptionByCourseId(course.getId())));
        return courseList;
    }

    /**
     * 按 ID 获取名称
     *
     * @param course 课程
     */
    private void getNameById(Course course) {
        final Map<String, Object> param = course.getParam();
        // 讲师名称
        final Long teacherId = course.getTeacherId();
        if (teacherId != null) {
            param.put("teacherName", teacherService
                    .getById(teacherId)
                    .getName());
        }

        // 二级分类名称
        final Long subjectId = course.getSubjectId();
        if (teacherId != null) {
            param.put("subjectName", subjectService
                    .getById(subjectId)
                    .getTitle());
        }

        // 三级分类名称
        final Long subjectParentId = course.getSubjectParentId();
        if (subjectParentId != null) {
            param.put("subjectParentName", subjectService
                    .getById(subjectParentId)
                    .getTitle());
        }
    }
}
