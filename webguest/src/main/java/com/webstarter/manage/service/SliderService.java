package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.BoardMapper;
import com.webstarter.manage.mapper.db1.SliderMapper;
import com.webstarter.manage.model.BoardModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.SliderImagesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SliderService {
    private final SliderMapper sliderMapper;

    /**
     * 슬라이드 등록
     * @param type
     * @param imgSrc
     * @return
     */
    public ResponseService<String> insertSlider(String type,String imgSrc){
        String resMsg = "";
        Map reqMap = new HashMap<String, Object>();

        try {
            reqMap.put("type", type);
            reqMap.put("imgSrc", imgSrc);
            if(sliderMapper.insertSlider(reqMap)<1){
                resMsg="슬라이드 등록 실패";
            }
        }
        catch (Exception e) {
            // DB에러는 별도 처리
            resMsg = "슬라이드 등록 중 DB에러 발생";
        }

        return new ResponseService<String>(resMsg, "");
    }


    /**
     * 슬라이드 수정
     * @param id
     * @param imgSrc
     * @return
     */
    public ResponseService<String> updateSlider(int id,String imgSrc){
        String resMsg = "";
        Map reqMap = new HashMap<String, Object>();

        try {
            reqMap.put("id", id);
            reqMap.put("imgSrc", imgSrc);
            if(sliderMapper.updateSlider(reqMap)<1){
                resMsg="슬라이드 수정 실패";
            }
        }
        catch (Exception e) {
            // DB에러는 별도 처리
            resMsg = "슬라이드 수정 중 DB에러 발생";
        }

        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 슬라이드 삭제
     * @param id
     * @return
     */
    public ResponseService<String> deleteSlider(int id){
        String resMsg = "";
        Map reqMap = new HashMap<String, Object>();

        try {
            reqMap.put("id", id);
            if(sliderMapper.deleteSlider(reqMap)<1){
                resMsg="슬라이드 삭제 실패";
            }
        }
        catch (Exception e) {
            // DB에러는 별도 처리
            e.printStackTrace();
            resMsg = "슬라이드 삭제 중 DB에러 발생";
        }

        return new ResponseService<String>(resMsg, "");
    }


    /**
     * 타입에 따른 슬라이드 목록
     * @param type
     * @return
     */
    public ResponseService<List<SliderImagesModel>> selectListSlider(String type){
        String resMsg = "";
        Map reqMap = new HashMap<String, Object>();
        List<SliderImagesModel> sliderImagesModels = new ArrayList<>();

        try {
            reqMap.put("type", type);
            sliderImagesModels = sliderMapper.selectListSlider(reqMap);
        }
        catch (Exception e) {
            resMsg = "슬라이드 목록 조회 중 DB에러 발생";
        }
        return new ResponseService<List<SliderImagesModel>>(resMsg, sliderImagesModels);
    }


}
