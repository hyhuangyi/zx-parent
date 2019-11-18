package cn.biz.service;
import cn.biz.vo.DictVO;
import java.util.List;
import java.util.Map;

/**
 * 树状字典表 服务类
 * @author huangy
 * @since 2019-11-18
 */
public interface ISysTreeDictService  {
    /**
     * 根据字典代码获取字典值列表,带层级
     * @param code
     * @return
     */
    Map<String, List<DictVO>> listDictsByCode(String code);
    /**
     * 根据字典代码获取字典所有项，不带层级
     * @param code
     * @return
     */
    Map<String,List<DictVO>> listDicts(String code);
    /**
     * 根据字典代码获取字典二级项
     * @param code
     * @return
     */
    Map<String,List<DictVO>> listSubDicts(String code, String value);
}
