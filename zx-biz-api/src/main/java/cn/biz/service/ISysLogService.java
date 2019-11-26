package cn.biz.service;

import cn.biz.dto.OperateLogDTO;
import cn.biz.po.SysOperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface ISysLogService {
    //操作日志列表
    IPage<SysOperateLog> getOperateLogList(OperateLogDTO dto);
}
