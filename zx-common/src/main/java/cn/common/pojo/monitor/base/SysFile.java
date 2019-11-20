package cn.common.pojo.monitor.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统文件相关信息
 */
@Data
public class SysFile {
    @ApiModelProperty("盘符路径")
    private String dirName;
    @ApiModelProperty("盘符类型")
    private String sysTypeName;
    @ApiModelProperty("文件系统")
    private String typeName;
    @ApiModelProperty("总大小")
    private String total;
    @ApiModelProperty("剩余大小(可用大小)")
    private String free;
    @ApiModelProperty("已经使大小")
    private String used;
    @ApiModelProperty("资源使用率")
    private double usage;
}
