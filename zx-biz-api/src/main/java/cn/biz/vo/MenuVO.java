package cn.biz.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVO implements Serializable {

    @ApiModelProperty(value = "菜单ID")
    private Long id;
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @ApiModelProperty(value = "上级菜单ID")
    private Long parentId;
    @ApiModelProperty(value = "菜单URL")
    private String menuUrl;
    @ApiModelProperty(value = "菜单图标")
    private String icon;
    @ApiModelProperty(value = "类型 0菜单 1按钮")
    private Integer type;
    @ApiModelProperty("子菜单")
    private List<MenuVO> subMenu= Lists.newArrayList();
}
