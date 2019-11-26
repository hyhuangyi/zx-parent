package cn.biz.service;

import cn.biz.dto.OperateLogDTO;
import cn.biz.mapper.SysOperateLogMapper;
import cn.biz.po.SysOperateLog;
import cn.common.util.string.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
        Page<SysOperateLog> page=new Page<>(dto.getCurrent(),dto.getSize());
        List<SysOperateLog> list=sysOperateLogMapper.getOperateLogList(page,dto);
        if(list.size()!=0){
            list.forEach(l->{
                String method=l.getOperateMethod();
                if(!StringUtils.isBlank(method)){
                    //获取倒数第二个点的位置
                    l.setOperateMethod(method.substring(method.substring(0,method.lastIndexOf(".")).lastIndexOf(".")+1));
                }
            });
        }
        page.setRecords(list);
        return page;
    }
}
