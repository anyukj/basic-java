package com.wsc.basic.core.utils;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

/**
 * 国际化工具类
 *
 * @author 吴淑超
 * @since 2020-09-29
 */
@Component
public class I18nMessages {

    @Resource(type = MessageSource.class)
    private MessageSource messageSource;

    private static MessageSource staticMessageSource;

    @PostConstruct
    public void init() {
        staticMessageSource = messageSource;
    }

    /**
     * 获取国际化信息
     *
     * @param code 国际化编码
     * @return 国际化信息
     */
    public static String getMessage(String code, Object... args) {
        try {
            return staticMessageSource.getMessage(code, args, code, getLocale());
        } catch (Exception e) {
            return code;
        }
    }

    /**
     * 获取国际化编码
     *
     * @return 国际化编码
     */
    public static Locale getLocale() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return RequestContextUtils.getLocale(request);
    }

}
