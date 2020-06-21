package cn.webapp.controller.biz;

import cn.biz.dto.FundDTO;
import cn.biz.po.Fund;
import cn.biz.service.ISysService;
import cn.biz.service.ISysTreeDictService;
import cn.biz.vo.FundVO;
import cn.common.consts.LogModuleConst;
import cn.common.util.http.HttpRequestUtil;
import cn.webapp.aop.annotation.OperateLog;
import cn.webapp.aop.annotation.TimeCount;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "更新所有基金列表", moduleName = LogModuleConst.FUND_MODULE)
    public Boolean update() {
       return sysService.updateAllFund();
    }

    @TimeCount
    @ApiOperation("我的基金列表")
    @GetMapping("/fund/list")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "查询我的基金列表", moduleName = LogModuleConst.FUND_MODULE)
    public List<FundVO> fundList(@ApiParam("类型") @RequestParam @NotEmpty(message = "type不能为空")String type) {
       return sysService.fundList(type);
    }

    @ApiOperation("修改金额")
    @PostMapping("/fund/edit")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "修改金额", moduleName = LogModuleConst.FUND_MODULE)
    public boolean handleEdit(@ApiParam("主键id") @RequestParam @NotEmpty(message = "id不能为空") Integer id,
                              @ApiParam("remark") @RequestParam @NotEmpty(message = "备注不能为空") String remark) {
        return sysTreeDictService.remark(id, remark);
    }

    @ApiOperation("获取所有基金列表（分页）")
    @PostMapping("/fund/all")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "获取所有基金列表", moduleName = LogModuleConst.FUND_MODULE)
    public IPage<Fund> fundAll(@ModelAttribute FundDTO dto) {
        return sysService.getAllFund(dto);
    }

    @ApiOperation("获取基金类型")
    @GetMapping("/fund/type")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "获取基金类型", moduleName = LogModuleConst.FUND_MODULE)
    public List<String> fundType() {
        return sysService.getFundType();
    }

    @ApiOperation("费率为0的基金")
    @GetMapping("/comm/fund/zero")
    public List<Fund> zeroRateFund(@ApiParam("线程数量") @RequestParam Integer num)throws Exception{
        return sysService.getZeroRateFund(num);
    }

    @ApiOperation("费率为0的基金排行")
    @GetMapping("/comm/fund/zero/rank")
    public List<FundVO> zeroRateFundRank(@ApiParam("线程数量") @RequestParam Integer num)throws Exception{
        return sysService.getZeroRateFundRank(num);
    }
}
