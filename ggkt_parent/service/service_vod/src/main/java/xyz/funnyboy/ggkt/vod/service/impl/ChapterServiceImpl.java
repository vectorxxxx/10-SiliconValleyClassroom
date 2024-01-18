package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.Chapter;
import xyz.funnyboy.ggkt.vod.mapper.ChapterMapper;
import xyz.funnyboy.ggkt.vod.service.ChapterService;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService
{

}
