package xyz.funnyboy.ggkt.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.model.wechat.Menu;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.wechat.MenuVo;
import xyz.funnyboy.ggkt.wechat.service.MenuService;
import xyz.funnyboy.ggkt.wechat.utils.ConstantPropertiesUtil;
import xyz.funnyboy.ggkt.wechat.utils.HttpClientUtils;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Api(tags = "微信公众号菜单管理")
@RestController
@RequestMapping("/admin/wechat/menu")
@Slf4j
public class MenuController
{

    @Autowired
    private MenuService menuService;

    //获取所有菜单，按照一级和二级菜单封装
    @GetMapping("findMenuInfo")
    public Result findMenuInfo() {
        List<MenuVo> list = menuService.findMenuInfo();
        return Result.ok(list);
    }

    //获取所有一级菜单
    @GetMapping("findOneMenuInfo")
    public Result findOneMenuInfo() {
        List<Menu> list = menuService.findMenuOneInfo();
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(
            @PathVariable
                    Long id) {
        Menu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(
            @RequestBody
                    Menu menu) {
        menuService.save(menu);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(
            @RequestBody
                    Menu menu) {
        menuService.updateById(menu);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(
            @PathVariable
                    Long id) {
        menuService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(
            @RequestBody
                    List<Long> idList) {
        menuService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取access_token")
    @GetMapping("getAccessToken")
    public Result getAccessToken() {
        try {
            //拼接请求地址
            StringBuffer buffer = new StringBuffer();
            buffer.append("https://api.weixin.qq.com/cgi-bin/token");
            buffer.append("?grant_type=%s");
            buffer.append("&appid=%s");
            buffer.append("&secret=%s");
            //请求地址设置参数
            String url = String.format(buffer.toString(), "client_credential", ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            log.info("请求地址：{}", url);

            //发送http请求
            String tokenString = HttpClientUtils.get(url);
            log.info("响应结果：{}", tokenString);

            //解析相应内容，获取access_token
            JSONObject jsonObject = JSONObject.parseObject(tokenString);
            if (!StringUtils.isEmpty(jsonObject.getString("errcode"))) {
                final String errmsg = jsonObject.getString("errmsg");
                log.error("获取access_token失败：{}", errmsg);
                return Result.fail(errmsg);
            }
            String access_token = jsonObject.getString("access_token");
            log.info("access_token:{}", access_token);

            return Result.ok(access_token);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }

    @ApiOperation(value = "同步菜单")
    @PostMapping("syncMenu")
    public Result syncMenu() {
        menuService.syncMenu();
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("removeMenu")
    public Result removeMenu() {
        menuService.removeMenu();
        return Result.ok();
    }
}

