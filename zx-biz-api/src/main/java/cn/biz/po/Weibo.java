package cn.biz.po;

import java.time.LocalDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zx
 * @since 2020-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Weibo对象", description = "Weibo")
public class Weibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微博id")
    private String userId;

    @ApiModelProperty(value = "微博名称")
    private String screenName;

    @ApiModelProperty(value = "微博类容")
    private String text;

    @ApiModelProperty(value = "话题")
    private String topics;

    @ApiModelProperty(value = "图片")
    private String pics;

    @ApiModelProperty(value = "发布时间")
    private String createdAt;

    @ApiModelProperty(value = "发布来源")
    private String source;

    @ApiModelProperty(value = "点赞数")
    private String attitudesCount;

    @ApiModelProperty(value = "评论数")
    private String commentsCount;

    @ApiModelProperty(value = "转发数")
    private String repostsCount;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
