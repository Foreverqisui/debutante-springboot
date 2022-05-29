package com.pc.controller;

import com.pc.result.ResultBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.pc.service.FileService;
@RestController
@RequestMapping("/api/oss/file")
@CrossOrigin
public class FileApiController {

    /**
     * 注入service属性
     * */
    @Autowired
    private FileService fileService;

    /**
     * 上传文件到阿里云oss
     * */
    @PostMapping("uploadFile")
    public ResultBack uploadFile(MultipartFile file){
        //获取存储在oss中的链接地址
        String url = fileService.uploadFile(file);
        return ResultBack.ok().data("url",url);
    }
}
