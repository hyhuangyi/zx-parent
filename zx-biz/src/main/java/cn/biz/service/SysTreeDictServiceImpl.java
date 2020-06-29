package cn.biz.service;

import cn.biz.mapper.SysTreeDictMapper;
import cn.biz.vo.DictVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Service
public class SysTreeDictServiceImpl implements ISysTreeDictService {
    @Autowired
    private SysTreeDictMapper sysTreeDictMapper;

    @Override
    public Map<String, List<DictVO>> listSubDicts(String code, String value) {
        Map<String, List<DictVO>> map = Maps.newHashMap();
        if (code != null && value != null) {
            String[] split = value.split(",");
            for (String str : split) {
                List<DictVO> list = sysTreeDictMapper.listSubDicts(code, str);
                map.put(str, list);
            }
        }
        return map;
    }

    @Override
    public Map<String, List<DictVO>> listDictsByCode(String code) {
        return listDictsHasLevel(code, true);
    }

    @Override
    public Map<String, List<DictVO>> listDicts(String code) {
        return listDictsHasLevel(code, false);
    }


    private Map<String, List<DictVO>> listDictsHasLevel(String code, boolean level) {
        Map<String, List<DictVO>> map = Maps.newHashMap();
        if (code != null) {
            String[] split;
            if (code.contains("/")) {
                split = code.split("/");
            } else {
                split = code.split(",");
            }
            for (String str : split) {
                List<DictVO> list = sysTreeDictMapper.listDictsByCode(str);
                if (level) {
                    List<DictVO> ret = listSubDicts(list);
                    map.put(str, ret);
                } else {
                    map.put(str, list);
                }
            }
        }
        return map;
    }


    public List<DictVO> listSubDicts(List<DictVO> list) {
        List<DictVO> ret = Lists.newArrayList();
        for (DictVO dictVO : list) {
            if ("0".equals(dictVO.getParentValue())) {
                getChild(dictVO, list);
                ret.add(dictVO);
            }
        }
        return ret;
    }
    /*对子集处理*/
    private void getChild(DictVO dictVO, List<DictVO> list) {
        List<DictVO> children = dictVO.getDictDTOS();
        for (DictVO vo : list) {//获取子集
            if (vo.getParentValue().equals(dictVO.getDdValue())) {
                children.add(vo);
            }
        }
        if (!CollectionUtils.isEmpty(children)) {
            for (DictVO child : children) {
                getChild(child, list);
            }
        }
    }
}
