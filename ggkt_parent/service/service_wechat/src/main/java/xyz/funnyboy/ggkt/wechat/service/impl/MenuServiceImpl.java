package xyz.funnyboy.ggkt.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.wechat.Menu;
import xyz.funnyboy.ggkt.swagger.exception.GgktException;
import xyz.funnyboy.ggkt.vo.wechat.MenuVo;
import xyz.funnyboy.ggkt.wechat.mapper.MenuMapper;
import xyz.funnyboy.ggkt.wechat.service.MenuService;
import xyz.funnyboy.ggkt.wechat.utils.ConstantPropertiesUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-20
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService
{

    @Autowired
    private WxMpService wxMpService;

    /**
     * 获取全部菜单
     *
     * @return {@link List}<{@link MenuVo}>
     */
    @Override
    public List<MenuVo> findMenuInfo() {
        // 二级菜单Map
        final Map<Long, List<Menu>> twoMenuListMap = baseMapper
                .selectList(new LambdaQueryWrapper<Menu>().ne(Menu::getParentId, 0))
                .stream()
                .collect(Collectors.groupingBy(Menu::getParentId));

        return baseMapper
                .selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, 0))
                .stream()
                .map(oneMenu -> {
                    // 一级菜单
                    MenuVo oneMenuVo = new MenuVo();
                    BeanUtils.copyProperties(oneMenu, oneMenuVo);

                    // 二级菜单
                    final List<MenuVo> twoMenuList = twoMenuListMap
                            .getOrDefault(oneMenu.getId(), Collections.emptyList())
                            .stream()
                            .map(twoMenu -> {
                                MenuVo twoMenuVo = new MenuVo();
                                BeanUtils.copyProperties(twoMenu, twoMenuVo);
                                return twoMenuVo;
                            })
                            .collect(Collectors.toList());
                    oneMenuVo.setChildren(twoMenuList);
                    return oneMenuVo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取一级菜单
     *
     * @return {@link List}<{@link Menu}>
     */
    @Override
    public List<Menu> findMenuOneInfo() {
        return baseMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, 0));
    }

    /**
     * 同步菜单
     */
    @Override
    public void syncMenu() {
        // 获取所有菜单数据
        final List<MenuVo> menuVoList = this.findMenuInfo();

        // 封装button里面结构，数组格式
        JSONArray buttonList = new JSONArray();
        for (MenuVo oneMenuVo : menuVoList) {
            // 一级菜单
            JSONObject one = new JSONObject();
            one.put("name", oneMenuVo.getName());

            // 二级菜单
            JSONArray subButton = new JSONArray();
            for (MenuVo twoMenuVo : oneMenuVo.getChildren()) {
                JSONObject view = new JSONObject();
                view.put("type", twoMenuVo.getType());
                view.put("name", twoMenuVo.getName());
                if ("view".equals(twoMenuVo.getType())) {
                    view.put("url", ConstantPropertiesUtil.MENU_URL + twoMenuVo.getUrl());
                }
                else {
                    view.put("key", twoMenuVo.getMeunKey());
                }
                subButton.add(view);
            }
            one.put("sub_button", subButton);
            buttonList.add(one);
        }

        // 封装最外层button部分
        JSONObject button = new JSONObject();
        button.put("button", buttonList);
        log.info("菜单信息：{}", button.toJSONString());

        try {
            this.wxMpService
                    .getMenuService()
                    .menuCreate(button.toJSONString());
            log.info("公众号菜单同步成功");
        }
        catch (WxErrorException e) {
            log.error("公众号菜单同步失败：" + e.getMessage(), e);
            throw new GgktException(20001, "公众号菜单同步失败");
        }
    }

    /**
     * 删除菜单
     */
    @Override
    public void removeMenu() {
        try {
            this.wxMpService
                    .getMenuService()
                    .menuDelete();
            log.info("公众号菜单删除成功");
        }
        catch (WxErrorException e) {
            log.error("公众号菜单删除失败：" + e.getMessage(), e);
            throw new GgktException(20001, "公众号菜单删除失败");
        }
    }
}
