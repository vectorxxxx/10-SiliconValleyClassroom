package xyz.funnyboy.ggkt.live.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.live.service.LiveCourseAccountService;
import xyz.funnyboy.ggkt.live.service.LiveCourseService;
import xyz.funnyboy.ggkt.model.live.LiveCourse;
import xyz.funnyboy.ggkt.model.live.LiveCourseAccount;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.live.LiveCourseConfigVo;
import xyz.funnyboy.ggkt.vo.live.LiveCourseFormVo;

/**
 * <p>
 * 直播课程表 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
@Api(tags = "直播管理")
@RestController
@RequestMapping("/admin/live/liveCourse")
public class LiveCourseController
{

    @Autowired
    private LiveCourseService liveCourseService;

    @Autowired
    private LiveCourseAccountService liveCourseAccountService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result getLiveCourseList(
            @ApiParam(name = "page",
                      value = "当前页",
                      required = true)
            @PathVariable("page")
                    Integer page,

            @ApiParam(name = "limit",
                      value = "每页条数",
                      required = true)
            @PathVariable("limit")
                    Integer limit) {
        final Page<LiveCourse> pageParam = new Page<>(page, limit);
        final IPage<LiveCourse> pageModel = liveCourseService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(
            @ApiParam(name = "liveCourseFormVo",
                      value = "新增对象",
                      required = true)
            @RequestBody
                    LiveCourseFormVo liveCourseFormVo) {
        liveCourseService.saveLive(liveCourseFormVo);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(
            @ApiParam(name = "id",
                      value = "id",
                      required = true)
            @PathVariable
                    Long id) {
        liveCourseService.removeLive(id);
        return Result.ok();
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result<LiveCourse> get(
            @ApiParam(name = "id",
                      value = "id",
                      required = true)
            @PathVariable
                    Long id) {
        LiveCourse liveCourse = liveCourseService.getById(id);
        return Result.ok(liveCourse);
    }

    @ApiOperation(value = "获取")
    @GetMapping("getInfo/{id}")
    public Result<LiveCourseFormVo> getInfo(
            @ApiParam(name = "id",
                      value = "id",
                      required = true)
            @PathVariable
                    Long id) {
        return Result.ok(liveCourseService.getLiveCourseFormVo(id));
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(
            @ApiParam(name = "liveCourseFormVo",
                      value = "修改对象",
                      required = true)
            @RequestBody
                    LiveCourseFormVo liveCourseFormVo) {
        liveCourseService.updateLiveById(liveCourseFormVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取直播账号信息")
    @GetMapping("getLiveCourseAccount/{id}")
    public Result getLiveCourseAccount(
            @ApiParam(name = "id",
                      value = "id",
                      required = true)
            @PathVariable
                    Long id) {
        LiveCourseAccount liveCourseAccount = liveCourseAccountService.getLiveCourseAccountCourseById(id);
        return Result.ok(liveCourseAccount);
    }

    @ApiOperation(value = "获取直播配置信息")
    @GetMapping("getCourseConfig/{id}")
    public Result getCourseConfig(
            @ApiParam(name = "id",
                      value = "id",
                      required = true)
            @PathVariable
                    Long id) {
        LiveCourseConfigVo liveCourseConfigVo = liveCourseService.getCourseConfigById(id);
        return Result.ok(liveCourseConfigVo);
    }

    @ApiOperation(value = "修改配置")
    @PutMapping("updateConfig")
    public Result updateConfig(
            @ApiParam(name = "liveCourseConfigVo",
                      value = "修改对象",
                      required = true)
            @RequestBody
                    LiveCourseConfigVo liveCourseConfigVo) {
        liveCourseService.updateConfig(liveCourseConfigVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取最近的直播")
    @GetMapping("findLatelyList")
    public Result findLatelyList() {
        return Result.ok(liveCourseService.findLatelyList());
    }
}

