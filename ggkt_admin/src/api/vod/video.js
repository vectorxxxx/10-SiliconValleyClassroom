import request from '@/utils/request'

const api_name = '/admin/vod/video'

export default {
  // 根据ID获取课时
  getVideoById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },
  // 新增课时
  addVideo(video) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: video
    })
  },
  // 更新课时
  updateVideo(video) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: video
    })
  },
  // 删除课时
  removeVideo(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  }
}
