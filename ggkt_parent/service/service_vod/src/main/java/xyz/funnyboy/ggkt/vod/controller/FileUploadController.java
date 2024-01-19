package xyz.funnyboy.ggkt.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vod.service.FileService;

/**
 * 文件上传Controller
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-18 21:42:23
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
// @CrossOrigin
public class FileUploadController
{
    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public Result uploadFile(MultipartFile file) {
        final String url = fileService.upload(file);
        return Result
                .ok(url)
                .message("文件上传成功");
    }
}
