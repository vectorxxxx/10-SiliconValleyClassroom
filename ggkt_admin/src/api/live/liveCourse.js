import request from '@/utils/request'

const api_name = '/admin/live/liveCourse'

export default {
  // 获取分页列表
  getPageList(page, limit) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get'
    })
  },

  // 获取最近的直播
  findLatelyList() {
    return request({
      url: `${api_name}/findLatelyList`,
      method: 'get'
    })
  },

  // 获取
  getById(id) {
    return request({
      url: `${api_name}/getInfo/${id}`,
      method: 'get'
    })
  },

  // 获取直播账号信息
  getLiveCourseAccount(id) {
    return request({
      url: `${api_name}/getLiveCourseAccount/${id}`,
      method: 'get'
    })
  },

  // 新增
  save(liveCourse) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: liveCourse
    })
  },

  // 修改
  updateById(liveCourse) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: liveCourse
    })
  },

  // 删除
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },
  removeRows(idList) {
    return request({
      url: `${api_name}/batchRemove`,
      method: 'delete',
      data: idList
    })
  },

  // 获取直播配置信息
  getCourseConfig(id) {
    return request({
      url: `${api_name}/getCourseConfig/${id}`,
      method: 'get'
    })
  },

  // 修改配置
  updateConfig(liveCourseConfigVo) {
    return request({
      url: `${api_name}/updateConfig`,
      method: 'put',
      data: liveCourseConfigVo
    })
  }
}
