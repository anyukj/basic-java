package com.wsc.basic.core.annotation;

import java.lang.annotation.*;

/**
 * 日志记录埋点
 *
 * @author 吴淑超
 * @date 2020-07-16 17:26
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPoint {

    /**
     * 日志的消息内容。支持SpEL表达式，参数可以使用#号加参数名称，也可以使用#p加参数下标
     * 特殊变量：#result（返回结果）、#userInfo（用户信息）
     */
    String message();

    /**
     * 日志分类
     */
    String type() default "operation";

}
