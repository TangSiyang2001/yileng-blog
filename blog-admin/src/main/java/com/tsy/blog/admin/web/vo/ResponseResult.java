package com.tsy.blog.admin.web.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Steven.T
 * @date 2022/1/25
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseResult {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static ResponseResult success(){
        return new ResponseResult(true, MsgCode.SUCCESS.getCode(), MsgCode.SUCCESS.getMsg(), null);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(true, MsgCode.SUCCESS.getCode(), MsgCode.SUCCESS.getMsg(), data);
    }

    public static ResponseResult fail(MsgCode msgCode) {
        return new ResponseResult(false, msgCode.getCode(), msgCode.getMsg(), null);
    }

    public static ResponseResult fail(MsgCode msgCode, Object data) {
        return new ResponseResult(false, msgCode.getCode(), msgCode.getMsg(), data);
    }

}
