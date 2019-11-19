package cn.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class RoleVO {
    @ApiModelProperty(value = "角色主键")
    private Long id;
    @ApiModelProperty(value = "角色code")
    private String roleCode;
    @ApiModelProperty("角色名称")
    private String roleName;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "菜单权限集合")
    private List<String> menuList;
}
