package cn.webapp.controller.biz;

import cn.biz.dto.AddFundDTO;
import cn.biz.dto.FundDTO;
import cn.biz.po.Fund;
import cn.biz.service.ISysService;
import cn.biz.vo.FundVO;
import cn.common.consts.LogModuleConst;
import cn.common.util.file.EasyPoiUtil;
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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "基金相关接口")
@RestController
@Slf4j
public class FundController {
    @Autowired
    private ISysService sysService;

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
    public List<FundVO> fundList() {
       return sysService.fundList();
    }

    @ApiOperation("修改金额")
    @PostMapping("/fund/edit")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "修改金额", moduleName = LogModuleConst.FUND_MODULE)
    public boolean handleEdit(@ApiParam("主键id") @RequestParam @NotNull(message = "id不能为空") Long id,
                              @ApiParam("holdNum") @RequestParam @NotEmpty(message = "持有份额不能为空") String holdNum) {
        return sysService.updateHoldMoney(id,holdNum);
    }

    @ApiOperation("修改备注")
    @PostMapping("/fund/editRemark")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "修改备注", moduleName = LogModuleConst.FUND_MODULE)
    public boolean handleEditRemark(@ApiParam("主键id") @RequestParam @NotNull(message = "id不能为空") Long id,
                              @ApiParam(value = "remark",defaultValue = "") @RequestParam  String remark) {
        return sysService.updateRemark(id,remark);
    }
    @ApiOperation("新增基金")
    @PostMapping("/fund/add")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "新增基金", moduleName = LogModuleConst.FUND_MODULE)
    public boolean addFund(@ModelAttribute@Valid AddFundDTO addFundDTO) {
        return sysService.addFund(addFundDTO);
    }

    @ApiOperation("删除基金")
    @PostMapping("/fund/del")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "删除基金", moduleName = LogModuleConst.FUND_MODULE)
    public boolean addFund(@ApiParam("主键id") @RequestParam @NotNull(message = "id不能为空") Long id) {
        return sysService.delFund(id);
    }

    @ApiOperation("所有基金下拉选")
    @PostMapping("/fund/select")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "所有基金下拉选", moduleName = LogModuleConst.FUND_MODULE)
    public List<String> fundSelect() {
        return sysService.getFundSelect();
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

    @ApiOperation("费率为0的基金排行")
    @GetMapping("/fund/zero/rank")
    @PreAuthorize("hasAuthority('fund:list')")
    @OperateLog(operation = "费率为0的基金排行", moduleName = LogModuleConst.FUND_MODULE)
    public List<FundVO> zeroRateFundRank(@ApiParam("线程数量") @RequestParam Integer num)throws Exception{
        return sysService.getZeroRateFundRank(num);
    }

    @ApiOperation("费率为0的基金")
    @GetMapping("/comm/fund/zero")
    public List<Fund> zeroRateFund(@ApiParam("线程数量") @RequestParam Integer num)throws Exception{
        return sysService.getZeroRateFund(num);
    }

    @ApiOperation(value = "导出费率为0的基金排行")
    @GetMapping(value = "/comm/fund/zero/export")
    public void exportZero(HttpServletResponse response)throws Exception{
        List<FundVO> list=sysService.getZeroRateFundRank(30);
        EasyPoiUtil.exportExcel(list,"fund","zero_fund",FundVO.class,"免费率列表.xls",response);
    }
}
