package com.tsy.blog.common.annotation;

import java.lang.annotation.*;

/**
 * @author Steven.T
 * @date 2021/10/27
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 5 * 60 * 1000;

    String name() default "";

}
