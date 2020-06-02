package cn.biz.mapper;

import cn.biz.po.Fund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 基金表 Mapper 接口
 * </p>
 *
 * @author zx
 * @since 2020-06-02
 */
@Repository
public interface FundMapper extends BaseMapper<Fund> {
    Boolean batchInsertOrUpdate(List<Fund> list);
}
