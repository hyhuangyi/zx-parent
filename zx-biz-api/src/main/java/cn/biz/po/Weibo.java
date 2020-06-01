package cn.biz.po;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="Weibo对象", description="Weibo对象")
public class Weibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String bid;

    private String userId;

    private String screenName;

    private String text;

    private String articleUrl;

    private String topics;

    private String atUsers;

    private String pics;

    private String videoUrl;

    private String location;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private String source;

    private Integer attitudesCount;

    private Integer commentsCount;

    private Integer repostsCount;

    private String retweetId;


}
