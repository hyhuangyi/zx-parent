package cn.webapp.controller.biz;

import cn.biz.dto.OperateLogDTO;
import cn.biz.po.SysOperateLog;
import cn.biz.service.ISysLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "日志相关")
@RestController
public class SysLogController {
    @Autowired
    private ISysLogService sysLogService;

    @ApiOperation("操作日志列表")
    @PostMapping("/operate/log/list")
    public IPage<SysOperateLog> getOperateLogList(@ModelAttribute OperateLogDTO dto){
        return sysLogService.getOperateLogList(dto);
    }
}
