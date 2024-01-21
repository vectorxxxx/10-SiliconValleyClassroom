package xyz.funnyboy.ggkt.live.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.live.mapper.LiveVisitorMapper;
import xyz.funnyboy.ggkt.live.service.LiveVisitorService;
import xyz.funnyboy.ggkt.model.live.LiveVisitor;

/**
 * <p>
 * 直播来访者记录表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
@Service
public class LiveVisitorServiceImpl extends ServiceImpl<LiveVisitorMapper, LiveVisitor> implements LiveVisitorService
{

}
