package xyz.funnyboy.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.order.PaymentInfo;
import xyz.funnyboy.ggkt.order.mapper.PaymentInfoMapper;
import xyz.funnyboy.ggkt.order.service.PaymentInfoService;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService
{

}
