package com.tsy.blog.common.annotation;

import java.lang.annotation.*;

/**
 * @author Steven.T
 * @date 2021/10/20
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {

    //模块名
    String module() default "";

    //操作名
    String operation() default "";

}
