package cn.biz.vo;

import cn.common.pojo.base.Token;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
public class ZxToken extends Token {
    @ApiModelProperty("角色集合")
    private List<UserRoleVO> roleList;
    @ApiModelProperty("菜单集合")
    private List<MenuVO> menuVOList;
}
