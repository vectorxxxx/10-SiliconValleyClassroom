package xyz.funnyboy.ggkt.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.funnyboy.ggkt.model.vod.Chapter;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vo.vod.ChapterVo;
import xyz.funnyboy.ggkt.vod.service.ChapterService;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Api(tags = "课程章节管理")
@RestController
@RequestMapping("/admin/vod/chapter")
@CrossOrigin
public class ChapterController
{
    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "嵌套查询章节列表")
    @RequestMapping("getNestedTreeList/{courseId}")
    public Result getNestedTreeList(
            @ApiParam(name = "courseId",
                      value = "课程ID",
                      required = true)
            @PathVariable
                    Long courseId) {
        List<ChapterVo> chapterVoList = chapterService.getNestedTreeList(courseId);
        return Result.ok(chapterVoList);
    }

    @ApiOperation(value = "新增章节")
    @PostMapping("save")
    public Result save(
            @ApiParam(name = "chapter",
                      value = "新增对象",
                      required = true)
            @RequestBody
                    Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok();
    }

    @ApiOperation(value = "根据ID获取章节")
    @GetMapping("get/{id}")
    public Result get(
            @ApiParam(name = "id",
                      value = "章节ID",
                      required = true)
            @PathVariable
                    Long id) {
        final Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    @ApiOperation(value = "更新章节")
    @PutMapping("update")
    public Result update(
            @ApiParam(name = "chapter",
                      value = "更新对象",
                      required = true)
            @RequestBody
                    Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("remove/{id}")
    public Result remove(
            @ApiParam(name = "id",
                      value = "章节ID",
                      required = true)
            @PathVariable
                    Long id) {
        chapterService.removeById(id);
        return Result.ok();
    }
}

