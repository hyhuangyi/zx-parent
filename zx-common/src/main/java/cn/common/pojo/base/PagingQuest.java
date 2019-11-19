package cn.common.pojo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.Max;

/**
 * 分页查询请求参数
 * @author huangyi
 */
@Data
public class PagingQuest {
    @ApiModelProperty(value = "第几页 默认1")
    private Long current =1L;
    @ApiModelProperty(value = "每页记录数 默认10")
    @Max(value=10000L)
    private Long size=10L;
}
