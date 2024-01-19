package xyz.funnyboy.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import xyz.funnyboy.ggkt.model.vod.Subject;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
public interface SubjectService extends IService<Subject>
{

    /**
     * 选择列表
     *
     * @param id 编号
     * @return {@link Object}
     */
    List<Subject> selectList(Long id);

    /**
     * 导出数据
     *
     * @param response 响应
     */
    void exportData(HttpServletResponse response);

    /**
     * 导入数据
     *
     * @param file 文件
     * @return {@link String}
     */
    void importData(MultipartFile file);
}
