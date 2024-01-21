import request from "@/utils/request";

const api_name = "/api/vod";

export default {

  // 获取播放凭证
  getPlayAuth(videoId) {
    return request({
      url: `${api_name}/getPlayAuth/${videoId}`,
      method: "get"
    });
  }
};
