package cn.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRoleVO {
    @ApiModelProperty("角色id")
    private String roleId;
    @ApiModelProperty("角色名称")
    private String roleName;

}
