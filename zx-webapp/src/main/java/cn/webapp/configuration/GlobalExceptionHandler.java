package cn.webapp.configuration;

import cn.common.exception.ValidException;
import cn.common.exception.ZxException;
import cn.common.pojo.base.ResultDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * Created by huangYi on 2018/7/9
 * 如果单使用@ExceptionHandler，只能在当前Controller中处理异常。
 * 但当配合@ControllerAdvice一起使用的时候，就可以摆脱那个限制了。
 *
 * @ControllerAdvice 控制器增强
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * JSR和Hibernate validator的校验只能对Object的属性进行校验，不能对单个的参数进行校验，
     * spring 在此基础上进行了扩展，添加了MethodValidationPostProcessor拦截器，可以实现对方法参数的校验
     *
     * @return
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler
    public ResultDO handle(InternalAuthenticationServiceException e) {
        log.error("控制层捕获InternalAuthenticationServiceException,[{}]", e.getMessage());
        return new ResultDO("0", e.getMessage());
    }

    @ExceptionHandler
    public ResultDO handle(ZxException e) {
        log.error("控制层捕获ZxException,[{}]", e.getMessage());
        return new ResultDO("0", e.getMessage());
    }

    @ExceptionHandler
    public ResultDO handle(BadCredentialsException e) {
        log.error("控制层捕获BadCredentialsException,[{}]", e.getMessage());
        return new ResultDO("0", "用户名或密码错误");
    }

    @ExceptionHandler
    public ResultDO handle(AccessDeniedException e) {
        log.error("控制层捕获AccessDeniedException,[{}]", e.getMessage());
        return new ResultDO("0", "权限不足");
    }

    @ExceptionHandler
    public ResultDO handle(Exception e) {
        log.error("控制层捕获Exception(其他),[{}]", e.getMessage());
        return new ResultDO("0", "系统异常");
    }

    @ExceptionHandler
    public ResultDO handle(ArrayIndexOutOfBoundsException e) {
        log.error("控制层捕获ArrayIndexOutOfBoundsException,[{}]", e.getMessage());
        return new ResultDO("0", "数组越界异常");
    }

    @ExceptionHandler
    public ResultDO handle(NullPointerException e) {
        log.error("控制层捕获NullPointerException,[{}]", e.getMessage());
        return new ResultDO("0", "空指针异常");
    }

    /**
     * 自定义异常 校验异常 @ValidationAspect
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResultDO handle(ValidException e) {
        log.error("控制层捕获ValidException,[{}]", e.getMessage());
        return new ResultDO("0", e.getMessage());
    }

    /**
     * 没使用BindingResult接受会抛出这种异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResultDO handle(BindException e) {
        log.error("控制层捕获BindException,[{}]", e.getMessage());
        return new ResultDO("0", e.getFieldError().getDefaultMessage());
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是
     * MethodArgumentNotValidException异常。
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ResultDO handle(MethodArgumentNotValidException e) {
        log.error("控制层捕获MethodArgumentNotValidException,[{}]", e.getMessage());
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        return new ResultDO("0", message);
    }

    @ExceptionHandler
    public ResultDO handle(HttpRequestMethodNotSupportedException e) {
        log.error("控制层捕获HttpRequestMethodNotSupportedException,[{}]", e.getMessage());
        return new ResultDO("0", "请求方式不正确");
    }

    @ExceptionHandler
    public ResultDO handle(MissingServletRequestParameterException e) {
        log.error("控制层捕获MissingServletRequestParameterException,[{}]", e.getMessage());
        return new ResultDO("0", "参数未提交");
    }

    @ExceptionHandler
    public ResultDO handle(ValidationException e) {
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
            if (!CollectionUtils.isEmpty(set)) {
                ConstraintViolation<?> next = set.iterator().next();
                log.error("控制层捕获入参异常 ValidationException,[{}]", ex.getMessage());
                return new ResultDO("0", next.getMessageTemplate());
            }
        } else {
            log.error("控制层捕获入参异常,[{}]", e.getMessage());
        }
        return new ResultDO("0", "请求参数不正确");
    }
}
