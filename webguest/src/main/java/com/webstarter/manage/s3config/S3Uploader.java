package com.webstarter.manage.s3config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.webstarter.manage.model.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String uploadImages(MultipartFile multipartFile, String dirName) throws IOException {


        if(multipartFile.getSize() > 10000000){
            //10MB 보다 큰 경우
            throw new IOException("10MB보다 작은 파일을 업로드하세요.");
        }

        String contentType = multipartFile.getContentType();
        String originalFileExtension;
        if (ObjectUtils.isEmpty(contentType)) {
            throw new IOException("파일의 확장자가 지정되어 있지 않습니다.");
        } else {
            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
                originalFileExtension = ".gif";
            }
            // 다른 파일 명이면 아무 일 하지 않는다
            else {
                throw new IOException("허용되지 않는 확장자 입니다.");
            }
        }

        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile, dirName);
    }

    public UploadFile uploadFile(MultipartFile multipartFile, String dirName) throws IOException {
        String fileUrl = "";
        String fileName = "";


        if(multipartFile.getSize() > 500000000){
            //500MB 보다 큰 경우
            throw new IOException("500 MB 보다 작은 파일을 업로드하세요.");
        }


        String contentType = multipartFile.getContentType();
        String originalFileExtension;
        if (ObjectUtils.isEmpty(contentType)) {
            throw new IOException("파일의 확장자가 지정되어 있지 않습니다.");
        } else {

            log.info("uploadFiel ::::: contentType::::"+contentType);

            if (contentType.contains("pdf")) {
                originalFileExtension = ".pdf";
            }
            // 다른 파일 명이면 아무 일 하지 않는다
            else {
                throw new IOException("허용되지 않는 확장자 입니다.");
            }
        }
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        fileUrl = upload(uploadFile, dirName);
        if(fileUrl.length() > 0){
            fileName = multipartFile.getOriginalFilename();
        }else{
            throw new IOException("파일 업로드 실패 ");
        }
        return new UploadFile(fileUrl,fileName,contentType);
    }




    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}
