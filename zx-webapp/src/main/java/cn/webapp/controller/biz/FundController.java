package cn.webapp.controller.biz;

import cn.biz.service.ISysService;
import cn.biz.service.ISysTreeDictService;
import cn.biz.vo.FundVO;
import cn.common.consts.LogModuleConst;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.TimeCount;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Api(tags = "基金相关接口")
@RestController
@Slf4j
public class FundController {
    @Autowired
    private ISysService sysService;
    @Autowired
    private ISysTreeDictService sysTreeDictService;

    @TimeCount
    @ApiOperation("更新所有基金列表")
    @GetMapping("/fund/update")
    @OperateLog(operation = "更新所有基金列表", moduleName = LogModuleConst.FUND_MODULE)
    public Boolean update() {
       return sysService.updateAllFund();
    }

    @TimeCount
    @ApiOperation("基金列表")
    @GetMapping("/fund/list")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "查询基金列表", moduleName = LogModuleConst.FUND_MODULE)
    public List<FundVO> fundList() {
       return sysService.fundList();
    }

    @ApiOperation("修改金额")
    @PostMapping("/fund/edit")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "修改金额", moduleName = LogModuleConst.FUND_MODULE)
    public boolean handleEdit(@ApiParam("主键id") @RequestParam @NotEmpty(message = "id不能为空") Integer id,
                              @ApiParam("remark") @RequestParam @NotEmpty(message = "备注不能为空") String remark) {
        return sysTreeDictService.remark(id, remark);
    }
}
