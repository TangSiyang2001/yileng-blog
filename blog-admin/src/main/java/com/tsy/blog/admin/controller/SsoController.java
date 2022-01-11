package com.tsy.blog.admin.controller;

import com.tsy.blog.admin.vo.Result;
import com.tsy.blog.admin.vo.param.LoginParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Steven.T
 * @date 2021/10/29
 */
@RestController
public class SsoController {

    ;

    @PostMapping("/login")
    public Result login(LoginParam loginParam){
        return null;
    }

}
