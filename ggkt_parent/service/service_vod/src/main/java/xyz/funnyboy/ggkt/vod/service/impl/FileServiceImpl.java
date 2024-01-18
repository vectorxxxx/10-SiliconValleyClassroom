package xyz.funnyboy.ggkt.vod.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.vod.service.FileService;
import xyz.funnyboy.ggkt.vod.utils.ConstantPropertiesUtil;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-18 21:23:58
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService
{
    private COSClient createCli() {
        final String accessKey = ConstantPropertiesUtil.ACCESS_SECRET_ID;
        final String secretKey = ConstantPropertiesUtil.ACCESS_SECRET_KEY;
        final String regionName = ConstantPropertiesUtil.REGION;

        // 初始化用户身份信息(secretId, secretKey)
        final COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);

        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        final Region region = new Region(regionName);
        final ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String upload(MultipartFile file) {
        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建
        // 详细代码参见本页：简单操作 -> 创建 COSClient
        COSClient cosClient = null;
        try {
            // 1、基础数据
            final String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
            final String key = new DateTime().toString("yyyy/MM/dd") + "/" + UUID
                    .randomUUID()
                    .toString()
                    .replaceAll("-", "") + file.getOriginalFilename();
            final InputStream content = file.getInputStream();

            // 2、对象元数据
            final ObjectMetadata objectMetadata = new ObjectMetadata();
            // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
            // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
            objectMetadata.setContentLength(file.getSize());

            // 3、创建上传对象请求
            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, content, objectMetadata);

            // 4、上传文件
            cosClient = createCli();
            cosClient.putObject(putObjectRequest);

            // 5、返回文件的完整路径
            final String url = "https://" + bucketName + ".cos." + ConstantPropertiesUtil.REGION + ".myqcloud.com/" + key;
            log.info("上传文件成功：" + url);
            return url;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        finally {
            if (cosClient != null) {
                cosClient.shutdown();
            }
        }
    }
}
