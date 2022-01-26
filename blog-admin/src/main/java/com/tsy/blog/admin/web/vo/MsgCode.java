package com.tsy.blog.admin.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Steven.T
 * @date 2022/1/25
 */
@AllArgsConstructor
public enum MsgCode {

    /**
     * 状态码
     */
    SUCCESS(200, "success"),
    PARAMS_ERROR(10001, "参数错误"),
    ACCOUNT_PASSWORD_NOT_EXIST(10002, "用户名或密码错误"),
    INVALID_TOKEN(10003, "非法token"),
    EXISTED_ACCOUNT(10004, "用户名重复"),
    UPLOAD_FAIL(20001, "上传文件失败"),
    NOT_LOGIN(90002, "未登录"),
    DATA_NOT_EXISTS(50001, "数据不存在"),
    OPERATION_FAIL(50002,"操作失败"),
    INTERNAL_SERVER_ERROR(-999, "系统异常");

    @Getter
    private final int code;

    @Getter
    private final String msg;

}
