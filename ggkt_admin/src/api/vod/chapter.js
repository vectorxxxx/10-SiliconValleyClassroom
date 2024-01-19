import request from '@/utils/request'

const api_name = '/admin/vod/chapter'

export default {
  // 嵌套查询章节列表
  getNestedTreeList(courseId) {
    return request({
      url: `${api_name}/getNestedTreeList/${courseId}`,
      method: 'get'
    })
  },
  // 新增章节
  addChapter(chapter) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: chapter
    })
  },
  // 根据ID获取章节
  getChapterById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },
  // 更新章节
  updateChapter(chapter) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: chapter
    })
  },
  // 删除章节
  removeChapterById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  }
}
