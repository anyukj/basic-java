package com.wsc.basic.core.constant;

/**
 * Jwt常量
 *
 * @author 吴淑超
 * @since 2020-09-29
 */
public class JwtConstants {

    /** 发行者 */
    public static final String ISSUER = "wsc";
    /** 类型 */
    public static final String TOKEN_TYPE = "Bearer";
    /** 消息头 */
    public static final String AUTH_HEADER = "Authorization";
    /** 秘钥 */
    public static final String SIGN_KEY = "Y2Fhc5NlY3JldAo=";
    /** 有效期(秒) */
    public static final Integer EXPIRATION = 3600;

}
