package com.tsy.blog.controller;

import com.tsy.blog.common.annotation.ParamCheck;
import com.tsy.blog.service.SsoService;
import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.params.SsoParam;
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
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private SsoService ssoService;

    @PostMapping
    public Result register(@RequestBody @ParamCheck(type = ParamCheck.CheckType.IS_COMPLETED) SsoParam ssoParam) {
        return ssoService.register(ssoParam);
    }
}
