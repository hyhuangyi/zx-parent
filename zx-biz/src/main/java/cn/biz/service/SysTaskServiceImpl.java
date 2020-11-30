package cn.biz.service;

import cn.biz.mapper.SysTaskMapper;
import cn.biz.po.SysTask;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysTaskServiceImpl implements ISysTaskService {
    @Autowired
    private SysTaskMapper sysTaskMapper;
    @Autowired
    private Scheduler scheduler;
    @Override
    public void initSchedule() throws Exception {
       List<SysTask>list= sysTaskMapper.selectList(new QueryWrapper<SysTask>().eq("is_del",0));
       for(SysTask task:list){
           JobKey jobKey = new JobKey(task.getJobName(), task.getJobGroup());
           // 绑定job类
           Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(task.getBeanClass()).newInstance().getClass());
           JobDetail jobDetail = JobBuilder.newJob(jobClass) .withIdentity(jobKey).build();
           CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
           CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(task.getJobName(), task.getJobGroup()) .withSchedule(scheduleBuilder).build();
           JobDataMap dataMap= cronTrigger.getJobDataMap();
           dataMap.put("taskId",task.getId());
           dataMap.put("param",task.getParam());
           if (!scheduler.checkExists(jobKey)) {
               if("0".equals(task.getJobStatus())){
                   scheduler.scheduleJob(jobDetail,cronTrigger);
               }
           }
           // 启动
           if (!scheduler.isShutdown()) {
               scheduler.start();
           }
       }
    }
}
