package com.tsy.blog.admin.service.impl;

import com.tsy.blog.admin.entity.Role;
import com.tsy.blog.admin.mapper.RoleMapper;
import com.tsy.blog.admin.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
