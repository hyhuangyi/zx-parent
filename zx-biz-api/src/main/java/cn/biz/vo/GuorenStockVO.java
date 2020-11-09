package cn.biz.vo;

import lombok.Data;

@Data
public class GuorenStockVO {
    private String code;
    private String name;
    private Double price;
    private Double rate;

    public GuorenStockVO(String code, String name, Double price, Double rate) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.rate = rate;
    }
}
