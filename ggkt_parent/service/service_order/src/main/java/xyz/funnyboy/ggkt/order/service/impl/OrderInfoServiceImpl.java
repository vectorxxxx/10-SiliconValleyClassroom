package xyz.funnyboy.ggkt.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.ggkt.client.activity.CouponInfoFeignClient;
import xyz.funnyboy.ggkt.client.course.CourseFeignClient;
import xyz.funnyboy.ggkt.client.user.UserInfoFeignClient;
import xyz.funnyboy.ggkt.model.activity.CouponInfo;
import xyz.funnyboy.ggkt.model.order.OrderDetail;
import xyz.funnyboy.ggkt.model.order.OrderInfo;
import xyz.funnyboy.ggkt.model.user.UserInfo;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.order.mapper.OrderInfoMapper;
import xyz.funnyboy.ggkt.order.service.OrderDetailService;
import xyz.funnyboy.ggkt.order.service.OrderInfoService;
import xyz.funnyboy.ggkt.swagger.exception.GgktException;
import xyz.funnyboy.ggkt.swagger.utils.AuthContextHolder;
import xyz.funnyboy.ggkt.swagger.utils.OrderNoUtils;
import xyz.funnyboy.ggkt.vo.order.OrderFormVo;
import xyz.funnyboy.ggkt.vo.order.OrderInfoQueryVo;
import xyz.funnyboy.ggkt.vo.order.OrderInfoVo;

import java.math.BigDecimal;
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

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private CouponInfoFeignClient couponInfoFeignClient;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

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
     * 生成订单
     *
     * @param orderFormVo 查询 VO
     * @return {@link Long}
     */
    @Override
    public Long submitOrder(OrderFormVo orderFormVo) {
        final Long courseId = orderFormVo.getCourseId();
        final Long couponId = orderFormVo.getCouponId();
        final Long couponUseId = orderFormVo.getCouponUseId();
        final Long userId = AuthContextHolder.getUserId();

        // 查询订单是否已生成
        final OrderDetail orderDetailExist = orderDetailService.getOne(new LambdaQueryWrapper<OrderDetail>()
                .eq(OrderDetail::getUserId, userId)
                .eq(OrderDetail::getCourseId, courseId));
        if (orderDetailExist != null) {
            return orderDetailExist.getOrderId();
        }

        // 获取课程信息
        final Course course = courseFeignClient.getById(courseId);
        if (course == null) {
            throw new GgktException(20001, "课程不存在");
        }

        // 获取用户信息
        final UserInfo userInfo = userInfoFeignClient.getById(userId);
        if (userInfo == null) {
            throw new GgktException(20001, "用户不存在");
        }

        // 获取优惠券信息
        BigDecimal couponReduce = BigDecimal.ZERO;
        if (couponId != null) {
            final CouponInfo couponInfo = couponInfoFeignClient.getById(couponId);
            couponReduce = couponInfo.getAmount();
        }

        // 保存订单基本信息
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setNickName(userInfo.getNickName());
        orderInfo.setPhone(userInfo.getPhone());
        orderInfo.setProvince(userInfo.getProvince());
        orderInfo.setOriginAmount(course.getPrice());
        orderInfo.setCouponReduce(couponReduce);
        orderInfo.setFinalAmount(course
                .getPrice()
                .subtract(couponReduce));
        orderInfo.setOutTradeNo(OrderNoUtils.getOrderNo());
        orderInfo.setTradeBody(course.getTitle());
        orderInfo.setOrderStatus("0");
        baseMapper.insert(orderInfo);

        // 保存订单详情信息
        final OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderInfo.getId());
        orderDetail.setUserId(userId);
        orderDetail.setCourseId(courseId);
        orderDetail.setCourseName(course.getTitle());
        orderDetail.setCover(course.getCover());
        orderDetail.setOriginAmount(course.getPrice());
        orderDetail.setCouponReduce(BigDecimal.ZERO);
        orderDetail.setFinalAmount(orderInfo
                .getFinalAmount()
                .subtract(orderDetail.getCouponReduce()));
        orderDetailService.save(orderDetail);

        // 更新优惠券状态
        if (couponUseId != null) {
            couponInfoFeignClient.updateCouponInfoUseStatus(couponUseId, orderInfo.getId());
        }

        // 返回订单ID
        return orderDetail.getOrderId();
    }

    /**
     * 按 ID 获取订单信息
     *
     * @param id 编号
     * @return {@link OrderInfoVo}
     */
    @Override
    public OrderInfoVo getOrderInfoById(Long id) {
        final OrderInfo orderInfo = baseMapper.selectById(id);
        final OrderDetail orderDetail = orderDetailService.getById(id);

        final OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        orderInfoVo.setCourseId(orderDetail.getCourseId());
        orderInfoVo.setCourseName(orderDetail.getCourseName());
        return orderInfoVo;
    }

    /**
     * 更新订单状态
     *
     * @param outTradeNo 交易流水号
     */
    @Override
    public void updateOrderStatus(String outTradeNo) {
        final OrderInfo orderInfo = baseMapper.selectOne(new LambdaQueryWrapper<OrderInfo>().eq(OrderInfo::getOutTradeNo, outTradeNo));
        orderInfo.setOrderStatus("1");
        baseMapper.updateById(orderInfo);
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
