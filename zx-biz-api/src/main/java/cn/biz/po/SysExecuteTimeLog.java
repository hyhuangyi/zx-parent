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
 * 执行时间表
 * @author huangy
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysExecuteTimeLog对象", description="")
public class SysExecuteTimeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "执行时间")
    private Integer executeTime;

    @ApiModelProperty(value = "执行方法")
    private String executeMethod;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operateTime;


}
