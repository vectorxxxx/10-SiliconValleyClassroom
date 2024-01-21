package xyz.funnyboy.ggkt.user.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.funnyboy.ggkt.model.user.UserInfo;
import xyz.funnyboy.ggkt.swagger.utils.JwtHelper;
import xyz.funnyboy.ggkt.user.service.UserInfoService;
import xyz.funnyboy.ggkt.user.utils.ConstantPropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 14:49:25
 */
@Api(tags = "微信授权登录接口")
@Controller
@RequestMapping("/api/user/wechat")
@Slf4j
public class WechatController
{
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WxMpService wxMpService;

    @ApiOperation(value = "授权")
    @GetMapping("/authorize")
    public String authorize(
            @ApiParam(name = "returnUrl",
                      value = "授权回调地址",
                      required = true)
            @RequestParam("returnUrl")
                    String returnUrl, HttpServletRequest request) {
        // 构造网页授权url
        final String redirectURL = wxMpService
                .getOAuth2Service()
                .buildAuthorizationUrl(ConstantPropertiesUtil.USER_INFO_URL, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl.replace("guiguketan", "#")));
        log.info("【微信网页授权】url: " + redirectURL);
        return "redirect:" + redirectURL;
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/userInfo")
    public String userInfo(
            @ApiParam(name = "code",
                      value = "授权码",
                      required = true)
            @RequestParam("code")
                    String code,

            @ApiParam(name = "state",
                      value = "授权回调地址",
                      required = true)
            @RequestParam("state")
                    String returnUrl) throws Exception {
        // 1、获得access token
        final WxOAuth2AccessToken wxOAuth2AccessToken = wxMpService
                .getOAuth2Service()
                .getAccessToken(code);
        final String openId = wxOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openId: " + openId);

        // 2、获得用户基本信息
        final WxOAuth2UserInfo wxOAuth2UserInfo = wxMpService
                .getOAuth2Service()
                .getUserInfo(wxOAuth2AccessToken, null);
        log.info("【微信网页授权】wxOAuth2UserInfo: " + wxOAuth2UserInfo);

        // 3、保存用户信息
        UserInfo userInfo = userInfoService.getByOpenId(openId);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setOpenId(openId);
            userInfo.setUnionId(wxOAuth2UserInfo.getUnionId());
            userInfo.setNickName(wxOAuth2UserInfo.getNickname());
            userInfo.setAvatar(wxOAuth2UserInfo.getHeadImgUrl());
            userInfo.setProvince(wxOAuth2UserInfo.getProvince());
            userInfo.setSex(wxOAuth2UserInfo.getSex());
            userInfoService.save(userInfo);
        }

        // 4、Jwt生成token
        final String token = JwtHelper.createToken(userInfo.getId(), userInfo.getNickName());
        if (returnUrl.contains("?")) {
            returnUrl += "&token=" + token;
        }
        else {
            returnUrl += "?token=" + token;
        }
        log.info("【微信网页授权】returnUrl: " + returnUrl);
        return "redirect:" + returnUrl;
    }
}
