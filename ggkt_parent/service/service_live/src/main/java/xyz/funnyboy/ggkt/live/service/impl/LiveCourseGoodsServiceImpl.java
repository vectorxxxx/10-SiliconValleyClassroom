package xyz.funnyboy.ggkt.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.live.mapper.LiveCourseGoodsMapper;
import xyz.funnyboy.ggkt.live.service.LiveCourseGoodsService;
import xyz.funnyboy.ggkt.model.live.LiveCourseGoods;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
@Service
public class LiveCourseGoodsServiceImpl extends ServiceImpl<LiveCourseGoodsMapper, LiveCourseGoods> implements LiveCourseGoodsService
{

    /**
     * 按ID获取货物清单课程
     *
     * @param id 编号
     * @return {@link List}<{@link LiveCourseGoods}>
     */
    @Override
    public List<LiveCourseGoods> getGoodsListCourseById(Long id) {
        return baseMapper.selectList(new LambdaQueryWrapper<LiveCourseGoods>().eq(LiveCourseGoods::getLiveCourseId, id));
    }
}
