package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.CourseDescription;
import xyz.funnyboy.ggkt.vod.mapper.CourseDescriptionMapper;
import xyz.funnyboy.ggkt.vod.service.CourseDescriptionService;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService
{

}
