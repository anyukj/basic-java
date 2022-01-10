package com.wsc.basic.core.utils;

import com.wsc.basic.core.constant.JwtConstants;
import com.wsc.basic.core.model.TokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author 吴淑超
 */
@Slf4j
public class JwtTokenUtils {

    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(JwtConstants.ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SIGN_KEY)
                .compact();
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(JwtConstants.SIGN_KEY)
                    .parseClaimsJws(token.trim())
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成token
     */
    public static String generate(TokenEntity entity) {
        Map<String, String> map;
        try {
            map = BeanUtils.describe(entity);
        } catch (Exception e) {
            log.error("TokenEntity convert Map failed");
            return null;
        }
        Map<String, Object> claims = new HashMap<>(map);
        return String.format("%s %s", JwtConstants.TOKEN_TYPE, generateToken(claims));
    }

    /**
     * 解析token
     */
    public static TokenEntity validation(String token) {
        Map<String, Object> claims = getClaimsFromToken(token.replace(JwtConstants.TOKEN_TYPE, ""));
        if (claims == null) {
            log.warn("Token validation failed");
            return null;
        }
        TokenEntity tokenEntity = new TokenEntity();
        try {
            BeanUtils.populate(tokenEntity, claims);
        } catch (Exception e) {
            log.error("Map convert TokenEntity failed");
            return null;
        }
        return tokenEntity;
    }

}

