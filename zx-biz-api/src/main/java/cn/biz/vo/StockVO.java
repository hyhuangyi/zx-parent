package cn.biz.vo;

import lombok.Data;

@Data
public class StockVO {
    private String sh000001="0 %";//上证
    private String sz399001="0 %";//深证
    private String sz399006="0 %";//创业板
    private String turnover="0 亿";//两市成交额
}
