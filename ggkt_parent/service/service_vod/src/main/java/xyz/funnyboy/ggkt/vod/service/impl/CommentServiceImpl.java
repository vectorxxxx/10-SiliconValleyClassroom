package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.Comment;
import xyz.funnyboy.ggkt.vod.mapper.CommentMapper;
import xyz.funnyboy.ggkt.vod.service.CommentService;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService
{

}
