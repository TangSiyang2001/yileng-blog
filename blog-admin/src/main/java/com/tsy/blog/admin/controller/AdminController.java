package com.tsy.blog.admin.controller;

import com.tsy.blog.admin.service.AdminService;
import com.tsy.blog.admin.vo.Result;
import com.tsy.blog.admin.vo.param.AdminParam;
import com.tsy.blog.admin.vo.param.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/29
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping
    public Result listAdmins(@RequestBody PageParam pageParam){
        return Result.success(adminService.listAdmins(pageParam));
    }

    @PostMapping("/add")
    public Result addAdmin(@RequestBody AdminParam adminParam){
        return Result.success(adminService.registerAdmin(adminParam));
    }

    @PostMapping("/delete/{id}")
    public Result deleteAdmin(@PathVariable("id") Long id){
        return Result.success(adminService.deleteAdminById(id));
    }

    @PostMapping("/update")
    public Result updateAdminInfo(AdminParam adminParam){
        return Result.success(adminService.updateAdminInfo(adminParam));
    }

    @GetMapping("/search")
    public Result findAdmin(PageParam pageParam){
        return Result.success(adminService.listAdmins(pageParam));
    }
}
