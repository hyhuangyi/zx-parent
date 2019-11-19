package cn.biz.vo;

import cn.biz.po.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserListVO extends SysUser {
    @ApiModelProperty("角色名称")
    private String roleNames;
    @ApiModelProperty("角色id")
    private String roleIds;
}
