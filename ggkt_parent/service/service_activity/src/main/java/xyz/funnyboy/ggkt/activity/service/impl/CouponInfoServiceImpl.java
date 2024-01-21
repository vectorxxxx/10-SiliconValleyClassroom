package xyz.funnyboy.ggkt.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.funnyboy.ggkt.activity.mapper.CouponInfoMapper;
import xyz.funnyboy.ggkt.activity.service.CouponInfoService;
import xyz.funnyboy.ggkt.activity.service.CouponUseService;
import xyz.funnyboy.ggkt.client.user.UserInfoFeignClient;
import xyz.funnyboy.ggkt.model.activity.CouponInfo;
import xyz.funnyboy.ggkt.model.activity.CouponUse;
import xyz.funnyboy.ggkt.model.user.UserInfo;
import xyz.funnyboy.ggkt.vo.activity.CouponUseQueryVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService
{

    @Autowired
    private CouponUseService couponUseService;

    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    /**
     * 分页查询优惠券使用信息
     *
     * @param pageParam        页面参数
     * @param couponUseQueryVo 优惠券使用查询VO
     * @return {@link IPage}<{@link CouponUse}>
     */
    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        final Long couponId = couponUseQueryVo.getCouponId();
        final String couponStatus = couponUseQueryVo.getCouponStatus();
        final String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        final String getTimeEnd = couponUseQueryVo.getGetTimeEnd();

        // 查询条件
        final LambdaQueryWrapper<CouponUse> queryWrapper = new LambdaQueryWrapper<CouponUse>()
                .eq(!StringUtils.isEmpty(couponId), CouponUse::getCouponId, couponId)
                .eq(!StringUtils.isEmpty(couponStatus), CouponUse::getCouponStatus, couponStatus)
                .ge(!StringUtils.isEmpty(getTimeBegin), CouponUse::getGetTime, getTimeBegin)
                .le(!StringUtils.isEmpty(getTimeEnd), CouponUse::getGetTime, getTimeEnd);

        // 分页查询
        final Page<CouponUse> pageModel = couponUseService.page(pageParam, queryWrapper);
        final List<CouponUse> couponUseList = pageModel.getRecords();
        couponUseList.forEach(this::getUserInfoById);
        return pageModel;
    }

    /**
     * 更新优惠券信息使用状态
     *
     * @param couponUseId 优惠券使用ID
     * @param orderId     订单编号
     */
    @Override
    public void updateCouponInfoUseStatus(Long couponUseId, Long orderId) {
        CouponUse couponUse = new CouponUse();
        couponUse.setId(couponUseId);
        couponUse.setOrderId(orderId);
        // 购物券状态（0：未使用 1：已使用）
        couponUse.setCouponStatus("1");
        couponUse.setUsingTime(new Date());
        couponUseService.updateById(couponUse);
    }

    /**
     * 根据用户id，通过远程调用得到用户信息
     *
     * @param couponUse 优惠券使用
     */
    private CouponUse getUserInfoById(CouponUse couponUse) {
        final Long userId = couponUse.getUserId();
        if (userId == null) {
            return couponUse;
        }

        final UserInfo userInfo = userInfoFeignClient.getById(userId);
        if (userInfo == null) {
            return couponUse;
        }

        couponUse
                .getParam()
                .put("nickName", userInfo.getNickName());
        couponUse
                .getParam()
                .put("phone", userInfo.getPhone());
        return couponUse;
    }
}
