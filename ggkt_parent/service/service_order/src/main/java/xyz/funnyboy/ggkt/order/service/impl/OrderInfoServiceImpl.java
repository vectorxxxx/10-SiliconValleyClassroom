package xyz.funnyboy.ggkt.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.ggkt.model.order.OrderDetail;
import xyz.funnyboy.ggkt.model.order.OrderInfo;
import xyz.funnyboy.ggkt.order.mapper.OrderInfoMapper;
import xyz.funnyboy.ggkt.order.service.OrderDetailService;
import xyz.funnyboy.ggkt.order.service.OrderInfoService;
import xyz.funnyboy.ggkt.vo.order.OrderInfoQueryVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService
{

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 分页查询订单
     *
     * @param pageParam        页面参数
     * @param orderInfoQueryVo 订单信息查询VO
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @Override
    public Map<String, Object> findPageOrderInfo(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {
        final Long userId = orderInfoQueryVo.getUserId();
        final String nickName = orderInfoQueryVo.getNickName();
        final String phone = orderInfoQueryVo.getPhone();
        final Integer orderStatus = orderInfoQueryVo.getOrderStatus();
        final String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        final String province = orderInfoQueryVo.getProvince();
        final String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        final String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();

        // 查询条件
        final LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<OrderInfo>()
                .eq(!StringUtils.isEmpty(userId), OrderInfo::getUserId, userId)
                .like(!StringUtils.isEmpty(nickName), OrderInfo::getNickName, nickName)
                .like(!StringUtils.isEmpty(phone), OrderInfo::getPhone, phone)
                .eq(!StringUtils.isEmpty(orderStatus), OrderInfo::getOrderStatus, orderStatus)
                .like(!StringUtils.isEmpty(outTradeNo), OrderInfo::getOutTradeNo, outTradeNo)
                .like(!StringUtils.isEmpty(province), OrderInfo::getProvince, province)
                .ge(!StringUtils.isEmpty(createTimeBegin), OrderInfo::getCreateTime, createTimeBegin)
                .le(!StringUtils.isEmpty(createTimeEnd), OrderInfo::getCreateTime, createTimeEnd)
                .orderByDesc(OrderInfo::getCreateTime);

        // 查询订单列表
        final Page<OrderInfo> pages = baseMapper.selectPage(pageParam, queryWrapper);
        final List<OrderInfo> records = pages.getRecords();
        final long totalCount = pages.getTotal();
        final long pageCount = pages.getPages();

        //订单里面包含详情内容，封装详情数据，根据订单id查询详情
        records.forEach(this::getOrderDetail);

        // 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("total", totalCount);
        map.put("pageCount", pageCount);
        map.put("records", records);
        return map;
    }

    /**
     * 获取订单详细信息
     *
     * @param orderInfo 订单信息
     */
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
        final OrderDetail orderDetail = orderDetailService.getById(orderInfo.getId());
        if (orderDetail != null) {
            orderInfo
                    .getParam()
                    .put("courseName", orderDetail.getCourseName());
        }
        return orderInfo;
    }
}
