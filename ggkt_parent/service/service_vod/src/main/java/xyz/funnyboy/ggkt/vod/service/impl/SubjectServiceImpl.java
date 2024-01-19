package xyz.funnyboy.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.funnyboy.ggkt.model.vod.Subject;
import xyz.funnyboy.ggkt.vod.mapper.SubjectMapper;
import xyz.funnyboy.ggkt.vod.service.SubjectService;

import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-18
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService
{

    /**
     * 课程分类列表
     * <p>
     * 懒加载，每次查询一层数据
     *
     * @param id 编号
     * @return {@link Object}
     */
    @Override
    public List<Subject> selectList(Long id) {
        final List<Subject> subjectList = baseMapper.selectList(new LambdaQueryWrapper<Subject>().eq(Subject::getParentId, id));
        subjectList.forEach(subject -> subject.setHasChildren(this.hasChildren(subject.getId())));
        return subjectList;
    }

    /**
     * 判断是否有下一层数据
     *
     * @param id 编号
     * @return boolean
     */
    private boolean hasChildren(Long id) {
        final Integer count = baseMapper.selectCount(new LambdaQueryWrapper<Subject>().eq(Subject::getParentId, id));
        return count > 0;
    }
}
