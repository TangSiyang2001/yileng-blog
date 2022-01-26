package com.tsy.blog.service;

import com.tsy.blog.dao.dto.SysUser;
import com.tsy.blog.web.vo.Result;
import com.tsy.blog.web.vo.UserVo;

/**
 * @author Steven.T
 * @date 2021/10/10
 */
public interface SysUserService {
    /**
     * 查询单个用户
     *
     * @param id id
     * @return 目标用户
     */
    SysUser findUserById(Long id);

    /**
     * 根据用户信息查询用户
     *
     * @param account 账号
     * @param pwd     密码
     * @return 用户实例
     */
    SysUser findUserByInfo(String account, String pwd);

    /**
     * 由token取用户信息
     * @param token token
     * @return 消息模型
     */
    Result getUserInfoByToken(String token);

    /**
     * 根据用户名找用户
     * @param account 用户名
     * @return 实体
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser 保存对象
     */
    void save(SysUser sysUser);

    /**
     * 根据id封装UserVo
     * @param id id
     * @return UserVo
     */
    UserVo findUserVoById(Long id);

}
