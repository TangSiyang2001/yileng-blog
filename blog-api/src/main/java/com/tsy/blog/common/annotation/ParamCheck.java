package com.tsy.blog.common.annotation;

import java.lang.annotation.*;

/**
 * 参数非空校验
 * @author Steven.T
 * @date 2021/11/3
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamCheck {
    CheckType type() default CheckType.NOTNULL;

    /**
     * @author Steven.T
     * @date 2021/11/3
     */
    enum CheckType {
        /**
         * 非空
         */
        NOTNULL,
        /**
         * 必须完整
         */
        IS_COMPLETED;
    }
}
