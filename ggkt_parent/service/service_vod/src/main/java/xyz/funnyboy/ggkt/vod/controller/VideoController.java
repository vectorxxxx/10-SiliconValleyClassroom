package xyz.funnyboy.ggkt.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.model.vod.Video;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vod.service.VideoService;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Api(tags = "课时管理")
@RestController
@RequestMapping("/admin/vod/video")
@CrossOrigin
public class VideoController
{
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(
            @ApiParam(name = "id",
                      value = "课时ID",
                      required = true)
            @PathVariable
                    Long id) {
        return Result.ok(videoService.getById(id));
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(
            @ApiParam(name = "video",
                      value = "新增对象",
                      required = true)
            @RequestBody
                    Video video) {
        videoService.save(video);
        return Result.ok();
    }

    @ApiOperation(value = "更新")
    @PutMapping("update")
    public Result update(
            @ApiParam(name = "video",
                      value = "更新对象",
                      required = true)
            @RequestBody
                    Video video) {
        videoService.updateById(video);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(
            @ApiParam(name = "id",
                      value = "课时ID",
                      required = true)
            @PathVariable
                    Long id) {
        videoService.removeById(id);
        return Result.ok();
    }
}

