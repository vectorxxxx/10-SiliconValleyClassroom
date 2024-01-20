package xyz.funnyboy.ggkt.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.wechat.Menu;
import xyz.funnyboy.ggkt.vo.wechat.MenuVo;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
public interface MenuService extends IService<Menu>
{

    /**
     * 获取全部菜单
     *
     * @return {@link List}<{@link MenuVo}>
     */
    List<MenuVo> findMenuInfo();

    /**
     * 获取一级菜单
     *
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> findMenuOneInfo();

    /**
     * 同步菜单
     */
    void syncMenu();

    /**
     * 删除菜单
     */
    void removeMenu();
}
