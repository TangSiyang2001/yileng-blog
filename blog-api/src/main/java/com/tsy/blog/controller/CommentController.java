package com.tsy.blog.controller;

import com.tsy.blog.common.annotation.ParamCheck;
import com.tsy.blog.service.CommentService;
import com.tsy.blog.vo.Result;
import com.tsy.blog.vo.params.CommentParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/16
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/article/{id}")
    public Result listComments(@PathVariable("id") Long id){
        return commentService.listComments(id);
    }

    @PostMapping("create/change")
    public Result publishComment(@RequestBody @ParamCheck CommentParam comment){
        return commentService.publishComment(comment);
    }
}
