package cn.biz.dto;

import cn.common.pojo.base.PagingQuest;
import lombok.Data;

@Data
public class FundDTO extends PagingQuest {
    private String type;
    private String name;
}
