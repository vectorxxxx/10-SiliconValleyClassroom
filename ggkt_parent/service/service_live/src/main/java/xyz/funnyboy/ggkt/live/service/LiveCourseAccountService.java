package xyz.funnyboy.ggkt.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.live.LiveCourseAccount;

/**
 * <p>
 * 直播课程账号表（受保护信息） 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
public interface LiveCourseAccountService extends IService<LiveCourseAccount>
{

    /**
     * 按ID获取直播课程帐户课程
     *
     * @param id 编号
     * @return {@link LiveCourseAccount}
     */
    LiveCourseAccount getLiveCourseAccountCourseById(Long id);
}
