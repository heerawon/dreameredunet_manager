package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.SliderMapper;
import com.webstarter.manage.mapper.db1.UploadFileMapper;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.SliderImagesModel;
import com.webstarter.manage.model.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReferenceServiceOld {
    private final UploadFileMapper uploadFileMapper;

    /**
     * 파일 등록
     * @return
     */
    public ResponseService<String> insertReference(String url,String fileName, String contentType){
        String resMsg = "";
        UploadFile uploadFile = new UploadFile(url,fileName,contentType);
        try {
            if(uploadFileMapper.insertFile(uploadFile) < 1){
                resMsg="파일 등록 실패";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            resMsg = "파일 등록 중 DB에러 발생";
        }
        return new ResponseService<String>(resMsg, "");
    }


    /**
     * 파일 수정
     * @return
     */
    public ResponseService<String> updateReference(int id,String url,String fileName, String contentType) {
        String resMsg = "";
        UploadFile uploadFile = new UploadFile(id, url, fileName, contentType);
        try {
            if (uploadFileMapper.updateFile(uploadFile) < 1) {
                resMsg = "파일 수정 실패";
            }
        } catch (Exception e) {
            // DB에러는 별도 처리
            resMsg = "파일 수정 중 DB에러 발생";
        }
        return new ResponseService<String>(resMsg, "");

    }

    /**
     * 파일 삭제
     * @param id
     * @return
     */
    public ResponseService<String> deleteReference(int id){
        String resMsg = "";
        Map reqMap = new HashMap<String, Object>();

        try {
            reqMap.put("id", id);
            if(uploadFileMapper.deleteFile(id)<1){
                resMsg="파일 삭제 실패";
            }
        }
        catch (Exception e) {
            resMsg = "파일 삭제 중 DB에러 발생";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     *  파일 목록
     * @return
     */
    public ResponseService<List<UploadFile>> selectListReference(){
        String resMsg = "";
        List<UploadFile> uploadFiles = new ArrayList<>();
        try {
            uploadFiles = uploadFileMapper.selectListFiles();
        }
        catch (Exception e) {
            log.info("selectListReference:::::" + e.getMessage());
            resMsg = "파일 목록 조회 중 DB에러 발생";
        }
        return new ResponseService<List<UploadFile>>(resMsg, uploadFiles);
    }


}
