package com.wsc.basic.core.config.security;

import com.wsc.basic.core.constant.JwtConstants;
import com.wsc.basic.core.model.TokenEntity;
import com.wsc.basic.core.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JWT登录认证过滤器
 *
 * @author 吴淑超
 * @since 2020-10-06 23:44
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = request.getHeader(JwtConstants.AUTH_HEADER);
        if (StringUtils.isNotBlank(token)) {
            TokenEntity tokenEntity = JwtTokenUtils.validation(token);
            if (tokenEntity != null) {
                // 创建UserDetails对象
                String roleIdsSplit = ",";
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                if (StringUtils.isNotBlank(tokenEntity.getRoleIds())) {
                    for (String roleId : tokenEntity.getRoleIds().split(roleIdsSplit)) {
                        authorities.add(new SimpleGrantedAuthority(roleId));
                    }
                }
                JwtUserDetails userDetails = new JwtUserDetails(tokenEntity.getUserName(), "", authorities);
                userDetails.setTokenEntity(tokenEntity);
                // 使用SpringSecurity管理UserDetails
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
