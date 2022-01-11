package com.tsy.blog.vo.params;

import lombok.Data;

/**
 * 单点登录参数，包括登录和注册用
 * @author Steven.T
 * @date 2021/10/13
 */
@Data
public class SsoParam {

    private String account;

    private String password;

    private String nickName;

}
