package xyz.funnyboy;

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

/**
 * 视频点播测试
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 20:49:15
 */
public class VodTest
{
    private static final String SECRET_ID = "AKIDX2W6pRwHiMwLdmplcGm8PWbuAE8EID6y";
    private static final String SECRET_KEY = "vhVmyxmaHvTzQkx462oW9vcBICuTuEOR";
    private static final String REGION = "ap-shanghai";
    private static final String PROCEDURE = "LongVideoPreset";
    private static final String ENDPOINT = "vod.tencentcloudapi.com";

    public static void main1(String[] args) {
        VodUploadClient client = new VodUploadClient(SECRET_ID, SECRET_KEY);
        VodUploadRequest request = new VodUploadRequest();
        request.setProcedure(PROCEDURE);
        request.setConcurrentUploadNumber(5);
        request.setMediaName("test2.mp4");
        request.setMediaFilePath("D:/workspace-mine/10-SiliconValleyClassroom/ggkt_parent/service/service_vod/src/main/resources/video/test.mp4");
        request.setMediaType("MP4");
        try {
            VodUploadResponse response = client.upload(REGION, request);
            System.out.println(response.getFileId());
        }
        catch (Exception e) {
            // 业务方进行异常处理
            System.out.println("Upload Err: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential(SECRET_ID, SECRET_KEY);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(ENDPOINT);
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(cred, REGION, clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId("3270835015247315115");
            // 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);
            // 输出json格式的字符串回包
            System.out.println(DeleteMediaResponse.toJsonString(resp));
        }
        catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
