package com.tsy.blog.admin.controller;


import com.tsy.blog.admin.common.response.ResponseResult;
import com.tsy.blog.admin.service.IArticleService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Api("ArticleController")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private IArticleService articleService;

    @GetMapping
    public ResponseResult listArticles(){
        return ResponseResult.success(articleService.list());
    }


}
