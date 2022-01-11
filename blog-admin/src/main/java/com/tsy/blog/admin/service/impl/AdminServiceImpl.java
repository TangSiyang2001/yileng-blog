package com.tsy.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tsy.blog.admin.dao.entity.Admin;
import com.tsy.blog.admin.dao.entity.Role;
import com.tsy.blog.admin.dao.mapper.AdminMapper;
import com.tsy.blog.admin.dao.mapper.RoleMapper;
import com.tsy.blog.admin.service.AdminService;
import com.tsy.blog.admin.vo.AdminVo;
import com.tsy.blog.admin.vo.PageVo;
import com.tsy.blog.admin.vo.Result;
import com.tsy.blog.admin.vo.param.AdminParam;
import com.tsy.blog.admin.vo.param.PageParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Result registerAdmin(AdminParam adminParam) {
        LambdaQueryWrapper<Admin> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Admin::getUsername,adminParam.getUsername())
                .or().eq(Admin::getEmail,adminParam.getEmail())
                .or().eq(Admin::getPhoneNum,adminParam.getPhoneNum());

        if(adminMapper.selectOne(queryWrapper)!=null){
            return Result.fail(Result.CodeMsg.EXISTED_ACCOUNT);
        }
        Admin admin=new Admin();
        BeanUtils.copyProperties(adminParam,admin);
        admin.setCreatedDate(System.currentTimeMillis());
        admin.setStatus(Admin.Status.NORMAL);
        adminMapper.insert(admin);
        return Result.success(null);
    }

    @Override
    public Result deleteAdminById(Long id) {
        LambdaUpdateWrapper<Admin> updateWrapper=new LambdaUpdateWrapper();
        updateWrapper.eq(Admin::getId,id);
        Admin admin=new Admin();
        admin.setStatus(Admin.Status.DELETED);
        adminMapper.update(admin,updateWrapper);
        return Result.success(null);
    }

    @Override
    public Result updateAdminInfo(AdminParam adminParam) {
        LambdaUpdateWrapper<Admin> updateWrapper=new LambdaUpdateWrapper();
        updateWrapper.eq(Admin::getId,adminParam.getId());
        Admin admin=new Admin();
        BeanUtils.copyProperties(adminParam,admin);
        adminMapper.update(admin,updateWrapper);
        return Result.success(null);
    }

    @Override
    public Result findAdminById(Long id) {
        return null;
    }

    @Override
    public Result listAdmins(PageParam pageParam) {
        final IPage<Admin> adminPage = adminMapper.listAdmin(pageParam.getPage(), pageParam.getLimit(),
                pageParam.getUsername(), pageParam.getRoleId(),
                pageParam.getPhoneNum(), pageParam.getEmail());
        final List<Admin> records = adminPage.getRecords();
        return Result.success(new PageVo<>(records.size(), copyList(records)));
    }

    private AdminVo copy(Admin admin){
        AdminVo adminVo=new AdminVo();
        BeanUtils.copyProperties(admin,adminVo);
        final LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId,admin.getRoleId());
        final Role role = roleMapper.selectOne(queryWrapper);
        adminVo.setRole(role.getRoleName());
        adminVo.setCreatedDate(new DateTime(admin.getCreatedDate()).toString("yyyy-MM-dd HH:mm"));
        return adminVo;
    }

    private List<AdminVo> copyList(List<Admin> adminList){
        List<AdminVo> res=new ArrayList<>();
        for (Admin admin : adminList) {
            res.add(copy(admin));
        }
        return res;
    }
}
