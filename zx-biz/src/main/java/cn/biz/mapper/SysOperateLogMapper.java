package cn.biz.mapper;

import cn.biz.dto.OperateLogDTO;
import cn.biz.dto.TableListDTO;
import cn.biz.po.SysOperateLog;
import cn.biz.vo.TableListVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 操作日志
 * @author huangy
 * @since 2019-11-25
 */
@Repository
public interface SysOperateLogMapper extends BaseMapper<SysOperateLog> {
     List<SysOperateLog> getOperateLogList(Page page,@Param("dto") OperateLogDTO dto);

     List<TableListVO> getTableList(Page page,@Param("dto") TableListDTO dto);

     @Select("SELECT TABLE_SCHEMA  FROM information_schema.`TABLES` GROUP BY TABLE_SCHEMA")
     List<String> getSchemas();

     List<String> getUsers();

     List<Map> getChart();
}
