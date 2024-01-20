package xyz.funnyboy.ggkt.vod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.model.vod.Course;
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
