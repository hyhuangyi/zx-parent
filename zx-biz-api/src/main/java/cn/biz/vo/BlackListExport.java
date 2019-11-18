package cn.biz.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huangy
 * @date 2019/6/28 14:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackListExport {
    @Excel(name = "客户姓名", width = 15, orderNum = "2")
    private String name;
    @Excel(name = "备注", width = 10, orderNum = "1")
    private String remark;
    @Excel(name = "手机号", width = 15, orderNum = "0")
    private String phone;
}
