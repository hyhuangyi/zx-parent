package cn.biz.service;

/**
 * 定时任务表 服务类
 * @author zx
 * @since 2020-11-27
 */
public interface ISysTaskService  {
     //初始化jobs
    void initSchedule() throws Exception;
}
