package cn.webapp.configuration;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @author huangy
 * @date 2019/7/19 14:47
 * springboot 高版本只要实现 start stop isRunning
 */
@Component
public class MySmartLifeCycle implements SmartLifecycle {
    private boolean isRunning = false;

    @Override
    public void start() {
        System.out.println("start");
        // 执行完其他业务后，可以修改 isRunning = true
        isRunning = true;
    }

    @Override
    public void stop() {
        System.out.println("stop");
        isRunning = false;
    }

    @Override
    public boolean isRunning() {
        // 默认返回false
        return isRunning;
    }
}
