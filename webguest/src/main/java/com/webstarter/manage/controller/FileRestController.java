package com.webstarter.manage.controller;

import com.google.gson.Gson;
import com.webstarter.manage.model.HttpMessageModel;
import com.webstarter.manage.model.ResponseMessage;
import com.webstarter.manage.model.UploadFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@RestController
//@RequestMapping(value = "filerest")
public class FileRestController {
//    @Value("${spring.servlet.multipart.location}")
//    private String filePath = "/usr/local/tomcat/webapps/uploadimg";
//
//    @PostMapping(value = "/upload",consumes = {"multipart/form-data"} )
//    public ResponseEntity<HttpMessageModel> upload(@RequestPart(value="file",required = false) MultipartFile[] uploadfile, Model model) throws IOException {
//        //TODO: upload 는 admin 일 경우에만 사용
//        //Session 확인필요
//        log.info("uploadfile::::: " + uploadfile.length);
//        List<UploadFile> list = new ArrayList<>();
//        Gson gson = new Gson();
//        String resFileName = "";
//
//        try{
//            for (MultipartFile file : uploadfile) {
//                if (!file.isEmpty()) {
//                    // UUID를 이용해 unique한 파일 이름을 만들어준다.
//                    String rndString = UUID.randomUUID().toString();
//                    String fileName = rndString + "_" + file.getOriginalFilename();
//                    resFileName = fileName;
//
//                    UploadFile dto = new UploadFile(
//                            rndString,
//                            file.getOriginalFilename(),
//                            file.getContentType(),"uploadimg/" + fileName);
//
//                    File newFileName = new File(fileName);
//                    // 전달된 내용을 실제 물리적인 파일로 저장해준다.
//                    file.transferTo(newFileName);
//
//                    list.add(dto);
//
//                }
//            }
//
//            return new ResponseMessage(200, "success", resFileName).getResponse();
//        }catch (Exception e){
//            return new ResponseMessage(500, "fail ", null).getResponse();
//        }
//    }
//
//    @GetMapping("/download")
//    public ResponseEntity<Resource> download(@ModelAttribute UploadFile dto) throws IOException {
//
//
//        log.info("filePath ::::: " + filePath);
//        log.info("UploadFile:::::" + dto.toString());
//        Path path = Paths.get(filePath + "/" + dto.getUuid() + "_" + dto.getFileName());
//        String contentType = Files.probeContentType(path);
//        // header를 통해서 다운로드 되는 파일의 정보를 설정한다.
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(ContentDisposition.builder("attachment")
//                .filename(dto.getFileName(), StandardCharsets.UTF_8)
//                .build());
//        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
//
//        Resource resource = new InputStreamResource(Files.newInputStream(path));
//        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//    }



}
