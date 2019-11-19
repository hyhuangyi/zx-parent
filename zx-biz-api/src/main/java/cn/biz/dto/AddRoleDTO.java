package cn.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
@Data
public class AddRoleDTO {
    @ApiModelProperty(value = "角色主键 新增不需要传，编辑需要传")
    private String id;
    @ApiModelProperty("角色名称")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;
    @ApiModelProperty(value = "菜单权限集合")
    private String menuList;
}
