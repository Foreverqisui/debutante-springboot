package com.pc.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.pc.service.FileService;
import com.pc.until.ConstansOssUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;




@Service
public class FileServiceImpl implements FileService {
    /**
     * 文件上传功能
     *
     * @param file ：上传的文件
     * @return 存储在oss的地址
     */
    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstansOssUtils.END_POIND;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstansOssUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstansOssUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstansOssUtils.BUCKET_NAME;
        //获取文件名称
        String fileName = file.getOriginalFilename();

        //把文件按照日期进行分类
        //获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");
        //进行拼接
        fileName = datePath + "/" + fileName;
        System.out.println(fileName);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);

            ossClient.shutdown();
            //返回照片路径 通过路径拼接
            return "https://" + bucketName + "." + endpoint + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return "你是傻逼";
        }

    }
}
