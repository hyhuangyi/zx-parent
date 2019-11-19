package cn.common.pojo.base;

import lombok.Data;
import java.util.List;

/**
 * Created by huangYi on 2018/11/1.
 **/
@Data
public class PageResultDO<T> extends ResultDO {
    private Long recordsTotal;
    private List<T> data;
    public PageResultDO(List<T> data, Long recordsTotal) {
        super("200","成功");
        this.recordsTotal = recordsTotal;
        this.data = data;
    }
}
