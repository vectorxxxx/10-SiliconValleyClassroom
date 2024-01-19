package xyz.funnyboy.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.vod.Subject;

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
}
