package xyz.funnyboy.ggkt.order.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.order.service.OrderInfoService;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.order.OrderFormVo;
import xyz.funnyboy.ggkt.vo.order.OrderInfoVo;

/**
 * 订单接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 18:53:15
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping("api/order/orderInfo")
public class OrderInfoApiController
{
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "生成订单")
    @PostMapping("submitOrder")
    public Result submitOrder(
            @ApiParam(name = "orderFormVo",
                      value = "查询条件",
                      required = true)
            @RequestBody
                    OrderFormVo orderFormVo) {
        Long orderId = orderInfoService.submitOrder(orderFormVo);
        return Result.ok(orderId);
    }

    @ApiOperation(value = "查询订单")
    @GetMapping("getInfo/{id}")
    public Result getInfo(
            @ApiParam(name = "id",
                      value = "订单ID",
                      required = true)
            @PathVariable("id")
                    Long id) {
        OrderInfoVo orderInfoVo = orderInfoService.getOrderInfoById(id);
        return Result.ok(orderInfoVo);
    }
}
