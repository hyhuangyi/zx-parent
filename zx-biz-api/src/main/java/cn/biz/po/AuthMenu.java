package cn.biz.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 菜单表
 * @author huangy
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AuthMenu对象", description="菜单表")
public class AuthMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "上级菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "菜单URL")
    private String menuUrl;

    @ApiModelProperty(value = "排序号")
    private Integer sort;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "1 删除 0 未删除")
    private Integer isDel;

    @ApiModelProperty(value = "0菜单 1按钮")
    private Integer type;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
