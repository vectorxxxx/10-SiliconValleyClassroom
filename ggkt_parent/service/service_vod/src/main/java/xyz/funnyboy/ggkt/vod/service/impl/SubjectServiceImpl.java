package xyz.funnyboy.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.model.vod.Subject;
import xyz.funnyboy.ggkt.vo.vod.SubjectEeVo;
import xyz.funnyboy.ggkt.vod.listener.SubjectListener;
import xyz.funnyboy.ggkt.vod.mapper.SubjectMapper;
import xyz.funnyboy.ggkt.vod.service.SubjectService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService
{

    @Autowired
    private SubjectListener subjectListener;

    /**
     * 课程分类列表
     * <p>
     * 懒加载，每次查询一层数据
     *
     * @param id 编号
     * @return {@link Object}
     */
    @Override
    public List<Subject> selectList(Long id) {
        final List<Subject> subjectList = baseMapper.selectList(new LambdaQueryWrapper<Subject>().eq(Subject::getParentId, id));
        subjectList.forEach(subject -> subject.setHasChildren(this.hasChildren(subject.getId())));
        return subjectList;
    }

    /**
     * 导出数据
     *
     * @param response 响应
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 查询数据
            final List<Subject> subjectList = baseMapper.selectList(null);

            // 转换数据
            final List<SubjectEeVo> subjectEeVoList = subjectList
                    .stream()
                    .map(subject -> {
                        final SubjectEeVo subjectEeVo = new SubjectEeVo();
                        BeanUtils.copyProperties(subject, subjectEeVo);
                        return subjectEeVo;
                    })
                    .collect(Collectors.toList());

            // 设置响应头
            final String UTF_8 = StandardCharsets.UTF_8.name();
            final String fileName = "课程分类";
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(UTF_8.toLowerCase(Locale.ROOT));
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, UTF_8) + ".xlsx");

            // 导出数据
            EasyExcel
                    .write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet(fileName)
                    .doWrite(subjectEeVoList);
        }
        catch (IOException e) {
            log.error("导出数据失败：" + e.getMessage(), e);
        }
    }

    /**
     * 导入数据
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel
                    .read(file.getInputStream(), SubjectEeVo.class, subjectListener)
                    .sheet()
                    .doRead();
        }
        catch (Exception e) {
            log.error("导入数据失败：" + e.getMessage(), e);
        }
    }

    /**
     * 判断是否有下一层数据
     *
     * @param id 编号
     * @return boolean
     */
    private boolean hasChildren(Long id) {
        final Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Subject>().eq(Subject::getParentId, id));
        return count > 0;
    }
}
