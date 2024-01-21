package xyz.funnyboy.ggkt.order.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.order.service.OrderInfoService;
import xyz.funnyboy.ggkt.order.service.WXPayService;
import xyz.funnyboy.ggkt.swagger.exception.GgktException;
import xyz.funnyboy.ggkt.swagger.result.Result;

import java.util.Map;

/**
 * 微信支付接口
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-21 19:51:18
 */
@Api(tags = "微信支付接口")
@RestController
@RequestMapping("/api/order/wxPay")
@Slf4j
public class WXPayController
{
    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "下单 小程序支付")
    @GetMapping("createJsapi/{orderNo}")
    public Result createJsapi(
            @ApiParam(name = "orderNo",
                      value = "订单号",
                      required = true)
            @PathVariable("orderNo")
                    String orderNo) {
        return Result.ok(wxPayService.createJsapi(orderNo));
    }

    @ApiOperation(value = "查询支付状态")
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(
            @ApiParam(name = "orderNo",
                      value = "订单号",
                      required = true)
            @PathVariable("orderNo")
                    String orderNo) {
        log.info("订单号：" + orderNo);
        //调用查询接口
        Map<String, String> resultMap = wxPayService.queryPayStatus(orderNo);
        if (resultMap == null) {
            throw new GgktException(20001, "支付出错");
        }

        if ("SUCCESS".equals(resultMap.get("trade_status"))) {
            // 更改订单状态，处理支付结果
            final String outTradeNo = resultMap.get("out_trade_no");
            log.info("支付成功，流水号：" + outTradeNo);
            orderInfoService.updateOrderStatus(outTradeNo);
            return Result
                    .ok()
                    .message("支付成功");
        }
        return Result
                .ok()
                .message("支付中");
    }
}
