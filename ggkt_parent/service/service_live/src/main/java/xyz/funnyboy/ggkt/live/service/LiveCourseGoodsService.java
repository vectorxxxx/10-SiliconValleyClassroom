package xyz.funnyboy.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.live.LiveCourseGoods;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
public interface LiveCourseGoodsService extends IService<LiveCourseGoods>
{

    /**
     * 按ID获取货物清单课程
     *
     * @param id 编号
     * @return {@link List}<{@link LiveCourseGoods}>
     */
    List<LiveCourseGoods> getGoodsListCourseById(Long id);
}
