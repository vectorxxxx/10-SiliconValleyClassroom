package xyz.funnyboy.ggkt.wechat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.swagger.utils.AuthContextHolder;
import xyz.funnyboy.ggkt.swagger.utils.Base64Util;
import xyz.funnyboy.ggkt.vo.wechat.WxJsapiSignatureVo;

/**
 * 微信分享接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-22 04:42:13
 */
@Api(tags = "微信分享接口")
@RestController
@RequestMapping("/api/wechat/share")
@Slf4j
public class ShareController
{
    @Autowired
    private WxMpService wxMpService;

    @ApiOperation(value = "获取签名")
    @GetMapping("getSignature")
    public Result getSignature(
            @RequestParam("url")
                    String url) throws WxErrorException {
        // 获取当前url
        String currentUrl = url.replace("guiguketan", "#");
        log.info("当前url: {}", currentUrl);

        // 获取签名
        final WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(currentUrl);

        // 返回签名
        final WxJsapiSignatureVo wxJsapiSignatureVo = new WxJsapiSignatureVo();
        BeanUtils.copyProperties(jsapiSignature, wxJsapiSignatureVo);
        wxJsapiSignatureVo.setUserEedId(Base64Util.base64Encode(AuthContextHolder.getUserId() + ""));
        log.info("wxJsapiSignatureVo: {}", wxJsapiSignatureVo);

        return Result.ok(wxJsapiSignatureVo);
    }
}
