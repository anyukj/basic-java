package com.wsc.basic.core.filter;

import com.wsc.basic.core.constant.MDCConstants;
import com.wsc.basic.core.model.TraceInfo;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在logback日志输出中增加MDC参数选项
 * 注意，此Filter尽可能的放在其他Filter之前
 * 我们可以在logback、log4j、log4j2文件的layout部分，
 * 通过%X{key}的方式使用MDC中的变量
 * <p>
 * 默认情况下，将会把“requestId”、“requestIp”、“timestamp”、“method”、“uri”添加到MDC上下文中。
 * 1）其中requestId为调用链跟踪使用，开发者不需要手动修改他们。
 * 2）requestIp为当前请求用户的ip地址。
 * 3）timestamp为请求开始被servlet处理的时间戳，设计上为被此Filter执行的开始时间，可以使用此值来判断内部程序执行的效率。
 * 4）method为当前request的请求方式。
 * 4）uri为当前request的uri。
 * <p>
 *
 * @author 吴淑超
 * @since 2020-09-29
 */
public class HttpRequestMDCFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 添加过滤MDC
        try {
            TraceInfo traceInfo = new TraceInfo(request);
            MDC.put(MDCConstants.REQUEST_ID, traceInfo.getRequestId());
            MDC.put(MDCConstants.REQUEST_IP, traceInfo.getRequestIp());
            MDC.put(MDCConstants.TIMESTAMP, traceInfo.getTimestamp());
            MDC.put(MDCConstants.METHOD, traceInfo.getMethod());
            MDC.put(MDCConstants.URI, traceInfo.getUri());
        } catch (Exception ignored) {
        }
        // 继续请求
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
            request.removeAttribute(MDCConstants.REQUEST_ID);
        }
    }
}
