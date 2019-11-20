package cn.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserStatusDTO {
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("传 0 启用  传 1禁用")
    @Min(0)
    @Max(1)
    private Integer status;
}
