package xyz.funnyboy.ggkt.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.activity.CouponInfo;
import xyz.funnyboy.ggkt.model.activity.CouponUse;
import xyz.funnyboy.ggkt.vo.activity.CouponUseQueryVo;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
public interface CouponInfoService extends IService<CouponInfo>
{

    /**
     * 分页查询优惠券使用信息
     *
     * @param pageParam        页面参数
     * @param couponUseQueryVo 优惠券使用查询VO
     * @return {@link IPage}<{@link CouponUse}>
     */
    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);
}
