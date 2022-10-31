package com.wsc.basic.core.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wsc.basic.core.constant.MDCConstants;
import com.wsc.basic.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

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
public class RequestAspect {

    @Resource
    private HttpServletRequest request;
    @Resource
    private MappingJackson2HttpMessageConverter httpMessageConverter;

    @Around(value = "execution(public * *..controller..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        org.springframework.util.StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            // 记录请求日志
            String parameter = null;
            try {
                parameter = httpMessageConverter.getObjectMapper().writeValueAsString(joinPoint.getArgs());
            } catch (Exception ignored) {
            }
            log.info("URI:{} UseTime:{}ms Parameter:{}", request.getRequestURI(), stopWatch.getTotalTimeMillis(), parameter);
        }
    }

    @AfterReturning(value = "execution(public * *..controller..*.*(..))  || execution(public * *..exception..*.*(..))", returning = "result")
    public void afterReturn(Result<?> result) {
        if (result != null && request != null) {
            Object requestObj = request.getAttribute(MDCConstants.REQUEST_ID);
            result.setRequestId(requestObj == null ? null : requestObj.toString());
        }
    }
}
