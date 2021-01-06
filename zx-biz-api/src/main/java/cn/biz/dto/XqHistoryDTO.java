package cn.biz.dto;

import cn.common.pojo.base.PagingQuest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class XqHistoryDTO extends PagingQuest {
    @ApiModelProperty("日期")
    @NotEmpty(message = "日期不能为空")
    private String date;
    @ApiModelProperty("涨幅")
    @NotNull(message = "涨幅不能为空")
    private Double percent;
}
