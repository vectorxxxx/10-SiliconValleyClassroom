package xyz.funnyboy.ggkt.client.course;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.model.vod.Teacher;

import java.util.List;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-20 21:53:25
 */
@Component
@FeignClient(value = "service-vod")
public interface CourseFeignClient
{
    /**
     * 根据关键字查询课程
     *
     * @param keyword 关键词
     * @return {@link List}<{@link Course}>
     */
    @GetMapping("/api/vod/course/inner/findByKeyword/{keyword}")
    List<Course> findByKeyword(
            @PathVariable("keyword")
                    String keyword);

    @GetMapping("/api/vod/course/inner/getById/{courseId}")
    Course getById(
            @PathVariable("courseId")
                    Long courseId);

    @GetMapping("/admin/vod/teacher/inner/getTeacher/{id}")
    Teacher getTeacherInfo(
            @PathVariable("id")
                    Long id);
}
