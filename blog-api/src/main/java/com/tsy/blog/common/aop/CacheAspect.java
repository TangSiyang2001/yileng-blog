package com.tsy.blog.common.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsy.blog.common.annotation.Cache;
import com.tsy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.el.MethodNotFoundException;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author Steven.T
 * @date 2021/10/27
 */
@Slf4j
@Aspect
@Component
public class CacheAspect {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Pointcut("@annotation(com.tsy.blog.common.annotation.Cache)")
    public void point() {
        //切点
    }

    /**
     * 封装根据实参获取参数的类型操作
     *
     * @param args               实参数组
     * @param paramStringBuilder 参数字符信息构造器，传入空构造器得到新构造器
     * @return 类型数组
     */
    private Class<?>[] getParamTypes(Object[] args, StringBuilder paramStringBuilder) throws JsonProcessingException {
        Class<?>[] paramTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                paramTypes[i] = args[i].getClass();
                paramStringBuilder.append(objectMapper.writeValueAsString(args[i]));
            } else {
                paramTypes[i] = null;
            }
        }
        return paramTypes;
    }

    private Method getTargetMethod(Signature signature, Class<?>[] paramTypes) throws NoSuchMethodException {
        //NoSuchMethodException:调用getMethod不能获取私有方法，应该用getDeclaredMethod
        final Class<?> declaringType = signature.getDeclaringType();
        Method method = declaringType.getDeclaredMethod(signature.getName(), paramTypes);
        ReflectionUtils.makeAccessible(method);
        return method;
    }

    private Result loadCache(String redisKey,String className,String methodName) {
        final String redisValue = redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.hasLength(redisValue)) {
            Result result;
            try {
                result = objectMapper.readValue(redisValue, Result.class);
            } catch (JsonProcessingException e) {
                throw new SerializationException("执行缓存时json序列化错误", e);
            }
            //如果缓存结果为成功直接返回，为失败就重新请求
            if (result.isSuccess()) {
                log.info("调取缓存 className:{}//methodName:{}", className, methodName);
                return result;
            }
        }
        return null;
 }

    /**
     * 通过反射执行方法，对结果进行缓存
     *
     * @param joinPoint 切点
     * @param redisKey  存储的键值
     * @param expire    过期时间
     * @return 执行结果
     */
    private Result doCache(ProceedingJoinPoint joinPoint, String redisKey, long expire,String className,String methodName) {
        Result res;
        try {
            res = (Result) joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("{}::{}执行错误:{}", className, methodName, throwable.getMessage());
            return Result.fail(Result.CodeMsg.INTERNAL_SERVER_ERROR);
        }

        try {
            redisTemplate.opsForValue().set(redisKey, objectMapper.writeValueAsString(res), Duration.ofMillis(expire));
        } catch (JsonProcessingException e) {
            throw new SerializationException("执行缓存时json序列化错误", e);
        }
        log.info("{}::{} 结果已缓存", className, methodName);
        return res;
    }

    @Around("point()")
    public Result cache(ProceedingJoinPoint joinPoint) {
        //获得实参的内容
        Object[] args = joinPoint.getArgs();
        Class<?>[] paramTypes;
        StringBuilder params = new StringBuilder();
        //装填参数类型数组、参数字符串
        try {
            paramTypes = getParamTypes(args, params);
        } catch (JsonProcessingException e) {
            throw new SerializationException("执行缓存时json序列化错误", e);
        }

        //TODO：如何理解方法签名？
        final Signature signature = joinPoint.getSignature();
        Method method;
        try {
            method = getTargetMethod(signature, paramTypes);
        } catch (NoSuchMethodException e) {
            throw new MethodNotFoundException("执行缓存找不到目标方法", e);
        }
        Cache cacheAnnotation = method.getAnnotation(Cache.class);


        //获取对应类名
        String className = signature.getDeclaringType().getName();
        //获取方法名
        String methodName = signature.getName();
        //缓存的name
        final String cacheName = cacheAnnotation.name();
        String redisKey = cacheName + "::" + className + "::" + methodName + "::" +
                //加密 以防出现key过长以及字符转义获取不到的情况
                DigestUtils.md5Hex(String.valueOf(params));

        //调取缓存
        Result res = loadCache(redisKey,className,methodName);
        //说明没有缓存
        if (res == null) {
            //缓存的过期时间
            final long expire = cacheAnnotation.expire();
            //执行方法
            res = doCache(joinPoint,redisKey, expire,className,methodName);
        }
        return res;
    }
}
