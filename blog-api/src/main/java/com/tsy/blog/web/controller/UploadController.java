package com.tsy.blog.web.controller;

import com.tsy.blog.service.StorageService;
import com.tsy.blog.web.vo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 上传接口，待七牛云注册后测试
 *
 * @author Steven.T
 * @date 2021/10/22
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private StorageService uploadService;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {
        //原文件名
        final String originalFilename;
        final String[] blocks;
        if ((originalFilename = file.getOriginalFilename()) != null &&
                (blocks = StringUtils.split(originalFilename.trim(), ".")) != null) {
            //用UUID替换原文件名，将其独特化、唯一化
            String newFileName = UUID.randomUUID() + "." + blocks[blocks.length - 1];
            if(uploadService.upload(file,newFileName)){
                return Result.success(uploadService.getUrl()+newFileName);
            }
        }
        return Result.fail(Result.CodeMsg.UPLOAD_FAIL);
    }
}
