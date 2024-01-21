package xyz.funnyboy.ggkt.activity.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.activity.service.CouponInfoService;
import xyz.funnyboy.ggkt.model.activity.CouponInfo;

/**
 * 优惠券接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 18:59:30
 */
@Api(tags = "优惠券接口")
@RestController
@RequestMapping("/api/activity/couponInfo")
public class CouponInfoApiController
{
    @Autowired
    private CouponInfoService couponInfoService;

    @ApiOperation(value = "获取优惠券")
    @GetMapping("inner/getById/{couponId}")
    public CouponInfo getById(
            @ApiParam(name = "couponId",
                      value = "优惠券ID",
                      required = true)
            @PathVariable("couponId")
                    Long couponId) {
        return couponInfoService.getById(couponId);
    }

    @ApiOperation(value = "更新优惠券使用状态")
    @PutMapping("inner/updateCouponInfoUseStatus/{couponUseId}/{orderId}")
    public Boolean updateCouponInfoUseStatus(
            @ApiParam(name = "couponUseId",
                      value = "优惠券使用ID",
                      required = true)
            @PathVariable("couponUseId")
                    Long couponUseId,

            @ApiParam(name = "orderId",
                      value = "订单ID",
                      required = true)
            @PathVariable("orderId")
                    Long orderId) {
        couponInfoService.updateCouponInfoUseStatus(couponUseId, orderId);
        return true;
    }
}
