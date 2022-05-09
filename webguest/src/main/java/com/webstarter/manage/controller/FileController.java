package com.webstarter.manage.controller;

import com.webstarter.manage.s3config.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images")
    public String uploadImages(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        String url ="";
        try{
            multipartFile.getContentType();
            url = s3Uploader.uploadImages(multipartFile, "static");
        }catch (Exception e){
            url="";
        }
        return url;
    }

    @PostMapping("/files")
    public String uploadFilePdf(@RequestParam("files") MultipartFile multipartFile) throws IOException {
//        String url ="";
//        try {
//            url = s3Uploader.uploadFile(multipartFile, "static");
//        }catch (Exception e){
//            url="";
//        }
//        return url;
        return "none::::notUse";
    }
}