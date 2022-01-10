package com.wsc.basic.core.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

/**
 * 异步线程传递线程变量处理
 * 可以在@Async中直接使用UserContext.getUser()获取用户信息
 *
 * @author 吴淑超
 * @date 2021-11-10 11:49
 */
public class AsyncTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        SecurityContext context = SecurityContextHolder.getContext();
        Map<String, String> previous = MDC.getCopyOfContextMap();
        return () -> {
            try {
                SecurityContextHolder.setContext(context);
                MDC.setContextMap(previous);
                runnable.run();
            } finally {
                SecurityContextHolder.clearContext();
                MDC.clear();
            }
        };
    }

}
