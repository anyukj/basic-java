package com.wsc.basic.core.aop;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wsc.basic.core.annotation.LockPoint;
import com.wsc.basic.core.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

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

    /** 创建缓存，默认10秒过期 */
    private static final TimedCache<String, Boolean> CACHES = CacheUtil.newTimedCache(DateUnit.SECOND.getMillis() * 10);
    /** 缓存加锁进行 */
    private final ReentrantLock lock = new ReentrantLock(true);

    @Around(value = "@annotation(lockPoint)")
    public Object around(ProceedingJoinPoint joinPoint, LockPoint lockPoint) throws Throwable {
        String key = generateKey(joinPoint);
        lockKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            CACHES.remove(key);
        }
    }

    /**
     * 加锁
     */
    private void lockKey(String key) {
        lock.lock();
        try {
            if (CACHES.containsKey(key)) {
                log.warn("重复的请求：{}", key);
                throw new GlobalException("重复的请求");
            }
            CACHES.put(key, true);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 生成key， key生成规则为【包路径.方法名-md5(参数)】
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
        return StrUtil.format("{}.{}-{}", className, methodName, DigestUtil.md5(args));
    }

}
