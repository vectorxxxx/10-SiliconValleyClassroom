package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.vo.vod.CourseQueryVo;
import xyz.funnyboy.ggkt.vod.mapper.CourseMapper;
import xyz.funnyboy.ggkt.vod.service.CourseService;
import xyz.funnyboy.ggkt.vod.service.SubjectService;
import xyz.funnyboy.ggkt.vod.service.TeacherService;

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
