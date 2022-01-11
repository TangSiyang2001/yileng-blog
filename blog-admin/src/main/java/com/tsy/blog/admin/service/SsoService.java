package com.tsy.blog.admin.service;

import com.tsy.blog.admin.vo.Result;
import com.tsy.blog.admin.vo.param.LoginParam;

/**
 * @author Steven.T
 * @date 2021/10/30
 */
public interface SsoService {

    Result login(LoginParam loginParam);

    Result logout();

}
