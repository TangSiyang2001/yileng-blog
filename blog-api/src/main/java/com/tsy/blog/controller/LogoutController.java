package com.tsy.blog.controller;

import com.tsy.blog.common.annotation.ParamCheck;
import com.tsy.blog.service.SsoService;
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
@RequestMapping("/logout")
public class LogoutController {

    @Resource
    private SsoService ssoService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") @ParamCheck String token){
        return ssoService.logout(token);
    }
}
