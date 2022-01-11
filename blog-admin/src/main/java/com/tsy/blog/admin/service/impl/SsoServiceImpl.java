package com.tsy.blog.admin.service.impl;

import com.tsy.blog.admin.dao.mapper.AdminMapper;
import com.tsy.blog.admin.service.SsoService;
import com.tsy.blog.admin.vo.Result;
import com.tsy.blog.admin.vo.param.LoginParam;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/30
 */
public class SsoServiceImpl implements SsoService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Result login(LoginParam loginParam) {
        //TODO:后面再做，方便测试
        return null;
    }

    @Override
    public Result logout() {
        return null;
    }
}
