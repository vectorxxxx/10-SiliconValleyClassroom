package xyz.funnyboy.ggkt.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vod.service.VodService;
import xyz.funnyboy.ggkt.vod.utils.ConstantPropertiesUtil;
import xyz.funnyboy.ggkt.vod.utils.Signature;

import java.util.Random;

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
// @CrossOrigin
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

    @ApiOperation(value = "获取签名")
    @PostMapping("/getSignature")
    public Result getSignature() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_SECRET_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_SECRET_KEY);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        }
        catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            return Result.fail();
        }
    }
}
