package com.iamgpj.begin.module.system.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/5/5 23:25
 */
public interface QiniuService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
