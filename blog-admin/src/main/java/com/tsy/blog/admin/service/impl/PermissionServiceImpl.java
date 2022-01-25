package com.tsy.blog.admin.service.impl;

import com.tsy.blog.admin.entity.Permission;
import com.tsy.blog.admin.mapper.PermissionMapper;
import com.tsy.blog.admin.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
