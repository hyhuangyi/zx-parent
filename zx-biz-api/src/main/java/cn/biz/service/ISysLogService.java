package cn.biz.service;

import cn.biz.dto.OperateLogDTO;
import cn.biz.po.SysOperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

public interface ISysLogService {
    //操作日志列表
    IPage<SysOperateLog> getOperateLogList(OperateLogDTO dto);
    //chart数据
    Map getChartData();
}
