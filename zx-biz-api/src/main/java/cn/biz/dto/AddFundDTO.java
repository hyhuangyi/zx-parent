package cn.biz.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class AddFundDTO {
    @NotEmpty(message = "基金信息不能为空")
    @ApiModelProperty("基金信息")
    private String fund;
    @ApiModelProperty("备注")
    private String remark="-";
    @ApiModelProperty("持有金额")
    private String holdNum="0";
}
