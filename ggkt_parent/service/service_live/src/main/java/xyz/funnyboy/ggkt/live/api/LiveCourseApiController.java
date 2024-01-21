package xyz.funnyboy.ggkt.live.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.live.service.LiveCourseService;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.swagger.utils.AuthContextHolder;

import java.util.Map;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-22 02:53:42
 */
@Api(tags = "公众号直播对接接口")
@RestController
@RequestMapping("api/live/liveCourse")
@Slf4j
public class LiveCourseApiController
{
    @Autowired
    private LiveCourseService liveCourseService;

    @ApiOperation(value = "获取用户access_token")
    @GetMapping("getPlayAuth/{id}")
    public Result<JSONObject> getPlayAuth(
            @ApiParam(name = "id",
                      value = "id",
                      required = true)
            @PathVariable("id")
                    Long id) {
        JSONObject object = liveCourseService.getPlayAuth(id, AuthContextHolder.getUserId());
        log.info("获取用户access_token：{}", JSON.toJSONString(object));
        return Result.ok(object);
    }

    @ApiOperation(value = "根据课程ID查询课程")
    @GetMapping("/getInfo/{courseId}")
    public Result getInfo(
            @ApiParam(name = "courseId",
                      value = "课程ID",
                      required = true)
            @PathVariable
                    Long courseId) {
        Map<String, Object> map = liveCourseService.getInfoById(courseId);
        return Result.ok(map);
    }
}
