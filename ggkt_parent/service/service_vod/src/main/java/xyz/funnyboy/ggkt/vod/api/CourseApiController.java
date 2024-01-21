package xyz.funnyboy.ggkt.vod.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.vod.CourseQueryVo;
import xyz.funnyboy.ggkt.vod.service.CourseService;

import java.util.List;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-20 21:50:18
 */
@Api(tags = "课程接口")
@RestController
@RequestMapping("/api/vod/course")
public class CourseApiController
{
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "根据课程ID查询课程")
    @GetMapping("/getInfo/{courseId}")
    public Result getById(
            @ApiParam(name = "courseId",
                      value = "课程ID",
                      required = true)
            @PathVariable
                    Long courseId) {
        return Result.ok(courseService.getInfoById(courseId));
    }

    @ApiOperation(value = "根据课程分类查询课程列表")
    @GetMapping("{subjectId}/{page}/{limit}")
    public Result findPageCourse(
            @ApiParam(name = "subjectId",
                      value = "课程分类ID",
                      required = true)
            @PathVariable
                    Long subjectId,

            @ApiParam(name = "page",
                      value = "页数",
                      required = true)
            @PathVariable
                    Integer page,

            @ApiParam(name = "limit",
                      value = "每页数量",
                      required = true)
            @PathVariable
                    Integer limit) {
        final Page<Course> pageParam = new Page<>(page, limit);
        final CourseQueryVo courseQueryVo = new CourseQueryVo();
        courseQueryVo.setSubjectParentId(subjectId);
        return Result.ok(courseService.findPage(pageParam, courseQueryVo));
    }

    @ApiOperation(value = "根据关键字查询课程")
    @GetMapping("/inner/findByKeyword/{keyword}")
    public List<Course> findByKeyword(
            @ApiParam(name = "keyword",
                      value = "课程关键字",
                      required = true)
            @PathVariable
                    String keyword) {
        return courseService.findByKeyword(keyword);
    }
}
