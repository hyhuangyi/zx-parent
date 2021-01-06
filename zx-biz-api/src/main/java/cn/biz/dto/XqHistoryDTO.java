package cn.biz.dto;

import cn.common.pojo.base.PagingQuest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class XqHistoryDTO extends PagingQuest {
    @ApiModelProperty("日期")
    @NotEmpty(message = "日期不能为空")
    private String date;
    @ApiModelProperty("涨幅")
    @NotEmpty(message = "涨幅不能为空")
    private String percent;
}
