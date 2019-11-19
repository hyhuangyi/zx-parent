package cn.biz.dto;

import cn.biz.group.ZxSecond;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddUserDTO {
    @ApiModelProperty("用户id 编辑传")
    @NotNull(message = "id不能为空",groups = {ZxSecond.class})
    private String id;
    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("手机号")
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @ApiModelProperty("email")
    @NotEmpty(message = "email不能为空")
    private String email;
    @ApiModelProperty("角色")
    @NotEmpty(message = "角色不能为空")
    private List<String> roles;
}
