package cn.webapp.configuration;

import cn.webapp.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by huangYi on 2018/8/18
 * 替换spring boot默认的mvc配置
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CrossInterceptor crossInterceptor;

    /**
     * 自定义拦截器  拦截器需要注册，过滤器可以通过@WebFilter或者下面的注释掉的Filter注册
     * registry.addInterceptor(myInterceptor).addPathPatterns("/**"); 这种方式无论什么情况都可以
     * registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");这种情况时，自定义的interceptor中不能注入其他内容，比如redis或者其他service，如果要注入，必须使用上面这种方法
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossInterceptor).addPathPatterns("/**");
    }

//    /*跨域问题 springboot*/
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.addExposedHeader(RedisConst.AUTHORIZATION);
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(1);
//        return bean;
//    }

    /**
     * 防止自动注入
     * @return
     */
    @Bean
    public FilterRegistrationBean unJwtAuthFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(jwtAuthenticationFilter);
        registration.setEnabled(false);
        return registration;
    }

    /**
     * 过滤器 注入
     * @return
     */
    @Bean
    public FilterRegistrationBean jwtAuthFilter(){
        //新建过滤器注册类
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        // 添加我们写好的过滤器
        filterRegistrationBean.setFilter(jwtAuthenticationFilter);
        // 设置过滤器的URL模式
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(2);
        return  filterRegistrationBean;
    }

    /**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 设置spring mvc的解析器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        HttpMessageConverter<?> jsonConverter = new MappingJackson2HttpMessageConverter();
        HttpMessageConverter<?> stringConverter = new StringHttpMessageConverter(CHARSET);
        converters.add(jsonConverter);
        converters.add(stringConverter);
    }

}
