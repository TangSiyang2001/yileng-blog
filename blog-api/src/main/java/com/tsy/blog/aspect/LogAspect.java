package com.tsy.blog.aspect;


import com.alibaba.fastjson.JSON;
import com.tsy.blog.common.annotation.LogRecord;
import com.tsy.blog.common.utils.HttpContextUtils;
import com.tsy.blog.common.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Steven.T
 * @date 2021/10/20
 */

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.tsy.blog.common.annotation.LogRecord)")
    public void logPointCut() {
        //切点
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长
        long deltaTime = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint, deltaTime);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long deltaTime) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        LogRecord logRecord = method.getAnnotation(LogRecord.class);

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args.length == 0 ? null : args[0]);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        log.info("\n=====================log start================================\n" +
                "module:{}\n" +
                "operation:{}\n" +
                "request method:{}.{}()\n" +
                "params:{}\n" +
                "ip:{}\n" +
                "execute time : {} ms\n" +
                "=====================log end=================================="
                ,logRecord.module(),
                logRecord.operation(),
                className,methodName,params,
                IpUtils.getIpAdder(request),
                deltaTime);
    }
}
