package com.tsy.blog.admin.web.handler;

import com.tsy.blog.admin.web.vo.MsgCode;
import com.tsy.blog.admin.web.vo.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Steven.T
 * @date 2022/1/26
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseResult dealValidateException(MethodArgumentNotValidException exception){
        final BindingResult bindingResult = exception.getBindingResult();
        if(bindingResult.hasErrors()){
            Map<String,String> map =new HashMap<>(10);
            final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> {
                final String field = fieldError.getField();
                final String message = fieldError.getDefaultMessage();
                map.put(field,message);
            });
            return ResponseResult.fail(MsgCode.PARAMS_ERROR,map);
        }
        return ResponseResult.fail(MsgCode.INTERNAL_SERVER_ERROR);
    }
}
