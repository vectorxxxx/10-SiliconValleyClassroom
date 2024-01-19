package xyz.funnyboy.ggkt.vod.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.swagger.result.Result;
import xyz.funnyboy.ggkt.vod.service.SubjectService;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/admin/vod/subject")
// @CrossOrigin
public class SubjectController
{
    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "查询下一层的课程分类")
    @GetMapping("getChildSubject/{id}")
    public Result getChildSubject(
            @ApiParam(name = "id",
                      value = "父级ID",
                      required = true)
            @PathVariable
                    Long id) {
        return Result.ok(subjectService.selectList(id));
    }

    @ApiOperation(value = "导出数据")
    @GetMapping("exportData")
    public void exportData(
            @ApiParam(name = "request",
                      value = "请求对象",
                      required = true)
                    HttpServletResponse response) {
        subjectService.exportData(response);
    }

    @ApiOperation(value = "导入数据")
    @PostMapping("importData")
    public Result importData(
            @ApiParam(name = "file",
                      value = "文件",
                      required = true)
                    MultipartFile file) {
        subjectService.importData(file);
        return Result.ok();
    }
}

