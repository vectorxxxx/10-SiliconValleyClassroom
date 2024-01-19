import request from '@/utils/request'

const api_name = '/admin/vod/course'

export default {
  // 分页查询课程
  findPage(page, limit, courseQueryVo) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: courseQueryVo
    })
  }
}
