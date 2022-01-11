package com.tsy.blog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Steven.T
 * @date 2021/10/22
 */
public interface StorageService {
    /**
     * 上传文件
     * @param multipartFile 文件
     * @param fileNameInStorage 最终存储名
     * @return 执行情况
     */
    boolean upload(MultipartFile multipartFile,String fileNameInStorage);

    /**
     * 获取文件服务器地址
     * @return url
     */
    String getUrl();
}
