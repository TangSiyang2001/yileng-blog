package com.tsy.blog.admin.web.controller;


import com.tsy.blog.admin.web.vo.ResponseResult;
import com.tsy.blog.admin.service.IArticleBodyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Api("ArticleBodyController")
@RestController
@RequestMapping("/article-body")
public class ArticleBodyController {

    @Resource
    private IArticleBodyService articleBodyService;

    @ApiOperation("获取文章内容")
    @GetMapping("/{id}")
    public ResponseResult getBodyByArticleId(@PathVariable Integer id){
        return articleBodyService.getBodyByArticleId(id);
    }
}
