package xyz.funnyboy.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作Service
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-18 21:21:22
 */
public interface FileService
{
    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link String}
     */
    String upload(MultipartFile file);
}
