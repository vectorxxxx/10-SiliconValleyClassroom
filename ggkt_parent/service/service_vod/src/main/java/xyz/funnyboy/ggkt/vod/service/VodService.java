package xyz.funnyboy.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 视频点播Service
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 20:38:10
 */
public interface VodService
{
    /**
     * 上传视频
     *
     * @param file 文件
     * @return {@link String}
     */
    String uploadVideo(MultipartFile file);

    /**
     * 移除视频
     *
     * @param videoSourceId 视频源 ID
     */
    void removeVideo(String videoSourceId);

    /**
     * 获取视频播放凭证
     *
     * @param videoId 视频 ID
     * @return {@link Map}<{@link String},{@link Object}>
     */
    Map<String, Object> getPlayAuth(Long videoId);
}
