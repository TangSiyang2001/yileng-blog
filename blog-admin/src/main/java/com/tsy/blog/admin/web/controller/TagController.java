package com.tsy.blog.admin.web.controller;


import com.tsy.blog.admin.entity.Tag;
import com.tsy.blog.admin.service.ITagService;
import com.tsy.blog.admin.web.vo.MsgCode;
import com.tsy.blog.admin.web.vo.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Steven.T
 * @since 2022-01-25
 */
@Api("TagController")
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private ITagService tagService;

    @GetMapping
    public ResponseResult listTags(){
        return ResponseResult.success(tagService.list());
    }

    @PostMapping
    public ResponseResult createTag(@RequestBody @Valid Tag tag){
        return tagService.save(tag) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateById(tag) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.removeById(id) ? ResponseResult.success() : ResponseResult.fail(MsgCode.OPERATION_FAIL);
    }
}
