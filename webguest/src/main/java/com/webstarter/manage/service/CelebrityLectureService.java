package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.CelebrityLectureMapper;
import com.webstarter.manage.model.CelebrityLectureModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.TeamModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CelebrityLectureService {
    @Resource
    private CelebrityLectureMapper celebrityLectureMapper;

    /**
     * 특강 신청회원목록 조회
     * @param userName 신청 회원 이름
     * @param studentGender 신청 회원 성별
     * @param birthStartDt 검색 - 신청 회원 생년월일 (시작일)
     * @param birthEndDt 검색 - 신청 회원 생년월일 (종료일)
     * @param searchStartDt 검색 - 신청기간 (시작일)
     * @param searchEndDt 검색 - 신청기간 (시작일)
     */
    public ResponseService<List<CelebrityLectureModel>> getCelebLectureList(String userName,String studentGender,String birthStartDt,String birthEndDt,String searchStartDt,String searchEndDt){
        String resMsg = "";
        List<CelebrityLectureModel> celebList = new ArrayList<CelebrityLectureModel>();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userName",userName);
        hashMap.put("studentGender",studentGender);
        hashMap.put("birthStartDt",birthStartDt);
        hashMap.put("birthEndDt",birthEndDt);
        hashMap.put("searchStartDt",searchStartDt);
        hashMap.put("searchEndDt",searchEndDt);

        try {
            celebList = celebrityLectureMapper.getCelebLectureList(hashMap);
        }catch (Exception e){
            resMsg = "특강 신청회원목록 조회실패";
        }

        return new ResponseService<List<CelebrityLectureModel>>(resMsg, celebList);
    }

    public String updateStatus(CelebrityLectureModel ceLecModel){
        String resMsg = "";
        try{
            if(celebrityLectureMapper.updateStatus(ceLecModel)<1)
                return "변경 된 항목이 없습니다.";
        }catch (Exception e){
            return "DB 업데이트 실패";
        }
        return resMsg;
    }
}
