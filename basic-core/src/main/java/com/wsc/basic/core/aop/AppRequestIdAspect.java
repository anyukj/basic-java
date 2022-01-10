package com.wsc.basic.core.aop;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wsc.basic.core.constant.MDCConstants;
import com.wsc.basic.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 请求id切面
 *
 * @author 吴淑超
 * @since 2020-09-29
 */
@Slf4j
@Aspect
@Component
public class AppRequestIdAspect {

    @Resource
    private HttpServletRequest request;

    /**
     * 请求日志切入点
     */
    @Pointcut("execution(public * *..controller..*.*(..)) " +
            "|| execution(public * *..exception..*.*(..)) ")
    public void validPoint() {
        // do nothing
    }

    @Before(value = "validPoint()")
    public void before(JoinPoint joinPoint) {
        // 记录请求日志
        try {
            log.info("URI:{}\t Parameter:{}", request.getRequestURI(), new JsonMapper().writeValueAsString(joinPoint.getArgs()));
        } catch (Exception ignored) {
        }
    }

    @AfterReturning(value = "validPoint()", returning = "result")
    public void afterReturn(Result<?> result) {
        if (result != null && request != null) {
            Object requestObj = request.getAttribute(MDCConstants.REQUEST_ID);
            result.setRequestId(requestObj == null ? null : requestObj.toString());
        }
    }
}
