package com.tsy.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsy.blog.admin.common.constant.AdminStatus;
import com.tsy.blog.admin.common.constant.DefaultValues;
import com.tsy.blog.admin.entity.Admin;
import com.tsy.blog.admin.entity.AdminRole;
import com.tsy.blog.admin.mapper.AdminMapper;
import com.tsy.blog.admin.service.IAdminRoleService;
import com.tsy.blog.admin.service.IAdminService;
import com.tsy.blog.admin.web.vo.MsgCode;
import com.tsy.blog.admin.web.vo.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private IAdminRoleService adminRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult createAdmin(Admin admin) {
        if (adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, admin.getUsername())) != null) {
            return ResponseResult.fail(MsgCode.EXISTED_ACCOUNT);
        }
        admin.setStatus(AdminStatus.NORMAL);
        admin.setCreatedDate(System.currentTimeMillis());
        adminMapper.insert(admin);
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setRoleId(DefaultValues.DEFUALT_ROLE_ID);
        return adminRoleService.save(adminRole) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }
}
