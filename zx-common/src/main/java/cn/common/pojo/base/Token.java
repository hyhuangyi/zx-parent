package cn.common.pojo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * Created by huangYi on 2018/9/5
 **/
@Data
public class Token implements Serializable {
    @ApiModelProperty(value = "uuid",hidden = true)
    private String uuid;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("用户token")
    private String token;
    @ApiModelProperty("角色")
    private String[] roles = new String[0];
    @ApiModelProperty("权限")
    private List<String> permissions;
}
