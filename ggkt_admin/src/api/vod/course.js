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
  },
  // 保存课程
  saveCourseInfo(courseFormVo) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: courseFormVo
    })
  },
  // 根据ID查询课程
  getCourseInfoById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },
  // 更新课程
  updateCourseInfo(courseFormVo) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: courseFormVo
    })
  }
}
