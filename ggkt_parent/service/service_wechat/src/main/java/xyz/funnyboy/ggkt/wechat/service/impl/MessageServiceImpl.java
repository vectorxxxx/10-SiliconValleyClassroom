package xyz.funnyboy.ggkt.wechat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.funnyboy.ggkt.client.course.CourseFeignClient;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.wechat.service.MessageService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService
{

    @Autowired
    private CourseFeignClient courseFeignClient;

    /**
     * 接收消息
     *
     * @param param 参数
     * @return {@link String}
     */
    @Override
    public String receiveMessage(Map<String, String> param) {
        String content = "";
        try {
            // MsgType	消息类型，普通文本为text，事件推送为event
            final String msgType = param.get("MsgType");
            if ("text".equals(msgType)) {
                content = this.search(param);
            }
            else if ("event".equals(msgType)) {
                // Event	事件类型，subscribe(订阅)、unsubscribe(取消订阅)
                final String event = param.get("Event");
                final String eventKey = param.get("EventKey");
                // 关注
                if ("subscribe".equals(event)) {
                    content = this.subscribe(param);
                }
                // 取消关注
                else if ("unsubscribe".equals(event)) {
                    content = this.unsubscribe(param);
                }
                // 关于我们
                else if ("CLICK".equals(event) && "aboutUs".equals(eventKey)) {
                    content = this.aboutUs(param);
                }
                else {
                    content = "success";
                }
            }
            else {
                content = "success";
            }
        }
        catch (Exception e) {
            log.error("接收消息失败：" + e.getMessage(), e);
        }
        return content;
    }

    /**
     * 关于我们
     *
     * @param param 参数
     * @return {@link String}
     */
    private String aboutUs(Map<String, String> param) {
        final StringBuilder content = new StringBuilder();
        content
                .append("硅谷课堂现开设Java、HTML5前端+全栈、大数据、")
                .append("全链路UI/UE设计、人工智能、大数据运维+Python自动化、")
                .append("Android+HTML5混合开发等多门课程；同时，通过视频分享、")
                .append("谷粒学苑在线课堂、大厂学苑直播课堂等多种方式，")
                .append("满足了全国编程爱好者对多样化学习场景的需求，")
                .append("已经为行业输送了大量IT技术人才。");
        return this.text(param, content.toString());
    }

    /**
     * 退订
     *
     * @param param 参数
     * @return {@link String}
     */
    private String unsubscribe(Map<String, String> param) {
        return "success";
    }

    /**
     * 订阅
     *
     * @param param 参数
     * @return {@link String}
     */
    private String subscribe(Map<String, String> param) {
        return this.text(param, "感谢您关注“硅谷课堂”，可以根据关键字搜索您想看的视频教程，如：JAVA基础、Spring boot、大数据等");
    }

    /**
     * 回复文本
     *
     * @param param   参数
     * @param content 内容
     * @return {@link StringBuffer} 返回格式如下
     * <p>
     * <pre>
     * {@literal
     *  <xml>
     *      <ToUserName><![CDATA[toUser]]></ToUserName>
     *      <FromUserName><![CDATA[fromUser]]></FromUserName>
     *      <CreateTime>1348831860</CreateTime>
     *      <MsgType><![CDATA[text]]></MsgType>
     *      <Content><![CDATA[this is a test]]></Content>
     *      <MsgId>1234567890123456</MsgId>
     *      <MsgDataId>xxxx</MsgDataId>
     *      <Idx>xxxx</Idx>
     * </xml>
     * }
     * </pre>
     */
    private String text(Map<String, String> param, String content) {
        // 准备数据
        final String fromUserName = param.get("FromUserName");
        final String toUserName = param.get("ToUserName");
        // 单位为秒，不是毫秒
        final Long createTime = new Date().getTime() / 1000;
        final String msgType = "text";

        // 拼接xml
        StringBuilder text = new StringBuilder();
        text.append("<xml>");
        text.append("<ToUserName><![CDATA[%s]]></ToUserName>");
        text.append("<FromUserName><![CDATA[%s]]></FromUserName>");
        text.append("<CreateTime><![CDATA[%s]]></CreateTime>");
        text.append("<MsgType><![CDATA[%s]]></MsgType>");
        text.append("<Content><![CDATA[%s]]></Content>");
        text.append("</xml>");

        // 返回xml
        // 注意fromUserName和toUserName位置要对调，发送方变为接收方，接收方变为发送方，攻守易形了
        final String xml = String.format(text.toString(), fromUserName, toUserName, createTime, msgType, content);
        log.info("回复消息：{}", xml);
        return xml;
    }

    /**
     * 处理关键字搜索事件
     * <p>
     * 图文消息个数；当用户发送文本、图片、语音、视频、图文、地理位置这六种消息时，开发者只能回复1条图文消息；其余场景最多可回复8条图文消息
     *
     * @param param 参数
     * @return {@link String} 格式如下：
     *
     * <pre>
     *     {@literal
     * <xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>
     *   <FromUserName><![CDATA[fromUser]]></FromUserName>
     *   <CreateTime>12345678</CreateTime>
     *   <MsgType><![CDATA[news]]></MsgType>
     *   <ArticleCount>1</ArticleCount>
     *   <Articles>
     *     <item>
     *       <Title><![CDATA[title1]]></Title>
     *       <Description><![CDATA[description1]]></Description>
     *       <PicUrl><![CDATA[picurl]]></PicUrl>
     *       <Url><![CDATA[url]]></Url>
     *     </item>
     *   </Articles>
     * </xml>
     *     }
     * </pre>
     */
    private String search(Map<String, String> param) {
        // 准备数据
        final String fromUserName = param.get("FromUserName");
        final String toUserName = param.get("ToUserName");
        final String content = param.get("Content");
        final Long createTime = new Date().getTime() / 1000;
        final String msgType = "news";

        // 查询关键字
        log.info("搜索内容：" + content);
        final List<Course> courseList = courseFeignClient.findByKeyword(content);
        if (CollectionUtils.isEmpty(courseList)) {
            log.info("没有找到相关课程");
            return this.text(param, "没有找到相关课程，请重新搜索");
        }

        // 随机返回一个课程
        final Course course = courseList.get(new Random().nextInt(courseList.size()));
        StringBuilder itemSB = new StringBuilder();
        itemSB.append("<item>");
        itemSB.append("<Title><![CDATA[%s]]></Title>");
        itemSB.append("<Description><![CDATA[%s]]></Description>");
        itemSB.append("<PicUrl><![CDATA[%s]]></PicUrl>");
        itemSB.append("<Url><![CDATA[%s]]></Url>");
        itemSB.append("</item>");
        // final String item = String.format(itemSB.toString(), course.getTitle(), course.getTitle(), course.getCover(), course.getCover());
        final String item = String.format(itemSB.toString(), course.getTitle(), course
                .getParam()
                .getOrDefault("description", ""), course.getCover(), course.getCover());

        // 组装xml
        StringBuilder textSB = new StringBuilder();
        textSB.append("<xml>");
        textSB.append("<ToUserName><![CDATA[%s]]></ToUserName>");
        textSB.append("<FromUserName><![CDATA[%s]]></FromUserName>");
        textSB.append("<CreateTime><![CDATA[%s]]></CreateTime>");
        textSB.append("<MsgType><![CDATA[%s]]></MsgType>");
        textSB.append("<ArticleCount><![CDATA[%s]]></ArticleCount>");
        textSB.append("<Articles>");
        textSB.append(item);
        textSB.append("</Articles>");
        textSB.append("</xml>");

        // 返回xml
        // 注意fromUserName和toUserName位置要对调，发送方变为接收方，接收方变为发送方，攻守易形了
        final String xml = String.format(textSB.toString(), fromUserName, toUserName, createTime, msgType, 1);
        log.info("返回内容：{}", xml);
        return xml;
    }
}
