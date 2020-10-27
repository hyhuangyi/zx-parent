package cn.biz.mapper;

import cn.biz.po.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * 大盘信息 Mapper 接口
 *
 */
@Repository
public interface StockMapper extends BaseMapper<Stock> {
    //获取最近7天的日期
    List<String> getDate();
    //获取最近7天的数据
    List<Map> getData();
}
