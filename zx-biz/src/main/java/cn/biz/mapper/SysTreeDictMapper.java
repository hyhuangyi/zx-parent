package cn.biz.mapper;

import cn.biz.po.SysTreeDict;
import cn.biz.vo.DictVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 树状字典表 Mapper 接口
 * @author huangy
 * @since 2019-11-18
 */
@Repository
public interface SysTreeDictMapper extends BaseMapper<SysTreeDict> {
    List<DictVO> listDictsByCode(String code);
    List<DictVO> listSubDicts(@Param("ddItem")String ddItem, @Param("ddValue") String ddValue);
}
