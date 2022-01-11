package com.tsy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tsy.blog.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 采用七牛云实现文件（图片）存储和上传
 *
 * @author Steven.T
 * @date 2021/10/22
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Value("${qiniu.url}")
    private String url;

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.accessSecretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    @Override
    public boolean upload(MultipartFile multipartFile, String fileNameInStorage) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            byte[] uploadBytes = multipartFile.getBytes();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            //默认不指定文件名的情况下，以文件内容的hash值作为文件名
            Response response = uploadManager.put(uploadBytes, fileNameInStorage, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            log.info("PutResult:{}:{}", putRet.key, putRet.hash);
            return true;
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

}
