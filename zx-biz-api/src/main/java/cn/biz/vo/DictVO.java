package cn.biz.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class DictVO {
    @ApiModelProperty("字典类型")
    private String ddItem;
    @ApiModelProperty("字典值")
    private String ddValue;
    @ApiModelProperty("字典文本")
    private String ddText;
    @ApiModelProperty("顺序")
    private Integer index;
    @ApiModelProperty("父级字典值")
    private String parentValue;
    private List<DictVO> dictDTOS= Lists.newArrayList();
}
