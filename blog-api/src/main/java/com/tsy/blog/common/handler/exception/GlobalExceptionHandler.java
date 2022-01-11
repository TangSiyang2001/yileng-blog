package com.tsy.blog.common.handler.exception;

import com.tsy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Steven.T
 * @date 2021/10/10
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result dealException(Exception ex){
        log.error("=====================log start================================");
        log.error("Exception occurred:message:{},cause:{}",ex.getMessage(),ex.getCause());
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            log.error(String.valueOf(stackTraceElement));
        }
        log.error("=====================log end==================================");
        return Result.fail(Result.CodeMsg.INTERNAL_SERVER_ERROR);
    }
}
