package com.tsy.blog.web.controller;

import com.tsy.blog.common.annotation.Cache;
import com.tsy.blog.service.TagService;
import com.tsy.blog.web.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/10
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Resource
    private TagService tagService;

    @Cache
    @GetMapping("/hot")
    public Result listPopularTags(){
        final int limit =6;
        return tagService.findPopularTags(limit);
    }

    @Cache
    @GetMapping("/detail")
    public Result listTagDetails(){
        return tagService.findAllTags();
    }

    @GetMapping("/detail/{id}")
    public Result getTagDetailsById(@PathVariable("id") long id){
        return tagService.findTagById(id);
    }

    @Cache
    @GetMapping
    public Result listTags(){
        return tagService.findAllTags();
    }
}
