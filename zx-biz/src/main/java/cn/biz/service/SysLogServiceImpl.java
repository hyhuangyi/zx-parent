package cn.biz.service;

import cn.biz.dto.OperateLogDTO;
import cn.biz.mapper.SysOperateLogMapper;
import cn.biz.po.SysOperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements ISysLogService {
    @Autowired
    private SysOperateLogMapper sysOperateLogMapper;

    /**
     * 操作日志列表
     * @param dto
     * @return
     */
    @Override
    public IPage<SysOperateLog> getOperateLogList(OperateLogDTO dto) {
        Page<SysOperateLog> page=new Page<>();
        page.setRecords(sysOperateLogMapper.getOperateLogList(page,dto));
        return page;
    }
}
