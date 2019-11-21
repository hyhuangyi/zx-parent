package cn.biz.po;

import cn.biz.group.ZxFirst;
import cn.biz.group.ZxSecond;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 城市表
 * @author huangy
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CdCity对象", description="城市表")
public class CdCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "不能为空!")
    @Size(min=2,max=8,message = "大小在2-8之间")
    @ApiModelProperty(value = "code")
    @TableId(value = "code", type = IdType.AUTO)
    private String code;

    @NotEmpty(message = "不能为空!",groups = {ZxFirst.class})
    @ApiModelProperty(value = "全名")
    private String fullName;

    @NotEmpty(message = "不能为空!",groups = {ZxSecond.class})
    @ApiModelProperty(value = "短名")
    private String shortName;

    @ApiModelProperty(value = "全拼")
    private String py;

    @ApiModelProperty(value = "省份code")
    private String provCode;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "更新人")
    private Integer updateUser;


}
