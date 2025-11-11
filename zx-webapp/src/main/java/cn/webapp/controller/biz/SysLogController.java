package cn.webapp.controller.biz;

import cn.biz.dto.OperateLogDTO;
import cn.biz.po.SysOperateLog;
import cn.biz.service.ISysLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "日志相关")
@RestController
@RequiredArgsConstructor
public class SysLogController {
    private final ISysLogService sysLogService;

    @ApiOperation("操作日志列表")
    @PostMapping("/operate/log/list")
    @PreAuthorize("hasAuthority('log:list')")
    public IPage<SysOperateLog> getOperateLogList(@ModelAttribute OperateLogDTO dto) {
        return sysLogService.getOperateLogList(dto);
    }

    @ApiOperation("chart数据")
    @GetMapping("/operate/chart")
    public Map chart(@RequestParam(required = false, defaultValue = "bar") String type) {
        return sysLogService.getChartData(type);
    }
}
