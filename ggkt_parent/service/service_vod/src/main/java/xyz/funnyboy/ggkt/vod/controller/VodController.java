package xyz.funnyboy.ggkt.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vod.service.VodService;

/**
 * 腾讯云点播
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 22:10:10
 */
@Api(tags = "腾讯云点播")
@RestController
@RequestMapping("/admin/vod")
@CrossOrigin
public class VodController
{
    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传视频")
    @PostMapping("/upload")
    public Result uploadVideo(
            @ApiParam(name = "file",
                      value = "视频文件",
                      required = true)
                    MultipartFile file) {
        final String fileId = vodService.uploadVideo(file);
        return Result.ok(fileId);
    }

    @ApiOperation(value = "删除视频")
    @DeleteMapping("/remove/{videoSourceId}")
    public Result removeVideo(
            @ApiParam(name = "videoSourceId",
                      value = "视频源ID",
                      required = true)
            @PathVariable
                    String videoSourceId) {
        vodService.removeVideo(videoSourceId);
        return Result.ok();
    }
}
