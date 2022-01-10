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
    public static final String SIGN_KEY = "Y2Fhc3NlY3JldAo=";
    /** 有效期:如1小时,60分钟*60秒*1000毫秒=3600000L; */
    public static final Long EXPIRATION = 3600000L;
}
