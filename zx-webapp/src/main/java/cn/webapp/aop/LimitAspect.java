package cn.webapp.aop;

import cn.common.exception.ZxException;
import cn.common.util.ip.IpUtil;
import cn.webapp.aop.annotation.ServiceLimit;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 限流 AOP
 */
@Aspect
@Configuration
public class LimitAspect {

    /**
     * 根据IP分不同的令牌桶, 每天自动清理缓存 每秒只发出6个令牌
     */
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) {
                    return RateLimiter.create(6);
                }
            });

    /**
     * Service层切点  限流
     */
    @Pointcut("@annotation(cn.webapp.aop.annotation.ServiceLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
        ServiceLimit.LimitType limitType = limitAnnotation.limitType();
        String key = limitAnnotation.key();
        Object obj;
        try {
            if(limitType.equals(ServiceLimit.LimitType.IP)){
                key = IpUtil.getIpAddress(request);
            }
            RateLimiter rateLimiter = caches.get(key);
            Boolean flag = rateLimiter.tryAcquire();
            if(flag){
                obj = joinPoint.proceed();
            }else{
                throw new ZxException("小同志，你访问的太频繁了");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ZxException("系统异常，请联系管理员同志");
        }
        return obj;
    }
}