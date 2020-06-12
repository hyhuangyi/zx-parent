package cn.biz.mapper;

import cn.biz.dto.WeiboDTO;
import cn.biz.po.Weibo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zx
 * @since 2020-06-01
 */
@Repository
public interface WeiboMapper extends BaseMapper<Weibo> {
    List<Weibo> getWeiboList(Page page, @Param("dto") WeiboDTO dto);
    Boolean batchInsertOrUpdate(List<Weibo> list);
}
