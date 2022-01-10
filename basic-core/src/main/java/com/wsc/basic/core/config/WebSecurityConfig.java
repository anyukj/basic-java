package com.wsc.basic.core.config;

import com.wsc.basic.core.config.security.JwtAuthenticationFilter;
import com.wsc.basic.core.config.security.SimpleAccessDeniedHandler;
import com.wsc.basic.core.config.security.SimpleAuthenticationEntryPoint;
import com.wsc.basic.core.properties.WhitelistProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * @author 吴淑超
 * @since 2020-10-06 22:30
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private WhitelistProperties whitelistProperties;
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] whiteListUrls = whitelistProperties.getUrls().toArray(new String[0]);
        http
                // 关闭跨站域请求伪造及不使用session
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义异常处理
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
                .accessDeniedHandler(new SimpleAccessDeniedHandler())
                // 请求授权配置
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(whiteListUrls).permitAll()
                .anyRequest().access("@sysResourceServiceImpl.hasPermit(request,authentication)")
                // 自定义认证过滤器
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
