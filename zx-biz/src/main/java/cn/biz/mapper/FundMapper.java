package cn.biz.mapper;

import cn.biz.dto.FundDTO;
import cn.biz.po.Fund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 基金表 Mapper 接口
 * @author zx
 * @since 2020-06-02
 */
@Repository
public interface FundMapper extends BaseMapper<Fund> {
    Boolean batchInsertOrUpdate(List<Fund> list);
    List<Fund> getAllFund( Page page,@Param("dto") FundDTO dto);
    @Select("select type from fund group by type")
    List<String>getFundType();
    @Select("select * from fund where type  in('股票型','股票指数','联接基金','混合型','QDII-指数')")
    List<Fund> getFundForZero();
}
