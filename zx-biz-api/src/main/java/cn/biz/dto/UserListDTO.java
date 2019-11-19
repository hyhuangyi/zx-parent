package cn.biz.dto;

import cn.common.pojo.base.PagingQuest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserListDTO extends PagingQuest {
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
}
