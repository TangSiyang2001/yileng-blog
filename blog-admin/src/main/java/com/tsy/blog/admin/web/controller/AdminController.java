package com.tsy.blog.admin.web.controller;


import com.tsy.blog.admin.entity.Admin;
import com.tsy.blog.admin.service.IAdminService;
import com.tsy.blog.admin.web.vo.MsgCode;
import com.tsy.blog.admin.web.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Api("AdminController")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private IAdminService adminService;

    @ApiOperation("获取所有管理员")
    @GetMapping
    public ResponseResult listAdmins() {
        final List<Admin> admins = adminService.list();
        admins.forEach(admin -> admin.setPassword(null));
        return ResponseResult.success(admins);
    }

    @ApiOperation("创建管理员")
    @PostMapping
    public ResponseResult createAdmin(@RequestBody @Valid Admin admin) {
        return adminService.createAdmin(admin);
    }

    @ApiOperation("更新管理员")
    @PutMapping
    public ResponseResult updateAdmin(@RequestBody Admin admin) {
        return adminService.updateById(admin) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @ApiOperation("删除管理员")
    @DeleteMapping("/{id}")
    public ResponseResult deleteAdmin(@PathVariable Integer id) {
        return adminService.removeById(id) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }
}
