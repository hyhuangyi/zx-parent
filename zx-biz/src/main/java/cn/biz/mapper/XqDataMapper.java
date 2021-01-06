package cn.biz.mapper;

import cn.biz.dto.WeiboDTO;
import cn.biz.dto.XqHistoryDTO;
import cn.biz.po.Weibo;
import cn.biz.po.XqData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
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

    List<XqData> getXqHistoryList(Page page, @Param("dto") XqHistoryDTO dto);
}
