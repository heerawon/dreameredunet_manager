package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.ApplyCourseMapper;
import com.webstarter.manage.mapper.db1.CelebrityLectureMapper;
import com.webstarter.manage.model.ApplyCourseModel;
import com.webstarter.manage.model.CelebrityLectureModel;
import com.webstarter.manage.model.ResponseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ApplyCourseService {
    @Resource
    private ApplyCourseMapper applyCourseMapper;

    @Resource
    private TeamMemberService teamMemberService;

    /**
     * 특별활동 신청회원목록 조회 (1) - 팀 구성시 현재 팀에 구성되지 않은 신청 회원 목록 조회
     * @param teamId 현재 팀
     * @param categoryId 대상 카테고리
     */
    public ResponseService<List<ApplyCourseModel>> getApplyCourceListWithoutMyTeam (Integer teamId, Integer categoryId){
        String resMsg = "";
        List<ApplyCourseModel> applyCourseList = new ArrayList<ApplyCourseModel>();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("teamId",teamId);
        hashMap.put("categoryId",categoryId);

        try {
            applyCourseList = applyCourseMapper.getApplyCourceListWithoutMyTeam(hashMap);
        }catch (Exception e){
            resMsg = "특별활동 신청회원목록 조회실패";
        }

        return new ResponseService<List<ApplyCourseModel>>(resMsg, applyCourseList);
    }

    /**
     * 특별활동 신청회원목록 조회 (2) - 신청 대기 회원 화면 진입시
     * @param teamId 대상 팀
     * @param categoryId 대상 카테고리
     * @param userName 신청 회원 이름
     * @param studentGender 신청 회원 성별
     * @param birthStartDt 검색 - 신청 회원 생년월일 (시작일)
     * @param birthEndDt 검색 - 신청 회원 생년월일 (종료일)
     * @param searchStartDt 검색 - 신청기간 (시작일)
     * @param searchEndDt 검색 - 신청기간 (시작일)
     */
    public ResponseService<List<ApplyCourseModel>> getApplyCourseList (Integer teamId, Integer categoryId, String userName, String studentGender, String birthStartDt, String birthEndDt, String searchStartDt, String searchEndDt){
        String resMsg = "";
        List<ApplyCourseModel> applyCourseList = new ArrayList<ApplyCourseModel>();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("teamId",teamId);
        hashMap.put("categoryId",categoryId);
        hashMap.put("userName",userName);
        hashMap.put("studentGender",studentGender);
        hashMap.put("birthStartDt",birthStartDt);
        hashMap.put("birthEndDt",birthEndDt);
        hashMap.put("searchStartDt",searchStartDt);
        hashMap.put("searchEndDt",searchEndDt);

        try {
            applyCourseList = applyCourseMapper.getApplyCourceList(hashMap);
        }catch (Exception e){
            resMsg = "특별활동 신청회원목록 조회실패";
        }

        return new ResponseService<List<ApplyCourseModel>>(resMsg, applyCourseList);
    }

    /**
     * 특별활동 신청 회원 등록
     * TODO 1. applyCourse 테이블 업데이트 (teamID, status, allowDt), 2. teamMember 등록
     * @param applyCourseModel
     * @return
     */
    @Transactional
    public String updateStatus(ApplyCourseModel applyCourseModel){
        String resMsg = "";
        try{
            if(applyCourseMapper.updateCourseStatus(applyCourseModel)<1){
                return "변경 된 항목이 없습니다.";
            }
            else{
//                teamMemberService.insertTeamMember()
            }
        }catch (Exception e){
            return "DB 업데이트 실패";
        }
        return resMsg;
    }
}
