package xyz.funnyboy.ggkt.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.funnyboy.ggkt.model.live.LiveCourseConfig;
import xyz.funnyboy.ggkt.model.live.LiveCourseGoods;

import java.util.List;

/**
 * 直播课程配置 VO
 *
 * @author VectorX
 * @version 1.0.0
 * @date 2024/01/22
 * @see LiveCourseConfig
 */
@Data
@ApiModel(description = "LiveCourseConfig")
public class LiveCourseConfigVo extends LiveCourseConfig
{

    @ApiModelProperty(value = "商品列表")
    private List<LiveCourseGoods> liveCourseGoodsList;
}
