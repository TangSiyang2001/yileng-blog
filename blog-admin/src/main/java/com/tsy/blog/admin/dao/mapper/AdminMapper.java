package com.tsy.blog.admin.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tsy.blog.admin.dao.entity.Admin;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
public interface AdminMapper extends BaseMapper<Admin> {
    IPage<Admin> listAdmin(int page,int pageSize,String username,Integer roleId,String phoneNum,String email);
}
