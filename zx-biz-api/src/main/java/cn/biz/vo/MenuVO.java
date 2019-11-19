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
    private Integer parentId;
    @ApiModelProperty(value = "菜单URL")
    private String menuUrl;
    @ApiModelProperty("子菜单")
    private List<MenuVO> subMenu= Lists.newArrayList();
}
