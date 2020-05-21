package cn.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class TableListVO {
    @ApiModelProperty("表名")
    private String tableName;
    @ApiModelProperty("表注释")
    private String tableComment;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
}
