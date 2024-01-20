package xyz.funnyboy.ggkt.wechat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.wechat.service.MessageService;
import xyz.funnyboy.ggkt.wechat.utils.SHA1;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号消息接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-20 17:04:50
 */
@Api(tags = "微信公众号消息接口")
@RestController
@RequestMapping("/api/wechat/message")
@Slf4j
public class MessageController
{
    private static final String token = "ggkt";

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "推送支付消息")
    @GetMapping("/pushPayMessage")
    public Result pushPayMessage() throws WxErrorException {
        messageService.pushPayMessage(1L);
        return Result.ok();
    }

    @ApiOperation(value = "接收微信服务器发送来的消息")
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {
        // final WxMpXmlMessage wxMpXmlMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        // System.out.println(JSONObject.toJSONString(wxMpXmlMessage));
        final Map<String, String> xml = this.parseXml(request);
        xml.forEach((key, value) -> log.info("【{}】:【{}】", key, value));
        return messageService.receiveMessage(xml);
    }

    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();

        // 获取微信服务器发送来的消息
        try (final ServletInputStream inputStream = request.getInputStream()) {
            // 解析消息
            new SAXReader()
                    .read(inputStream)
                    .getRootElement()
                    .elements()
                    .forEach(element -> map.put(element.getName(), element.getText()));
        }
        return map;
    }

    @ApiOperation(value = "服务器有效性验证")
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        final String signature = request.getParameter("signature");
        final String timestamp = request.getParameter("timestamp");
        final String nonce = request.getParameter("nonce");
        final String echostr = request.getParameter("echostr");
        log.info("signature: {}, nonce: {}, echostr: {}, timestamp: {}", signature, nonce, echostr, timestamp);
        if (this.checkSignature(signature, timestamp, nonce)) {
            log.info("token ok");
            return echostr;
        }
        log.error("token error");
        return echostr;
    }

    /**
     * 检查签名
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return boolean
     */
    private boolean checkSignature(String signature, String timestamp, String nonce) {
        // 1）将token、timestamp、nonce三个参数进行字典序排序
        final String[] str = {token, timestamp, nonce};
        Arrays.sort(str);

        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        final String spliceStr = String.join("", str);
        final String encodeStr = SHA1.encode(spliceStr);

        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return signature.equals(encodeStr);
    }
}
