package xyz.funnyboy.ggkt.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.order.OrderInfo;
import xyz.funnyboy.ggkt.vo.order.OrderFormVo;
import xyz.funnyboy.ggkt.vo.order.OrderInfoQueryVo;
import xyz.funnyboy.ggkt.vo.order.OrderInfoVo;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
public interface OrderInfoService extends IService<OrderInfo>
{

    /**
     * 分页查询订单
     *
     * @param pageParam        页面参数
     * @param orderInfoQueryVo 订单信息查询VO
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> findPageOrderInfo(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    /**
     * 生成订单
     *
     * @param orderFormVo 查询 VO
     * @return {@link Long}
     */
    Long submitOrder(OrderFormVo orderFormVo);

    /**
     * 按 ID 获取订单信息
     *
     * @param id 编号
     * @return {@link OrderInfoVo}
     */
    OrderInfoVo getOrderInfoById(Long id);

    /**
     * 更新订单状态
     *
     * @param outTradeNo 交易流水号
     */
    void updateOrderStatus(String outTradeNo);
}
