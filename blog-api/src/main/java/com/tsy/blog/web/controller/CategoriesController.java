package com.tsy.blog.web.controller;

import com.tsy.blog.common.annotation.Cache;
import com.tsy.blog.service.CategoryService;
import com.tsy.blog.web.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Steven.T
 * @date 2021/10/19
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Resource
    private CategoryService categoryService;

    @Cache
    @GetMapping
    public Result listCategories(){
        return categoryService.findAllCategories();
    }

    @Cache
    @GetMapping("/detail")
    public Result listCategoriesDetails(){
        return categoryService.findAllCategories();
    }

    @GetMapping("/detail/{id}")
    public Result getCategoryDetailsById(@PathVariable("id") long id){
        return Result.success(categoryService.findCategoryById(id));
    }
}
