import request from '@/utils/request'

const api_name = '/admin/order/orderInfo'

export default {
  // 分页查询订单
  getPageList(page, limit, orderInfoQueryVo) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: orderInfoQueryVo
    })
  }
}
