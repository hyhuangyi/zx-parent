package cn.biz.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class GuorenStockVO implements Comparable<GuorenStockVO> {
    @Excel(name = "股票代码", width = 15, orderNum = "1")
    private String code;
    @Excel(name = "股票名称", width = 15, orderNum = "2")
    private String name;
    @Excel(name = "最新价格", width = 15, orderNum = "3")
    private Double price;
    @Excel(name = "最新涨幅", width = 15, orderNum = "4")
    private Double rate;
    @Excel(name = "行业", width = 15, orderNum = "5")
    private String industry;

    public GuorenStockVO(String code, String name, Double price, Double rate, String industry) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.rate = rate;
        this.industry = industry;
    }

    @Override
    public int compareTo(GuorenStockVO o) {
        double i=o.getRate()-this.getRate();
        if(i==0){
            return 0;
        }else if(i>0){
            return 1;
        }else {
            return -1;
        }
    }
}
