package cn.biz.mapper;

import cn.biz.dto.OperateLogDTO;
import cn.biz.po.SysOperateLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作日志
 * @author huangy
 * @since 2019-11-25
 */
@Repository
public interface SysOperateLogMapper extends BaseMapper<SysOperateLog> {
     List<SysOperateLog> getOperateLogList(Page page,@Param("dto") OperateLogDTO dto);
}
