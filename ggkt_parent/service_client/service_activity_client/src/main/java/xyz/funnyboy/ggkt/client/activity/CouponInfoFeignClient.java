package xyz.funnyboy.ggkt.client.activity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import xyz.funnyboy.ggkt.model.activity.CouponInfo;

/**
 * 优惠券Feign接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 19:06:39
 */
@Component
@FeignClient(value = "service-activity")
public interface CouponInfoFeignClient
{
    @GetMapping("/api/activity/couponInfo/inner/getById/{couponId}")
    CouponInfo getById(
            @PathVariable("couponId")
                    Long couponId);

    @PutMapping("/api/activity/couponInfo/inner/updateCouponInfoUseStatus/{couponUseId}/{orderId}")
    Boolean updateCouponInfoUseStatus(
            @PathVariable("couponUseId")
                    Long couponUseId,

            @PathVariable("orderId")
                    Long orderId);
}
