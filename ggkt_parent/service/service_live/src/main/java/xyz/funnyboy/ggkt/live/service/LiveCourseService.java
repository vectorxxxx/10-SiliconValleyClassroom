package xyz.funnyboy.ggkt.live.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.funnyboy.ggkt.model.live.LiveCourse;
import xyz.funnyboy.ggkt.vo.live.LiveCourseConfigVo;
import xyz.funnyboy.ggkt.vo.live.LiveCourseFormVo;
import xyz.funnyboy.ggkt.vo.live.LiveCourseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 直播课程表 服务类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
public interface LiveCourseService extends IService<LiveCourse>
{

    /**
     * 获取分页列表
     *
     * @param pageParam 页面参数
     * @return {@link Page}<{@link LiveCourse}>
     */
    IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam);

    /**
     * 新增直播
     *
     * @param liveCourseFormVo 直播课程VO
     */
    void saveLive(LiveCourseFormVo liveCourseFormVo);

    /**
     * 删除直播
     *
     * @param id 编号
     */
    void removeLive(Long id);

    /**
     * id查询直播课程基本信息和描述信息
     *
     * @param id 编号
     * @return {@link LiveCourseFormVo}
     */
    LiveCourseFormVo getLiveCourseFormVo(Long id);

    /**
     * 按 ID 更新直播
     *
     * @param liveCourseFormVo 现场课程表格 VO
     */
    void updateLiveById(LiveCourseFormVo liveCourseFormVo);

    /**
     * 按 ID 获取课程配置
     *
     * @param id 编号
     * @return {@link LiveCourseConfigVo}
     */
    LiveCourseConfigVo getCourseConfigById(Long id);

    /**
     * 更新配置
     *
     * @param liveCourseConfigVo 直播课程配置 VO
     */
    void updateConfig(LiveCourseConfigVo liveCourseConfigVo);

    /**
     * 获取最近的直播
     *
     * @return {@link List}<{@link LiveCourseVo}>
     */
    List<LiveCourseVo> findLatelyList();

    /**
     * 获取播放权限
     *
     * @param id     编号
     * @param userId 用户 ID
     * @return {@link JSONObject}
     */
    JSONObject getPlayAuth(Long id, Long userId);

    /**
     * 按 ID 获取信息
     *
     * @param courseId 课程编号
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    Map<String, Object> getInfoById(Long courseId);
}
