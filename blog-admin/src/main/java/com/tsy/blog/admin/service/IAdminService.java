package com.tsy.blog.admin.service;

import com.tsy.blog.admin.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsy.blog.admin.web.vo.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 创建管理员
     * @param admin 管理员
     * @return 结果
     */
    ResponseResult createAdmin(Admin admin);
}
