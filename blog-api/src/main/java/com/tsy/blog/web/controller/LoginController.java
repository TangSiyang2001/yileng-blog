package com.tsy.blog.web.controller;

import com.tsy.blog.service.SsoService;
import com.tsy.blog.web.vo.Result;
import com.tsy.blog.web.vo.params.SsoParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/13
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Resource
    SsoService ssoService;

    @PostMapping
    public Result login(@RequestBody SsoParam loginParam){
        return ssoService.login(loginParam);
    }
}
