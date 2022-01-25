package com.tsy.blog.admin.controller;


import com.tsy.blog.admin.common.constant.AdminStatus;
import com.tsy.blog.admin.common.response.MsgCode;
import com.tsy.blog.admin.common.response.ResponseResult;
import com.tsy.blog.admin.entity.Admin;
import com.tsy.blog.admin.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
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
    public ResponseResult listAdmins(){
        return ResponseResult.success(adminService.list());
    }

    @ApiOperation("创建管理员")
    @PostMapping
    public ResponseResult createAdmin(@RequestBody Admin admin){
        admin.setStatus(AdminStatus.NORMAL);
        admin.setCreatedDate(System.currentTimeMillis());
        return adminService.save(admin) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @ApiOperation("更新管理员")
    @PutMapping
    public ResponseResult updateAdmin(@RequestBody Admin admin){
        return adminService.updateById(admin) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @ApiOperation("删除管理员")
    @DeleteMapping("/{id}")
    public ResponseResult deleteAdmin(@PathVariable Integer id){
        return adminService.removeById(id) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }
}
