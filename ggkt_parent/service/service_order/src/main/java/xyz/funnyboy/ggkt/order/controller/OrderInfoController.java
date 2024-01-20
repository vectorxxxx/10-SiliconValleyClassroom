package xyz.funnyboy.ggkt.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.model.order.OrderInfo;
import xyz.funnyboy.ggkt.order.service.OrderInfoService;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.order.OrderInfoQueryVo;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController
{
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation("分页查询订单")
    @GetMapping("{page}/{limit}")
    public Result list(
            @ApiParam(name = "page",
                      value = "当前页码",
                      required = true)
            @PathVariable
                    Long page,

            @ApiParam(name = "limit",
                      value = "每页记录数",
                      required = true)
            @PathVariable
                    Long limit,

            @ApiParam(name = "orderInfoQueryVo",
                      value = "查询对象",
                      required = false)
                    OrderInfoQueryVo orderInfoQueryVo) {
        final Page<OrderInfo> pageParam = new Page<>(page, limit);
        Map<String, Object> map = orderInfoService.findPageOrderInfo(pageParam, orderInfoQueryVo);
        return Result.ok(map);
    }
}

