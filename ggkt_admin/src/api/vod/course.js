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
  },
  // 根据课程ID获取课程发布信息
  getCoursePublishInfo(courseId) {
    return request({
      url: `${api_name}/getCoursePublishInfo/${courseId}`,
      method: 'get'
    })
  },
  // 发布课程信息
  publishCourseInfo(id) {
    return request({
      url: `${api_name}/publishCourseInfo/${id}`,
      method: 'put'
    })
  },
  // 删除课程
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },

  // 查询全部课程
  findAll() {
    return request({
      url: `${api_name}/findAll`,
      method: 'get'
    })
  }
}
