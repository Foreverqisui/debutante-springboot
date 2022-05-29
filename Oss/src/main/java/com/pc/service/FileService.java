package com.pc.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author foreverqisui
 */
public interface FileService  {
    /**
     * 通过文件流上传文件功能
     * @param file：上传的文件
     * @return 存储在oss的地址
     * */
    String uploadFile(MultipartFile file);
}
