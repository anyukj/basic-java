package com.wsc.basic.core.config.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验资源权限接口
 *
 * @author 吴淑超
 * @date 2020-08-14 10:59
 */
public interface CheckResource {

    /**
     * 权限校验
     *
     * @param request        请求
     * @param authentication 授权信息
     * @return 是否允许
     */
    boolean hasPermit(HttpServletRequest request, Authentication authentication);

}
