package xyz.funnyboy.ggkt.swagger.utils;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * base64 实用程序
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/22
 */
public class Base64Util
{
    /**
     * 使用Base64进行加密
     *
     * @param content 密文
     * @return
     */
    @SneakyThrows
    public static String base64Encode(String content) {
        return Base64
                .getEncoder()
                .encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 使用Base64进行解密
     *
     * @param content
     * @return
     */
    @SneakyThrows
    public static String base64Decode(String content) {
        return new String(Base64
                .getDecoder()
                .decode(content), StandardCharsets.UTF_8);
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[1-9][0-9]*$");
        return pattern
                .matcher(str)
                .matches();

    }

    public static void main(String[] args) {
        System.out.println(UrlUtil.getParam("http://vectorx.free.idcfengye.com/#/liveInfo/7?recommend=MQ==", "recommend"));
        System.out.println(Base64Util.base64Encode("q2"));
        System.out.println(Base64Util.base64Decode("MQ=="));
    }
}
