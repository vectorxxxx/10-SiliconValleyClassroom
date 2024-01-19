import request from '@/utils/request'

const api_name = '/admin/vod'

export default {
  // 删除视频
  removeVideo(videoSourceId) {
    return request({
      url: `${api_name}/remove/${videoSourceId}`,
      method: 'delete'
    })
  }
}
