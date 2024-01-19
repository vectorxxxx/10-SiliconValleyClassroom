package xyz.funnyboy.ggkt.vod.service.impl;

import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.swagger.exception.GgktException;
import xyz.funnyboy.ggkt.vod.service.VodService;
import xyz.funnyboy.ggkt.vod.utils.ConstantPropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * 视频点播Service
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 20:38:32
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService
{

    /**
     * 上传视频
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            // 获取文件名称
            final String originalFilename = file.getOriginalFilename();
            // 获取文件类型
            final String fileType = Objects
                    .requireNonNull(originalFilename)
                    .substring(originalFilename.lastIndexOf(".") + 1);

            // 获取绝对路径
            final String absolutePath = getMediaFilePath(file, fileType);

            // 初始化上传请求
            VodUploadRequest request = new VodUploadRequest();
            request.setProcedure(ConstantPropertiesUtil.PROCEDURE);
            request.setMediaName(originalFilename);
            request.setMediaFilePath(absolutePath);
            request.setMediaType(fileType.toUpperCase(Locale.ROOT));

            // 初始化上传客户端
            VodUploadClient client = new VodUploadClient(ConstantPropertiesUtil.ACCESS_SECRET_ID, ConstantPropertiesUtil.ACCESS_SECRET_KEY);
            VodUploadResponse response = client.upload(ConstantPropertiesUtil.REGION, request);

            // 打印响应信息
            log.info("RequestId: " + response.getRequestId());
            log.info("FileId: " + response.getFileId());
            log.info("CoverUrl: " + response.getCoverUrl());
            log.info("MediaUrl: " + response.getMediaUrl());

            // 返回文件 ID
            return response.getFileId();
        }
        catch (Exception e) {
            log.error("视频上传失败：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取媒体文件路径
     * <p>
     * 由于腾讯云VOD不像阿里云VOD那样支持上传文件流的形式，这里需要先对文件进行落地
     *
     * @param file     文件
     * @param fileType 文件类型
     * @return {@link String} 绝对路径
     * @throws IOException ioexception
     */
    private String getMediaFilePath(MultipartFile file, String fileType) throws IOException {
        // 获取根目录
        final String rootPath = "D:/workspace-mine/10-SiliconValleyClassroom/ggkt_parent";
        // 创建视频文件夹
        final File videoPath = new File(Paths
                .get(rootPath, "video")
                .toString());
        if (!videoPath.exists()) {
            videoPath.mkdirs();
        }

        // 生成文件名
        final String newFilename = UUID
                .randomUUID()
                .toString()
                .replaceAll("-", "") + "." + fileType;
        // 创建文件
        final File videoFile = new File(Paths
                .get(rootPath, "video")
                .toString(), newFilename);

        // 写入文件
        file.transferTo(videoFile);

        // 返回绝对路径
        return videoFile.getAbsolutePath();
    }

    /**
     * 移除视频
     *
     * @param videoSourceId 视频源 ID
     */
    @Override
    public void removeVideo(String videoSourceId) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential(ConstantPropertiesUtil.ACCESS_SECRET_ID, ConstantPropertiesUtil.ACCESS_SECRET_KEY);

            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(ConstantPropertiesUtil.ENDPOINT);

            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(cred, ConstantPropertiesUtil.REGION, clientProfile);

            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(videoSourceId);

            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);

            // 输出json格式的字符串回包
            final String jsonString = DeleteMediaResponse.toJsonString(resp);
            log.info("删除视频成功：" + jsonString);
        }
        catch (TencentCloudSDKException e) {
            log.error("删除视频失败：" + e.getMessage(), e);
            throw new GgktException(20001, "删除视频失败：" + e.getMessage());
        }
    }
}
