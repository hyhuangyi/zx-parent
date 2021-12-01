package cn.webapp;

import cn.biz.po.CdCity;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by huangYi on 2018/3/7
 **/
@SpringBootApplication
@EnableScheduling//开启定时任务
@EnableCaching//开启缓存
@EnableAsync//异步使@Async生效
@EnableTransactionManagement//开启事务
@ImportResource("classpath:spring/*.xml")
@MapperScan(basePackages = {"cn.biz.mapper"})
@ComponentScan(basePackages = {"cn.webapp", "cn.common", "cn.biz"})
@ServletComponentScan(basePackages = "cn.webapp")
/*使用@ServletComponentScan注解后 Servlet可以直接通过@WebServlet注解自动注册
Filter可以直接通过@WebFilter注解自动注册，Listener可以直接通过@WebListener 注解自动注册*/
@Slf4j
public class SpringbootApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringbootApplication.class);
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootApplication.class, args);
        CdCity city = (CdCity) context.getBean("city");
        log.info("code=" + city.getCode() + ";fullName=" + city.getFullName());
        log.info("open http://127.0.0.1:8088/zx/doc.html");
        log.info("open http://127.0.0.1:8088/zx/swagger-ui.html");
    }

    /**
     * 解决springBoot Invalid character found in the request target 特殊字符传参报错
     *
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }
}
