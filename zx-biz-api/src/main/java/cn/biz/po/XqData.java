package cn.biz.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 雪球数据
 * @author zx
 * @since 2021-01-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="XqData对象", description="雪球数据")
public class XqData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日期")
    @TableId(value = "date", type = IdType.AUTO)
    private String date;

    @ApiModelProperty(value = "代码")
    private String symbol;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "现价")
    private Double current;

    @ApiModelProperty(value = "涨幅")
    private Double percent;

    @ApiModelProperty(value = "振幅")
    private Double amplitude;

    @ApiModelProperty(value = "成交额")
    private String amount;

    @ApiModelProperty(value = "量比")
    private Double volumeRatio;

    @ApiModelProperty(value = "换手")
    private Double turnoverRate;

    @ApiModelProperty(value = "市值")
    private String marketCapital;

    @ApiModelProperty(value = "年初至今")
    private Double currentYearPercent;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(exist = false)
    private Long count;
}
