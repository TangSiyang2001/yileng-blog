package com.tsy.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * @author Steven.T
 * @date 2021/10/20
 */

@Slf4j
public class BeanUtils {

    private BeanUtils(){
        throw new IllegalStateException("This is a util class...");
    }

    public static boolean isNotComplete(Object object) {
        final Class<?> aClass = object.getClass();
        try {
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                final Object obj = declaredField.get(object);
                if (isEmpty(obj)) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public static boolean isNull(Object object){
        final Class<?> aClass = object.getClass();
        try {
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                final Object obj = declaredField.get(object);
                if (!isEmpty(obj)) {
                    return false;
                }
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return true;
    }

    private static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return !StringUtils.hasLength((String) obj);
        } else if (obj instanceof Map) {
            return CollectionUtils.isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof Collection) {
            return CollectionUtils.isEmpty((Collection<?>) obj);
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;
    }
}
