package cn.biz.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class DictVO {
    @ApiModelProperty("id")
    private Integer ddId;
    @ApiModelProperty("字典类型")
    private String ddItem;
    @ApiModelProperty("字典值")
    private String ddValue;
    @ApiModelProperty("字典文本")
    private String ddText;
    @ApiModelProperty("顺序")
    private Integer ddIndex;
    @ApiModelProperty("父级字典值")
    private String parentValue;
    @ApiModelProperty("备注")
    private String remark;
    private List<DictVO> dictDTOS= Lists.newArrayList();
}
