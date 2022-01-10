package com.wsc.basic.core.config.security;

import com.wsc.basic.core.exception.GlobalException;
import com.wsc.basic.core.model.TokenEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户信息上下文
 *
 * @author 吴淑超
 */
public class UserContext {

    public static TokenEntity getUser() {
        JwtUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            throw new GlobalException("用户未登陆", HttpStatus.UNAUTHORIZED);
        }
        return userDetails.getTokenEntity();
    }

    public static JwtUserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserDetails) {
            return (JwtUserDetails) authentication.getPrincipal();
        }
        return null;
    }

}
