package com.wsc.basic.core.config.security;

import com.wsc.basic.core.model.TokenEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 自定义UserDetails，包含tokenEntity
 *
 * @author 吴淑超
 * @since 2020-10-07 0:27
 */
public class JwtUserDetails extends User {

    @Setter
    @Getter
    private TokenEntity tokenEntity;

    public JwtUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
