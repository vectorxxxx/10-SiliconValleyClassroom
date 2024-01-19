import request from '@/utils/request'

const api_name = '/admin/vod/teacher'

export default {
  // 讲师列表
  pageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/findQueryPage/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  // 删除讲师
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },
  // 添加讲师
  addTeacher(teacher) {
    return request({
      url: `${api_name}/addTeacher`,
      method: 'post',
      data: teacher
    })
  },
  // 根据讲师id查询讲师
  getById(id) {
    return request({
      url: `${api_name}/getTeacher/${id}`,
      method: 'get'
    })
  },
  // 修改讲师
  updateTeacher(teacher) {
    return request({
      url: `${api_name}/updateTeacher`,
      method: 'put',
      data: teacher
    })
  },
  // 批量删除讲师
  removeBatch(ids) {
    return request({
      url: `${api_name}/removeBatch`,
      method: 'delete',
      data: ids
    })
  },
  // 所有讲师列表
  findAll() {
    return request({
      url: `${api_name}/findAll`,
      method: 'get'
    })
  }
}
