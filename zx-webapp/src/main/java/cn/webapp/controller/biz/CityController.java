package cn.webapp.controller.biz;

import cn.biz.mapper.CdCityMapper;
import cn.biz.po.CdCity;
import cn.common.consts.LogModuleConst;
import cn.common.util.string.StringUtils;
import cn.webapp.aop.annotation.OperateLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(tags = "城市相关接口")
@RestController
public class CityController {
    @Autowired
    private CdCityMapper cityMapper;

    @GetMapping("/city/list")
    @ApiOperation("城市分页列表")
    @PreAuthorize("hasAuthority('city:list')")
    @OperateLog(operation = "查询城市列表",moduleName = LogModuleConst.BIZ_MODULE)
    public IPage<CdCity> list(@RequestParam(required = false,defaultValue = "1") long current, @RequestParam(required = false,defaultValue = "10")long size, String name){
        Page page=new Page(current,size);
        return cityMapper.selectPage(page, StringUtils.isBlank(name) ?new QueryWrapper<>():new QueryWrapper<CdCity>().like("short_name",name));
    }

    @GetMapping("/city/all")
    @ApiOperation("全部列表")
    public List<CdCity> all(){
        return cityMapper.queryAllCity();
    }
}
