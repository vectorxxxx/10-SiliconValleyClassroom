package xyz.funnyboy;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-18 21:02:48
 */
public class CosTest
{
    private static COSClient cosClient = createCli();

    private static COSClient createCli() {
        final String accessKey = "AKIDX2W6pRwHiMwLdmplcGm8PWbuAE8EID6y";
        final String secretKey = "vhVmyxmaHvTzQkx462oW9vcBICuTuEOR";
        final String regionName = "ap-shanghai";

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

    private static void putObjectDemo() {
        String bucketName = "ggkt-vectorx-1252510833";
        String key = "test/test.png";
        String localPath = "D:\\workspace-mine\\10-SiliconValleyClassroom\\ggkt_parent\\service\\service_vod\\src\\main\\resources\\images\\test.png";

        // 上传文件
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, new File(localPath));
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
        System.out.println(putObjectResult.getRequestId());
    }

    public static void main(String[] args) {
        putObjectDemo();
    }
}
