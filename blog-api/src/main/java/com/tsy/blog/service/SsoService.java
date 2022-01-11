package com.tsy.blog.service;

import com.tsy.blog.dao.entity.SysUser;
import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.params.SsoParam;

/**
 * @author Steven.T
 * @date 2021/10/13
 * 不与SysUserService合并，因为SysUserService提供用户信息的增删改查，而LoginService负责登录的业务
 */
public interface SsoService {
    /**
     * 实现登录
     * @param ssoParam 登录信息
     * @return 消息模型
     */
    Result login(SsoParam ssoParam);

    /**
     * 实现登出
     * @param token token
     * @return 消息模型
     */
    Result logout(String token);

    /**
     * 注册服务
     * @param ssoParam 注册信息
     * @return 消息模型
     */
    Result register(SsoParam ssoParam);

    /**
     * 获取缓存中的在线用户
     * @param token token
     * @return 用户实例
     */
    SysUser getUserInfoInCache(String token);
}
