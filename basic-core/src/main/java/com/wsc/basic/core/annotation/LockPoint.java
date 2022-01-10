package com.wsc.basic.core.annotation;

import java.lang.annotation.*;

/**
 * 参数相同防止重入锁
 *
 * @author 吴淑超
 * @date 2022-01-08 18:02
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LockPoint {

}
