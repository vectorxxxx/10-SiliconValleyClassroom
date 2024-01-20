package xyz.funnyboy.ggkt.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.funnyboy.ggkt.model.user.UserInfo;
import xyz.funnyboy.ggkt.user.service.UserInfoService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController
{

    @Autowired
    private UserInfoService userService;

    @ApiOperation(value = "获取")
    @GetMapping("inner/getById/{id}")
    public UserInfo getById(
            @PathVariable("id")
                    Long id) {
        return userService.getById(id);
    }
}

