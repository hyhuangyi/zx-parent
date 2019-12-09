package cn.webapp.configuration;

import cn.common.consts.RedisConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangYi on 2018/8/23
 * swagger配置
 */
@Configuration
@EnableSwagger2
@Profile({"dev","prod"})
public class SwaggerConfig {
    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     * @return
     */
    @Bean
    public Docket zxApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name(RedisConst.AUTHORIZATION).defaultValue("").description("请求token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(zxApiInfo())
                .groupName("zx")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(aParameters)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.webapp.controller.zx"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket userApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name(RedisConst.AUTHORIZATION).defaultValue("").description("请求token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(zxApiInfo())
                .groupName("user")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(aParameters)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.webapp.controller.user"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket bizApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name(RedisConst.AUTHORIZATION).defaultValue("").description("请求token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(zxApiInfo())
                .groupName("biz")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(aParameters)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.webapp.controller.biz"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * 构建 api文档的详细信息函数
     * @return
     */
    private ApiInfo zxApiInfo() {
        return new ApiInfoBuilder()
                .title("zx-parent=>API列表")
                .description("springBoot-2.1.9.RELEASE")
                .contact(new Contact("zx","http://47.110.13.117/#/dashboard","597505910@qq.com"))
                .version("1.0")
                .build();
    }
}

