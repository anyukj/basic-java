package com.wsc.basic.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端IP地址工具类
 *
 * @author 吴淑超
 * @since 2020-09-29
 */
public final class IPUtils {

    private IPUtils() {
    }

    /**
     * 获取客户端 IP 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (isBlank(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isBlank(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static Boolean isBlank(String ip) {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }
}
