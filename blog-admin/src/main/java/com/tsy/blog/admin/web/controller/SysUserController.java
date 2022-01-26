package com.tsy.blog.admin.web.controller;


import com.tsy.blog.admin.web.vo.MsgCode;
import com.tsy.blog.admin.web.vo.ResponseResult;
import com.tsy.blog.admin.entity.SysUser;
import com.tsy.blog.admin.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Resource
    private ISysUserService userService;

    @GetMapping
    public ResponseResult listSysUsers(){
        final List<SysUser> users = userService.list();
        users.forEach(user -> user.setPassword(null));
        return ResponseResult.success(users);
    }

    @PostMapping
    public ResponseResult createUser(@RequestBody SysUser user){
        return userService.save(user) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Integer id){
        return userService.removeById(id) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody SysUser user){
        return userService.updateById(user) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

}
