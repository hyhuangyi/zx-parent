package cn.webapp.configuration;

import cn.webapp.interceptor.MyPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
public class MapperConfig {
    //注册插件
    @Bean
    public MyPlugin myPlugin() {
        MyPlugin myPlugin = new MyPlugin();
        //设置参数，比如阈值等，可以在配置文件中配置，这里直接写死便于测试
        Properties properties = new Properties();
        //这里设置慢查询阈值为2000毫秒
        properties.setProperty("time", "2000");
        myPlugin.setProperties(properties);
        return myPlugin;
    }
}
