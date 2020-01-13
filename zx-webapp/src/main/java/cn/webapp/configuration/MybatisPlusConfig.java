package cn.webapp.configuration;

import cn.webapp.configuration.bean.ZxSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * MybatisPlus配置类
 */

@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * SQL执行效率插件
     * 该插件只用于开发环境，不建议生产环境使用
     * @Profile({"dev","prod"})
     */
    @Bean
    @Profile({"dev"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }

    /**
     *  自定义sql注入器 批量插入方法
     * @return
     */
    @Bean
    public ZxSqlInjector customerSqlInjector() {
        return new ZxSqlInjector();
    }
}
