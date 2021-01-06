package cn.biz.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "代码")
    private String symbol;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "最新涨幅")
    private Double percent;

    @ApiModelProperty(value = "最新价")
    private Double current;

    @ApiModelProperty(value = "年初至今")
    private Double currentYearPercent;

    @ApiModelProperty(value = "市值")
    private String marketCapital;

    @ApiModelProperty(value = "换手率")
    private Double turnoverRate;

    @ApiModelProperty(value = "日期")
    private String date;

    @TableField(exist = false)
    private Long count;

    public XqData() {
    }

    public XqData(String symbol, String name, Double percent, Double current, Double currentYearPercent, String marketCapital, Double turnoverRate, String date) {
        this.symbol = symbol;
        this.name = name;
        this.percent = percent;
        this.current = current;
        this.currentYearPercent = currentYearPercent;
        this.marketCapital = marketCapital;
        this.turnoverRate = turnoverRate;
        this.date = date;
    }
}
