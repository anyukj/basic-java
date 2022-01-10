package com.wsc.basic.core.aop;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wsc.basic.core.annotation.LockPoint;
import com.wsc.basic.core.exception.GlobalException;
import com.wsc.basic.core.utils.Sm3Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 参数相同防止重入锁切点
 *
 * @author 吴淑超
 * @date 2022-01-08 18:03
 */
@Slf4j
@Aspect
@Component
public class LockPointAspect {

    /** 缓存正在操作的方法 */
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            // 最大缓存数量
            .maximumSize(512)
            // 缓存项在给定时间内没有被写访问（创建或覆盖），则回收。如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    @Around(value = "@annotation(lockPoint)")
    public Object around(ProceedingJoinPoint joinPoint, LockPoint lockPoint) throws Throwable {
        String key = lock(joinPoint);
        try {
            return joinPoint.proceed();
        } finally {
            // 完成请求后清空key
            CACHES.invalidate(key);
        }
    }

    /**
     * 加锁
     *
     * @param joinPoint 连接点
     * @return key
     * @throws Throwable 错误信息
     */
    private  String lock(ProceedingJoinPoint joinPoint) throws Throwable {
        String key = generateKey(joinPoint);
        if (CACHES.getIfPresent(key) != null) {
            log.warn("重复的请求：{}", key);
            throw new GlobalException("重复的请求");
        }
        CACHES.put(key, true);
        return key;
    }

    /**
     * 生成key， key生成规则为【包路径.方法名-Sm3(参数)】
     *
     * @param joinPoint 连接点
     * @return key
     * @throws Throwable 错误信息
     */
    private String generateKey(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getMethod().getDeclaringClass().getName();
        String methodName = methodSignature.getMethod().getName();
        String args = new JsonMapper().writeValueAsString(joinPoint.getArgs());
        return String.format("%s.%s-%s", className, methodName, Sm3Utils.encryption(args));
    }

}
