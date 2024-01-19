package xyz.funnyboy.ggkt.vod.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.vod.CourseQueryVo;
import xyz.funnyboy.ggkt.vod.service.CourseService;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/admin/vod/course")
@CrossOrigin
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "分页查询课程")
    @GetMapping("/{page}/{limit}")
    public Result index(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    Long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    Long limit,

            @ApiParam(name = "courseQueryVo",
                      value = "查询对象",
                      required = false)
                    CourseQueryVo courseQueryVo) {
        final Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.findPage(pageParam, courseQueryVo);
        return Result.ok(map);
    }
}

