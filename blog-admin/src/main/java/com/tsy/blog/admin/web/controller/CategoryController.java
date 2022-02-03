package com.tsy.blog.admin.web.controller;


import com.tsy.blog.admin.entity.Category;
import com.tsy.blog.admin.service.ICategoryService;
import com.tsy.blog.admin.web.vo.MsgCode;
import com.tsy.blog.admin.web.vo.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Api("CategoryController")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @GetMapping
    public ResponseResult listCategory() {
        return ResponseResult.success(categoryService.list());
    }

    @PostMapping
    public ResponseResult createCategory(@RequestBody @Valid Category category) {
        return categoryService.save(category) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category) {
        return categoryService.updateById(category) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.removeById(id) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }
}
