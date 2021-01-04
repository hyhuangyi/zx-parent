package cn.biz.mapper;

import cn.biz.po.XqData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 雪球数据 Mapper 接口
 *
 * @author zx
 * @since 2021-01-04
 */
@Repository
public interface XqDataMapper extends BaseMapper<XqData> {
    int insertBatch( List<XqData> xqDataList);
}
