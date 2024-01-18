package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.VideoVisitor;
import xyz.funnyboy.ggkt.vod.mapper.VideoVisitorMapper;
import xyz.funnyboy.ggkt.vod.service.VideoVisitorService;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService
{

}
