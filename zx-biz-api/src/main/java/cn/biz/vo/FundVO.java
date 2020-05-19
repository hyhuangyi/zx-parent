package cn.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundVO implements Comparable<FundVO>{
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("code")
    private String fundcode;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("净值日期")
    private String jzrq;
    @ApiModelProperty("单位净值")
    private String dwjz;
    @ApiModelProperty("估算值")
    private String gsz;
    @ApiModelProperty("估算值值率")
    private double gszzl;
    @ApiModelProperty("估值时间")
    private String gztime;
    @ApiModelProperty("持有金额")
    private double remark;
    @ApiModelProperty("利润")
    private BigDecimal ly;
    @Override
    public int compareTo(FundVO o) {
        double i=o.getGszzl()-this.getGszzl();
        if(i==0){
            return 0;
        }else if(i>0){
            return 1;
        }else {
            return -1;
        }
    }
}
