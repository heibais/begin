package com.iamgpj.begin.module.system.file.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.iamgpj.begin.core.exception.BeginException;
import com.iamgpj.begin.core.exception.enums.ExceptionEnum;
import com.iamgpj.begin.core.util.RegexUtils;
import com.iamgpj.begin.module.system.file.service.QiniuService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/5/5 23:26
 */
@Service
@Slf4j
public class QiniuServiceImpl implements QiniuService {

    @Value("${thirdparty-platform.qiniu.accessKey}")
    private String accessKey;
    @Value("${thirdparty-platform.qiniu.secretKey}")
    private String secretKey;
    @Value("${thirdparty-platform.qiniu.imgDomain}")
    private String imgDomain;
    @Value("${thirdparty-platform.qiniu.fileDomain}")
    private String fileDomain;

    private UploadManager uploadManager;
    private BucketManager bucketManager;
    private Auth auth;

    @Resource
    private ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        Configuration cfg = new Configuration(Zone.zone0());
        this.auth = Auth.create(accessKey, secretKey);
        this.uploadManager = new UploadManager(cfg);
        this.bucketManager = new BucketManager(auth, cfg);
    }

    /**
     * 生成凭证
     * @param bucket
     * @return
     */
    private String generateUpToken(String bucket) {
        return auth.uploadToken(bucket);
    }

    /**
     * 获取文件后缀
     * @param filename 文件名
     * @return
     */
    private String getExtension(String filename) {
        if (filename == null) {
            return null;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            System.out.println(extensionPos);
            return extensionPos == -1 ? "" : filename.substring(extensionPos + 1);
        }
    }

    /**
     * 判断是否是图片
     * @param ext
     * @return
     */
    private boolean isImg(String ext) {
        return RegexUtils.isMatch("jpg|jpeg|png|gif|bmp", ext.toLowerCase());
    }

    /**
     * 根据文件(后缀)名, 获取Bucket
     */
    private String getBucket(String key) {
        String extension = getExtension(key);
        return isImg(extension) ? "wind-img" : "wind-file";
    }

    /**
     * 查询是否已经存在文件
     * @param key
     * @param bucket
     * @return
     */
    private String searchFile(String key, String bucket) {
        try {
            FileInfo fileInfo = bucketManager.stat(bucket, key);
            return fileInfo != null ? key : null;
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * 文件访问地址(加域名全名)
     */
    private String getFullName(String key) {
        String extension = getExtension(key);
        return isImg(extension) ? "http://" + this.imgDomain + "/" + key : "http://" + this.fileDomain + "/" + key;
    }


    public String upload(byte[] fileBytes, String extension) {
        String sha1Val = Hashing.sha1().hashBytes(fileBytes).toString();
        String key = !StringUtils.hasText(extension) ? sha1Val : sha1Val + "." + extension;
        if (searchFile(key, getBucket(key)) != null) {
            return getFullName(key);
        } else {
            return getFullName(uploadFile(fileBytes, key, getBucket(key)));
        }
    }

    private String uploadFile(byte[] fileBytes, String key, String bucket) {
        try {
            Response response = uploadManager.put(fileBytes, key, generateUpToken(bucket));
            DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
            return putRet.getKey();
        } catch (Exception e) {
            log.error("【上传七牛失败】 错误：{}", e);
            throw new BeginException(ExceptionEnum.FILE_UPLOAD_FAIL);
        }
    }


    @Override
    public String upload(MultipartFile file) {
        try {
            return upload(file.getBytes(), getExtension(file.getOriginalFilename()));
        } catch (IOException e) {
            log.error("【上传七牛失败】 错误：{}", e);
            throw new BeginException(ExceptionEnum.FILE_UPLOAD_FAIL);
        }
    }

    public static class DefaultPutRet {
        private String hash;
        private String key;

        public DefaultPutRet() {
        }

        public DefaultPutRet(String hash, String key) {
            this.hash = hash;
            this.key = key;
        }

        public String getHash() {
            return hash;
        }
        public void setHash(String hash) {
            this.hash = hash;
        }
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
    }
}
