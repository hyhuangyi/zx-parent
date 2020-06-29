package cn.biz.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundVO implements Comparable<FundVO>{
    @ApiModelProperty("id")
    private Long id;
    @Excel(name = "基金代码", width = 15, orderNum = "1")
    @ApiModelProperty("code")
    private String fundcode;
    @Excel(name = "基金名称", width = 15, orderNum = "2")
    @ApiModelProperty("名称")
    private String name;
    @Excel(name = "净值日期", width = 15, orderNum = "3")
    @ApiModelProperty("净值日期")
    private String jzrq;
    @Excel(name = "单位净值", width = 15, orderNum = "4")
    @ApiModelProperty("单位净值")
    private String dwjz;
    @Excel(name = "估算值", width = 15, orderNum = "6")
    @ApiModelProperty("估算值")
    private String gsz;
    @Excel(name = "估算值值率", width = 15, orderNum = "7")
    @ApiModelProperty("估算值值率")
    private double gszzl;
    @Excel(name = "估值时间", width = 15, orderNum = "5")
    @ApiModelProperty("估值时间")
    private String gztime;
    @ApiModelProperty("持有金额")
    private double holdMoney;
    @ApiModelProperty("备注")
    private String remark;
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
