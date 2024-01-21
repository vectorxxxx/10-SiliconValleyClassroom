package xyz.funnyboy.ggkt.live.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import xyz.funnyboy.ggkt.client.course.CourseFeignClient;
import xyz.funnyboy.ggkt.live.mapper.LiveCourseMapper;
import xyz.funnyboy.ggkt.live.mtcloud.CommonResult;
import xyz.funnyboy.ggkt.live.mtcloud.MTCloud;
import xyz.funnyboy.ggkt.live.service.*;
import xyz.funnyboy.ggkt.model.live.*;
import xyz.funnyboy.ggkt.model.vod.Teacher;
import xyz.funnyboy.ggkt.swagger.exception.GgktException;
import xyz.funnyboy.ggkt.swagger.utils.DateUtil;
import xyz.funnyboy.ggkt.vo.live.LiveCourseConfigVo;
import xyz.funnyboy.ggkt.vo.live.LiveCourseFormVo;
import xyz.funnyboy.ggkt.vo.live.LiveCourseGoodsView;
import xyz.funnyboy.ggkt.vo.live.LiveCourseVo;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 直播课程表 服务实现类
 * </p>
 *
 * @author vectorx
 * @since 2024-01-21
 */
@Service
@Slf4j
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements LiveCourseService
{
    @Resource
    private CourseFeignClient teacherFeignClient;

    @Resource
    private LiveCourseDescriptionService liveCourseDescriptionService;

    @Resource
    private LiveCourseAccountService liveCourseAccountService;

    @Resource
    private LiveCourseConfigService liveCourseConfigService;

    @Resource
    private LiveCourseGoodsService liveCourseGoodsService;

    @Resource
    private MTCloud mtCloudClient;

    /**
     * 获取分页列表
     *
     * @param pageParam 页面参数
     * @return {@link Page}<{@link LiveCourse}>
     */
    @Override
    public IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam) {
        final Page<LiveCourse> page = baseMapper.selectPage(pageParam, null);
        page
                .getRecords()
                .forEach(liveCourse -> {
                    final Teacher teacher = teacherFeignClient.getTeacherInfo(liveCourse.getTeacherId());
                    liveCourse
                            .getParam()
                            .put("teacherName", teacher.getName());
                    liveCourse
                            .getParam()
                            .put("teacherLevel", teacher.getLevel());
                    liveCourse
                            .getParam()
                            .put("startTimeString", new DateTime(liveCourse.getStartTime()).toString("yyyy年MM月dd HH:mm"));
                    liveCourse
                            .getParam()
                            .put("endTimeString", new DateTime(liveCourse.getEndTime()).toString("yyyy年MM月dd HH:mm"));
                });
        return page;
    }

    /**
     * 新增直播课程
     *
     * @param liveCourseFormVo 直播课程VO
     * @return {@link Boolean}
     */
    @Override
    public void saveLive(LiveCourseFormVo liveCourseFormVo) {
        final LiveCourse liveCourse = new LiveCourse();
        BeanUtils.copyProperties(liveCourseFormVo, liveCourse);

        // 获取讲师信息
        final Teacher teacher = teacherFeignClient.getTeacherInfo(liveCourse.getTeacherId());
        // 创建map集合，封装直播课程其他参数
        HashMap<Object, Object> options = new HashMap<>();
        // 直播类型。1: 教育直播，2: 生活直播。默认 1
        options.put("scenes", 2);
        // 主播密码，只有新主播生效。已经存在的主播，密码不会被修改
        options.put("password", liveCourseFormVo.getPassword());

        try {
            // course_name	string	Y	直播名称
            // account	string	Y	接入方主播账号或ID或手机号等，最长32位
            // start_time	string	Y	直播开始时间,格式: 2015-01-10 12:00:00
            // end_time	string	Y	直播结束时间,格式: 2015-01-10 13:00:00
            // nickname	string	Y	主播的昵称
            // accountIntro	string	N	主播的简介
            // options	object	N	其他选项
            final String courseName = liveCourseFormVo.getCourseName();
            final String account = teacher
                    .getId()
                    .toString();
            final String startTime = new DateTime(liveCourseFormVo.getStartTime()).toString("yyyy-MM-dd HH:mm:ss");
            final String endTime = new DateTime(liveCourseFormVo.getEndTime()).toString("yyyy-MM-dd HH:mm:ss");
            final String nickName = teacher.getName();
            final String accountIntro = teacher.getIntro();
            final String res = mtCloudClient.courseAdd(courseName, account, startTime, endTime, nickName, accountIntro, options);
            log.info("新增直播课程返回: " + res);

            final CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if (Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                // partner_id	int	合作方id
                // bid	int	欢拓系统的主播id
                // course_name	string	直播名称
                // start_time	int	开始时间戳
                // end_time	int	结束时间戳
                // zhubo_key	string	主播登录秘钥
                // admin_key	string	客服登录秘钥
                // user_key	string	观众登录秘钥
                // add_time	int	直播创建时间
                // course_id	int	直播id

                // 保存直播课程记录
                JSONObject object = commonResult.getData();
                liveCourse.setCourseId(object.getLong("course_id"));
                baseMapper.insert(liveCourse);

                // 保存直播课程详情记录
                final LiveCourseDescription liveCourseDescription = new LiveCourseDescription();
                liveCourseDescription.setLiveCourseId(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                liveCourseDescriptionService.save(liveCourseDescription);

                // 保存直播课程账号记录
                final LiveCourseAccount liveCourseAccount = new LiveCourseAccount();
                liveCourseAccount.setLiveCourseId(liveCourse.getId());
                liveCourseAccount.setZhuboAccount(object.getString("bid"));
                liveCourseAccount.setZhuboPassword(liveCourseFormVo.getPassword());
                liveCourseAccount.setZhuboKey(object.getString("zhubo_key"));
                liveCourseAccount.setAdminKey(object.getString("admin_key"));
                liveCourseAccount.setUserKey(object.getString("user_key"));
                liveCourseAccountService.save(liveCourseAccount);
            }
            else {
                final String msg = commonResult.getmsg();
                log.error("新增直播课程失败: " + msg);
                throw new GgktException(20001, "新增直播课程失败: " + msg);
            }
        }
        catch (Exception e) {
            log.error("新增直播课程失败: " + e.getMessage(), e);
        }
    }

    /**
     * 删除直播
     *
     * @param id 编号
     */
    @Override
    public void removeLive(Long id) {
        final LiveCourse liveCourse = baseMapper.selectById(id);
        if (liveCourse == null) {
            return;
        }

        try {
            final Long courseId = liveCourse.getCourseId();
            // 调用方法删除平台直播课程
            mtCloudClient.courseDelete(courseId.toString());
            // 删除表数据
            baseMapper.deleteById(id);
        }
        catch (Exception e) {
            log.error("删除直播课程失败: " + e.getMessage(), e);
            throw new GgktException(20001, "删除直播课程失败");
        }
    }

    /**
     * id查询直播课程基本信息和描述信息
     *
     * @param id 编号
     * @return {@link LiveCourseFormVo}
     */
    @Override
    public LiveCourseFormVo getLiveCourseFormVo(Long id) {
        // 直播课程基本信息
        final LiveCourse liveCourse = baseMapper.selectById(id);
        // 直播课程详情信息
        final LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(id);

        // 返回数据
        final LiveCourseFormVo liveCourseFormVo = new LiveCourseFormVo();
        BeanUtils.copyProperties(liveCourse, liveCourseFormVo);
        if (liveCourseDescription != null && !StringUtils.isEmpty(liveCourseDescription.getDescription())) {
            liveCourseFormVo.setDescription(liveCourseDescription.getDescription());
        }
        return liveCourseFormVo;
    }

    /**
     * 按 ID 更新直播
     *
     * @param liveCourseFormVo 现场课程表格 VO
     */
    @Override
    public void updateLiveById(LiveCourseFormVo liveCourseFormVo) {
        // 直播课程基本信息
        LiveCourse liveCourse = baseMapper.selectById(liveCourseFormVo.getId());
        BeanUtils.copyProperties(liveCourseFormVo, liveCourse);
        // 讲师信息
        Teacher teacher = teacherFeignClient.getTeacherInfo(liveCourseFormVo.getTeacherId());

        // course_id	int	Y	直播id
        // account	String	Y	接入方主播账号或ID或手机号等
        // course_name	String	Y	直播名称
        // start_time	int	Y	直播开始时间,格式: 2015-01-10 12:00:00
        // end_time	int	Y	直播结束时间,格式: 2015-01-10 13:00:00
        // nickname	string	Y	主播的昵称
        // accountIntro	string	N	主播的简介
        // options	object	N	其它可选参数
        HashMap<Object, Object> options = new HashMap<>();
        try {
            final String courseId = liveCourse
                    .getCourseId()
                    .toString();
            final String account = teacher
                    .getId()
                    .toString();
            final String courseName = liveCourse.getCourseName();
            final String startTime = new DateTime(liveCourseFormVo.getStartTime()).toString("yyyy-MM-dd HH:mm:ss");
            final String endTime = new DateTime(liveCourseFormVo.getEndTime()).toString("yyyy-MM-dd HH:mm:ss");
            final String nickName = teacher.getName();
            final String accountIntro = teacher.getIntro();
            final String res = mtCloudClient.courseUpdate(courseId, account, courseName, startTime, endTime, nickName, accountIntro, options);
            log.info("更新直播课程返回: " + res);

            // 返回参数说明：code=0为成功，其他值为失败
            final CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if (Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                // 更新直播课程基本信息
                final JSONObject data = commonResult.getData();
                liveCourse.setCourseId(data.getLong("course_id"));
                baseMapper.updateById(liveCourse);

                // 直播课程描述信息更新
                final LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getLiveCourseById(liveCourse.getCourseId());
                if (liveCourseDescription != null && !StringUtils.isEmpty(liveCourseDescription.getDescription())) {
                    liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                    liveCourseDescriptionService.updateById(liveCourseDescription);
                }
            }
            else {
                final String msg = commonResult.getmsg();
                log.error("修改直播课程失败: " + msg);
                throw new GgktException(20001, "修改直播课程失败: " + msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按 ID 获取课程配置
     *
     * @param id 编号
     * @return {@link LiveCourseConfigVo}
     */
    @Override
    public LiveCourseConfigVo getCourseConfigById(Long id) {
        LiveCourseConfig liveCourseConfig = liveCourseConfigService.getCourseConfigCourseById(id);
        final LiveCourseConfigVo liveCourseConfigVo = new LiveCourseConfigVo();
        if (liveCourseConfig != null) {
            List<LiveCourseGoods> liveCourseGoodsList = liveCourseGoodsService.getGoodsListCourseById(id);
            BeanUtils.copyProperties(liveCourseConfig, liveCourseConfigVo);
            liveCourseConfigVo.setLiveCourseGoodsList(liveCourseGoodsList);
        }
        return liveCourseConfigVo;
    }

    /**
     * 更新配置
     *
     * @param liveCourseConfigVo 直播课程配置 VO
     */
    @Override
    public void updateConfig(LiveCourseConfigVo liveCourseConfigVo) {
        // 1 修改直播配置表
        final LiveCourseConfig liveCourseConfig = new LiveCourseConfig();
        BeanUtils.copyProperties(liveCourseConfigVo, liveCourseConfig);
        if (liveCourseConfigVo.getId() != null) {
            liveCourseConfigService.updateById(liveCourseConfig);
        }
        else {
            liveCourseConfigService.save(liveCourseConfig);
        }

        // 2 修改直播商品表
        liveCourseGoodsService.remove(new LambdaQueryWrapper<LiveCourseGoods>().eq(LiveCourseGoods::getLiveCourseId, liveCourseConfigVo.getLiveCourseId()));
        final List<LiveCourseGoods> liveCourseGoodsList = liveCourseConfigVo.getLiveCourseGoodsList();
        if (!CollectionUtils.isEmpty(liveCourseGoodsList)) {
            liveCourseGoodsService.saveBatch(liveCourseGoodsList);
        }

        // 3 修改在直播平台
        this.updateLiveConfig(liveCourseConfigVo);
    }

    /**
     * 获取最近的直播
     *
     * @return {@link List}<{@link LiveCourseVo}>
     */
    @Override
    public List<LiveCourseVo> findLatelyList() {
        List<LiveCourseVo> liveCourseVoList = baseMapper.getLatelyList();
        liveCourseVoList.forEach(liveCourseVo -> {
            liveCourseVo.setStartTimeString(new DateTime(liveCourseVo.getStartTime()).toString("yyyy年MM月dd HH:mm"));
            liveCourseVo.setEndTimeString(new DateTime(liveCourseVo.getEndTime()).toString("HH:mm"));
            liveCourseVo.setTeacher(teacherFeignClient.getTeacherInfo(liveCourseVo.getTeacherId()));
            liveCourseVo.setLiveStatus(this.getLiveStatus(liveCourseVo));
        });
        return liveCourseVoList;
    }

    private int getLiveStatus(LiveCourseVo liveCourseVo) {
        // 直播状态 0：未开始 1：直播中 2：直播结束
        int liveStatus = 0;
        Date curTime = new Date();
        if (DateUtil.dateCompare(curTime, liveCourseVo.getStartTime())) {
            liveStatus = 0;
        }
        else if (DateUtil.dateCompare(curTime, liveCourseVo.getEndTime())) {
            liveStatus = 1;
        }
        else {
            liveStatus = 2;
        }
        return liveStatus;
    }

    /**
     * 更新直播配置
     *
     * @param liveCourseConfigVo 直播课程配置 VO
     */
    private void updateLiveConfig(LiveCourseConfigVo liveCourseConfigVo) {
        final LiveCourse liveCourse = baseMapper.selectById(liveCourseConfigVo.getLiveCourseId());

        // 参数设置
        HashMap<Object, Object> options = new HashMap<>();
        // pageViewMode 界面模式
        // 0二分屏
        // 1全屏模式
        // 2课件模式
        options.put("pageViewMode", liveCourseConfigVo.getPageViewMode());
        // number 观看人数开关；
        // number.enable 是否开启 观看人数
        // 0否 1是；示例：{"enable":"1"}
        JSONObject number = new JSONObject();
        number.put("enable", liveCourseConfigVo.getNumberEnable());
        options.put("number", number.toJSONString());
        // store 商城；
        // store.enable是否开启 0未开启 1开启，
        // store.type 1商品列表,2商城链接,3商城二维码；
        // store.data.url 存储商城链接；
        // store.data.qrcode 商城二维码；
        // 示例：{"enable":1,"type":"1","data":{"url":"","qrcode":""}}
        JSONObject store = new JSONObject();
        store.put("enable", liveCourseConfigVo.getStoreEnable());
        store.put("type", liveCourseConfigVo.getStoreType());
        options.put("store", store.toJSONString());

        // 商城列表
        final List<LiveCourseGoods> liveCourseGoodsList = liveCourseConfigVo.getLiveCourseGoodsList();
        if (!CollectionUtils.isEmpty(liveCourseGoodsList)) {
            final List<LiveCourseGoodsView> liveCourseGoodsViewList = liveCourseGoodsList
                    .stream()
                    .map(liveCourseGoods -> {
                        LiveCourseGoodsView liveCourseGoodsView = new LiveCourseGoodsView();
                        BeanUtils.copyProperties(liveCourseGoods, liveCourseGoodsView);
                        return liveCourseGoodsView;
                    })
                    .collect(Collectors.toList());

            // goodsListEdit 商品列表编辑
            // 状态goodsListEdit.status
            // 0覆盖，1追加，不传默认为0；
            // 示例：{"status":1}
            JSONObject goodsListEdit = new JSONObject();
            goodsListEdit.put("status", "0");
            options.put("goodsListEdit", goodsListEdit.toJSONString());
            // goodsList 商品列表，最多可添加70个商品。
            // 应保证每个商品有以下字段: id,name, img, price, originalPrice, tab, url, putaway,pay,qrcode。
            // 分别表示: 商品id,商品名称, 商品图片地址, 商品现价, 商品原价, 商品标签(特价:1, 限时:2, 新品:3, 钜惠:4, 秒杀:5),
            //          商品链接, 商品状态（0下架，1上架，2推荐,3取消推荐）， 购买模式(1,链接购买 2,二维码购买), 商品二维码；
            // 示例：
            // [
            //     {
            //         "id": "123",
            //         "name": "test",
            //         "img": "",
            //         "price": "1",
            //         "originalPrice": "0",
            //         "tab": "1",
            //         "url": "",
            //         "putaway": "2"
            //     }
            // ]
            options.put("goodsList", JSON.toJSONString(liveCourseGoodsViewList));
        }
        log.info("修改生活直播相关配置参数: " + options);

        try {
            // 修改生活直播相关配置
            final String res = mtCloudClient.courseUpdateLifeConfig(liveCourse
                    .getCourseId()
                    .toString(), options);
            log.info("修改生活直播相关配置结果: " + res);

            final CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if (Integer.parseInt(commonResult.getCode()) != MTCloud.CODE_SUCCESS) {
                final String msg = commonResult.getmsg();
                log.error("修改生活直播相关配置失败: " + msg);
                throw new GgktException(20001, "修改生活直播相关配置失败: " + msg);
            }
        }
        catch (Exception e) {
            log.error("修改生活直播相关配置失败: " + e.getMessage(), e);
        }
    }
}
