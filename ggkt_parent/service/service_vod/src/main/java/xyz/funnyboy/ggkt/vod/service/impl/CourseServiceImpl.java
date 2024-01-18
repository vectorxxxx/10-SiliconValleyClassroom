package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.Course;
import xyz.funnyboy.ggkt.vod.mapper.CourseMapper;
import xyz.funnyboy.ggkt.vod.service.CourseService;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService
{

}
