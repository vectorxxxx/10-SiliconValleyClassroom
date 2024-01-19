package xyz.funnyboy.ggkt.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vod.service.VideoVisitorService;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Api(tags = "课程统计")
@RestController
@RequestMapping("/admin/vod/videoVisitor")
@CrossOrigin
public class VideoVisitorController
{

    @Autowired
    private VideoVisitorService videoVisitorService;

    @ApiOperation(value = "获取统计数据")
    @GetMapping(value = {"findCount/{courseId}", "findCount/{courseId}/{startDate}", "findCount/{courseId}/{endDate}", "findCount/{courseId}/{startDate}/{endDate}"})
    public Result findCount(
            @ApiParam(name = "courseId",
                      value = "课程ID",
                      required = true)
            @PathVariable("courseId")
                    Long courseId,

            @ApiParam(name = "startDate",
                      value = "开始日期",
                      required = false)
            @PathVariable(value = "startDate",
                          required = false)
                    String startDate,

            @ApiParam(name = "endDate",
                      value = "结束日期",
                      required = false)
            @PathVariable(value = "endDate",
                          required = false)
                    String endDate) {
        Map<String, Object> map = videoVisitorService.findCount(courseId, startDate, endDate);
        return Result.ok(map);
    }
}

