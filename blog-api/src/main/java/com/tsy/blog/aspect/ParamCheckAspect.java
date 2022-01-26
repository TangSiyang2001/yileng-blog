package com.tsy.blog.aspect;

import com.alibaba.fastjson.JSON;
import com.tsy.blog.common.annotation.ParamCheck;
import com.tsy.blog.common.utils.BeanUtils;
import com.tsy.blog.web.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 校验参数为非空
 *
 * @author Steven.T
 * @date 2021/11/3
 */
@Slf4j
@Aspect
@Component
public class ParamCheckAspect {

    @Pointcut("@annotation(com.tsy.blog.common.annotation.ParamCheck)")
    public void pt() {
        //切点
    }

    @Around("pt()")
    public Object validate(ProceedingJoinPoint pjp) throws Throwable {
        //获取拦截的方法
        final MethodSignature signature = (MethodSignature) pjp.getSignature();
        final Method method = signature.getMethod();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        //参数类型
        final Class<?>[] parameterTypes = method.getParameterTypes();
        //实参内容
        final Object[] args = pjp.getArgs();
        //获取参数名称
        final String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof ParamCheck) {
                    final ParamCheck targetAnnotation = (ParamCheck) annotation;
                    if (Objects.equals(targetAnnotation.type(), ParamCheck.CheckType.NOTNULL)
                            && BeanUtils.isNull(args[i])) {
                        log.error("参数:" + method.getName() + "::" + parameterTypes[i] + " " + parameterNames[i] + "空白");
                        return JSON.toJSONString(Result.fail(Result.CodeMsg.PARAMS_ERROR));
                    } else if (Objects.equals(targetAnnotation.type(), ParamCheck.CheckType.IS_COMPLETED)
                            && BeanUtils.isNotComplete(args[i])) {
                        log.error("参数:" + method.getName() + "::" + parameterTypes[i] + " " + parameterNames[i] + "不完整");
                        return JSON.toJSONString(Result.fail(Result.CodeMsg.PARAMS_ERROR));
                    }
                    break;
                }
            }
        }
        return pjp.proceed(args);
    }

}
