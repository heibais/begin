package com.iamgpj.begin.module.system.file.controller;

import com.iamgpj.begin.core.common.RespJson;
import com.iamgpj.begin.module.system.file.service.QiniuService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: gpj
 * @Create: 2018/5/6 0:24
 */
@RestController
@RequestMapping("/v1/sys")
public class FileController {

    @Resource
    private QiniuService qiniuService;

    @ApiOperation(value = "文件上传", notes = "文件上传", tags = {"sys", "sys-file"})
    @PostMapping(value = "/file", consumes = {"multipart/form-data"})
    public RespJson sysFilesPost(@ApiParam(value = "file detail") @RequestPart("file") MultipartFile file) {
        return RespJson.createSuccess(qiniuService.upload(file));
    }
}
