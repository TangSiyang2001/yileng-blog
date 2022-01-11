package com.tsy.blog.controller;

import com.tsy.blog.service.SysUserService;
import com.tsy.blog.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/13
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        return sysUserService.getUserInfoByToken(token);
    }
}
