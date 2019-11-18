package cn.biz.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 树状字典表
 * @author huangy
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysTreeDict对象", description="树状字典表")
public class SysTreeDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "dd_id", type = IdType.AUTO)
    private Integer ddId;

    @ApiModelProperty(value = "字典项,一组字典值的唯一标识")
    private String ddItem;

    @ApiModelProperty(value = "字典展示文本")
    private String ddText;

    @ApiModelProperty(value = "字典值")
    private String ddValue;

    @ApiModelProperty(value = "排序字段")
    private Integer ddIndex;

    @ApiModelProperty(value = "父级字典值")
    private String parentValue;

    @ApiModelProperty(value = "编辑人")
    private Integer updateUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    @ApiModelProperty(value = "删除标识")
    private Boolean isDel;


}
