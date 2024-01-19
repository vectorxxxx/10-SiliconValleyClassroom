package xyz.funnyboy.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.vod.Chapter;
import xyz.funnyboy.ggkt.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
public interface ChapterService extends IService<Chapter>
{

    /**
     * 获取嵌套树列表
     *
     * @param courseId 课程编号
     * @return {@link List}<{@link ChapterVo}>
     */
    List<ChapterVo> getNestedTreeList(Long courseId);
}
