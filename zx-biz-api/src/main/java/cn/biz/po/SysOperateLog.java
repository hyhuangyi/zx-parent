package cn.biz.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  操作日志
 * @author huangy
 * @since 2019-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysOperateLog对象", description="操作日志")
public class SysOperateLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "ip")
    private String operateIp;

    @ApiModelProperty(value = "url")
    private String operateUrl;

    @ApiModelProperty(value = "用户id")
    private Long operateUserId;

    @ApiModelProperty(value = "用户姓名")
    private String operateUserName;

    @ApiModelProperty(value = "方法")
    private String operateMethod;

    @ApiModelProperty(value = "操作内容")
    private String operateAction;

    @ApiModelProperty(value = "模块")
    private String operateModule;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operateTime;

}
