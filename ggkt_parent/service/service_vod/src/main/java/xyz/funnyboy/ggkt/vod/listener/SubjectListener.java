package xyz.funnyboy.ggkt.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.funnyboy.ggkt.model.vod.Subject;
import xyz.funnyboy.ggkt.vo.vod.SubjectEeVo;
import xyz.funnyboy.ggkt.vod.mapper.SubjectMapper;

/**
 * 课程导入监听器
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-19 10:06:34
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo>
{
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        System.out.println("解析到一条数据：" + subjectEeVo.toString());
        final Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo, subject);
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("所有数据解析完成");
    }
}
