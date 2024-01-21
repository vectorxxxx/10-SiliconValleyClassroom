package xyz.funnyboy.ggkt.vod.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.vod.service.VodService;

import java.util.Map;

/**
 * 腾讯视频点播
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 16:29:43
 */
@Api(tags = "腾讯视频点播")
@RestController
@RequestMapping("/api/vod")
public class VodApiController
{
    @Autowired
    private VodService vodService;

    @ApiOperation(value = "获取视频播放凭证")
    @GetMapping("getPlayAuth/{videoId}")
    public Map<String, Object> getPlayAuth(
            @ApiParam(name = "videoId",
                      value = "视频ID",
                      required = true)
            @PathVariable
                    Long videoId) {
        return vodService.getPlayAuth(videoId);
    }
}
