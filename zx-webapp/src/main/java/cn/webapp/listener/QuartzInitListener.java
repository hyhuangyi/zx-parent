package cn.webapp.listener;

import cn.biz.service.ISysTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QuartzInitListener implements CommandLineRunner {
    @Autowired
    private ISysTaskService sysTaskService;
    @Override
    public void run(String... args) throws Exception {
        sysTaskService.initSchedule();
    }
}
