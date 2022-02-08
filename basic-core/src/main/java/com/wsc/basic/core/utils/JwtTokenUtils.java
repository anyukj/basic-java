package com.wsc.basic.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.wsc.basic.core.constant.JwtConstants;
import com.wsc.basic.core.model.TokenEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT工具类
 *
 * @author 吴淑超
 */
@Slf4j
public class JwtTokenUtils {

    /**
     * 生成token
     */
    public static String generate(TokenEntity entity) {
        String token = JWT.create()
                .addPayloads(BeanUtil.beanToMap(entity))
                .setExpiresAt(DateTime.now().offset(DateField.SECOND, JwtConstants.EXPIRATION))
                .setIssuer(JwtConstants.ISSUER)
                .setSigner(JWTSignerUtil.hs512(JwtConstants.SIGN_KEY.getBytes()))
                .sign();
        return StrUtil.format("{} {}", JwtConstants.TOKEN_TYPE, token);
    }

    /**
     * 解析token
     */
    public static TokenEntity validation(String token) {
        JWT jwt = JWT.of(StrUtil.trim(StrUtil.removePrefix(token, JwtConstants.TOKEN_TYPE)));
        // 校验有效性
        try {
            JWTValidator.of(jwt)
                    .validateAlgorithm(JWTSignerUtil.hs512(JwtConstants.SIGN_KEY.getBytes()))
                    .validateDate(DateTime.now());
        } catch (ValidateException e) {
            log.error("JWT verification failed!", e);
            return null;
        }
        // token内容转实体
        return JSONUtil.toBean(jwt.getPayload().getClaimsJson(), TokenEntity.class);
    }

}

