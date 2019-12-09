package cn.webapp.controller.biz;

import cn.biz.service.ISysTreeDictService;
import cn.biz.vo.DictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags= "数据字典")
public class SysTreeDictController {
    @Autowired
    private ISysTreeDictService sysTreeDictService;

    @ApiOperation(value = "根据字典代码获取字典列表,带层级关系")
    @RequestMapping(value = "/comm/dict/items", method = RequestMethod.GET)
    public Map<String, List<DictVO>> listDictsByCode(
            @ApiParam(value = "字典代码，用逗号分隔", required = true) @RequestParam @NotEmpty String code) {
        return sysTreeDictService.listDictsByCode(code);
    }

    @ApiOperation(value = "根据字典代码获取字典所有项，不带层级关系")
    @RequestMapping(value = "/comm/dict/items/all", method = RequestMethod.GET)
    public Map<String, List<DictVO>> listDicts(
            @ApiParam(value = "字典代码，用逗号分隔", required = true) @RequestParam String code) {
        return sysTreeDictService.listDicts(code);
    }

    @ApiOperation(value = "根据字典代码及一级字典值获取二级字典所有项")
    @RequestMapping(value = "/comm/subDict/items", method = RequestMethod.GET)
    public Map<String, List<DictVO>> listSubDicts(
            @ApiParam(value = "字典代码,如'ssq'", required = true) @RequestParam String code,
            @ApiParam(value = "一级字典值，如杭州为'330100',可填写多个，逗号隔开", required = true) @RequestParam String value) {
        return sysTreeDictService.listSubDicts(code, value);
    }
}
