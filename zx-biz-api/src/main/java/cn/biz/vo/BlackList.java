package cn.biz.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackList implements Serializable {
    private static final long serialVersionUID = 7637066289484530260L;
    @Excel(name="备注")
    private String remark;
    @Excel(name="手机号码")
    private String phone;
    @Excel(name="身份证号码")
    private String cardNo;
}
