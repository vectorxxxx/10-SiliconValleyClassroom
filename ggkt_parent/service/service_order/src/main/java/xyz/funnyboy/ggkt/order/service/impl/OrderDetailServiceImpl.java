package xyz.funnyboy.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.order.OrderDetail;
import xyz.funnyboy.ggkt.order.mapper.OrderDetailMapper;
import xyz.funnyboy.ggkt.order.service.OrderDetailService;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService
{

}
