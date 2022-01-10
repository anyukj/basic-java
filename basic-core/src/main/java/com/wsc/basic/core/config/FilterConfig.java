package com.wsc.basic.core.config;

import com.wsc.basic.core.filter.HttpRequestMDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @author 吴淑超
 */
@Configuration
public class FilterConfig {

    /**
     * 在logback日志输出中增加MDC参数
     */
    @Bean
    public FilterRegistrationBean<Filter> httpRequestMDCFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpRequestMDCFilter());
        registration.setName("httpRequestMDCFilter");
        registration.setOrder(0);
        return registration;
    }

}
