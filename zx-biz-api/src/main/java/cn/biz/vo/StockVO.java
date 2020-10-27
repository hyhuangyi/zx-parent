package cn.biz.vo;

import lombok.Data;

@Data
public class StockVO {
    private Double shangz=0.0;//上证
    private Double shenz=0.0;;//深证
    private Double chuangy=0.0;//创业板
    private Double turnOver=0.0;//两市成交额
}
