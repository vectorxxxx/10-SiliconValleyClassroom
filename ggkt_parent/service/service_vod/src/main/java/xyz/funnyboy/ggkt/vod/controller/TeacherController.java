package xyz.funnyboy.ggkt.vod.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.model.vod.Teacher;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.vod.TeacherQueryVo;
import xyz.funnyboy.ggkt.vod.service.TeacherService;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
@CrossOrigin
public class TeacherController
{
    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Result findAll() {
        return Result
                .ok(teacherService.list())
                .message("查询成功");
    }

    @ApiOperation(value = "分页查询讲师")
    @PostMapping("findQueryPage/{page}/{limit}")
    public Result index(
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

            @ApiParam(name = "teacherQueryVo",
                      value = "查询对象",
                      required = false)
            @RequestBody(required = false)
                    TeacherQueryVo teacherQueryVo) {
        // 分页条件
        final Page<Teacher> pageParam = new Page<>(page, limit);

        // 查询条件
        final String name = teacherQueryVo.getName();
        final Integer level = teacherQueryVo.getLevel();
        final String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        final String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        final LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<Teacher>()
                .like(!StringUtils.isEmpty(name), Teacher::getName, name)
                .eq(!StringUtils.isEmpty(level), Teacher::getLevel, level)
                .ge(!StringUtils.isEmpty(joinDateBegin), Teacher::getJoinDate, joinDateBegin)
                .le(!StringUtils.isEmpty(joinDateEnd), Teacher::getJoinDate, joinDateEnd);

        // 查询结果
        final Page<Teacher> pages = teacherService.page(pageParam, queryWrapper);
        return Result.ok(pages);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeById(
            @ApiParam(name = "id",
                      value = "讲师ID",
                      required = true)
            @PathVariable
                    String id) {
        final boolean remove = teacherService.removeById(id);
        return remove ?
               Result.ok() :
               Result.fail();
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public Result addTeacher(
            @ApiParam(name = "teacher",
                      value = "新增讲师对象",
                      required = true)
            @RequestBody
                    Teacher teacher) {
        final boolean save = teacherService.save(teacher);
        return save ?
               Result.ok() :
               Result.fail();
    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result get(
            @ApiParam(name = "id",
                      value = "讲师ID",
                      required = true)
            @PathVariable
                    Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    @ApiOperation(value = "根据id修改讲师")
    @PutMapping("updateTeacher")
    public Result update(
            @ApiParam(name = "teacher",
                      value = "新增讲师对象",
                      required = true)
            @RequestBody
                    Teacher teacher) {
        final boolean update = teacherService.updateById(teacher);
        return update ?
               Result.ok() :
               Result.fail();
    }

    @ApiOperation(value = "批量删除讲师")
    @DeleteMapping("removeBatch")
    public Result removeBatch(
            @ApiParam(name = "ids",
                      value = "讲师ID",
                      required = true)
            @RequestBody
                    List<Long> ids) {
        final boolean remove = teacherService.removeByIds(ids);
        return remove ?
               Result.ok() :
               Result.fail();
    }
}

