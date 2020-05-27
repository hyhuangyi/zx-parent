package cn.biz.service;

import cn.biz.dto.OperateLogDTO;
import cn.biz.mapper.SysOperateLogMapper;
import cn.biz.po.SysOperateLog;
import cn.common.util.date.DateUtils;
import cn.common.util.string.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SysLogServiceImpl implements ISysLogService {
    @Autowired
    private SysOperateLogMapper sysOperateLogMapper;

    /**
     * 操作日志列表
     *
     * @param dto
     * @return
     */
    @Override
    public IPage<SysOperateLog> getOperateLogList(OperateLogDTO dto) {
        Page<SysOperateLog> page = new Page<>(dto.getCurrent(), dto.getSize());
        List<SysOperateLog> list = sysOperateLogMapper.getOperateLogList(page, dto);
        if (list.size() != 0) {
            list.forEach(l -> {
                String method = l.getOperateMethod();
                if (!StringUtils.isBlank(method)) {
                    //获取倒数第二个点的位置
                    l.setOperateMethod(method.substring(method.substring(0, method.lastIndexOf(".")).lastIndexOf(".") + 1));
                }
            });
        }
        page.setRecords(list);
        return page;
    }

    /**
     * chart数据
     *
     * @return
     */
    @Override
    public Map getChartData() {
        //存放数据
        List datasets = new ArrayList();
        List<String> users = sysOperateLogMapper.getUsers();
        if (users.size() == 0) {
            return null;
        }
        //最近6个月日期
        List<String> date = DateUtils.getSixMonth(new SimpleDateFormat("yyyy-MM").format(new Date()));
        List<Map> data = sysOperateLogMapper.getChart();
        for (int i = 0; i < users.size(); i++) {
            Map m = new HashMap();
            List<Long> count = new ArrayList<>();
            for (int j = 0; j < date.size(); j++) {
                boolean isSetZero = true;
                for (int k = 0; k < data.size(); k++) {
                    if (users.get(i).equals(data.get(k).get("name")) && date.get(j).equals(data.get(k).get("date"))) {
                        count.add((long) data.get(k).get("count"));
                        isSetZero = false;
                        break;
                    }
                }
                if (isSetZero) {
                    count.add(0L);
                }{
                    count.add(0L);
                }
            }
            m.put("data", count);
            m.put("label", users.get(i));
            datasets.add(m);
        }
        Map result = Maps.newHashMap();
        result.put("labels", date);
        result.put("datasets", datasets);
        return result;
    }
}
