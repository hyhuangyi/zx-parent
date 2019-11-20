package cn.common.pojo.monitor.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class Sys {
    @ApiModelProperty("服务器名称")
    private String computerName;
    @ApiModelProperty("服务器Ip")
    private String computerIp;
    @ApiModelProperty("项目路径")
    private String userDir;
    @ApiModelProperty("操作系统")
    private String osName;
    @ApiModelProperty("系统架构")
    private String osArch;
}
