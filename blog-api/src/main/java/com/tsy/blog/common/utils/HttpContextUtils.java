package com.tsy.blog.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Steven.T
 * @date 2021/10/20
 */
public class HttpContextUtils {

    private HttpContextUtils() {
        throw new IllegalStateException("This is a util class...");
    }

    public static HttpServletRequest getHttpServletRequest() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }
}
