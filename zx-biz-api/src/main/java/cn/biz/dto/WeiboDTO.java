package cn.biz.dto;

import cn.common.pojo.base.PagingQuest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeiboDTO extends PagingQuest {
    @ApiModelProperty("关键词")
    private String  topic;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
}
