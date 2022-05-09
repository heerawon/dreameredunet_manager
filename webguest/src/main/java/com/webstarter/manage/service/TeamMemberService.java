package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.ApplyCourseMapper;
import com.webstarter.manage.mapper.db1.TeamMemberMapper;
import com.webstarter.manage.model.ApplyCourseModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.TeamMemberModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
public class TeamMemberService {
    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Resource
    private ApplyCourseMapper applyCourseMapper;

    //현재팀에 소속된 학생 목록
    public List<TeamMemberModel> getTeamMemberList(Integer fkTeamId){
        return teamMemberMapper.getTeamMemberList(fkTeamId);
    }

    //미수강 1 - 어디에도 속하지 않은 학생 목록
    public List<TeamMemberModel> getMemberList(HashMap hashMap){
        return teamMemberMapper.getMemberList(hashMap);
    }

    //미수강 2 - 다른팀에 속한 학생 목록
    public List<TeamMemberModel> getMemberList2(HashMap hashMap){
        return teamMemberMapper.getMemberList2(hashMap);
    }

    //미수강 3 - 어디에도 속하지 않은 학생 목록 ByCategory
    public List<TeamMemberModel> getNoTeamMemberListByCategory(HashMap hashMap){
        return teamMemberMapper.getNoTeamMemberListByCategory(hashMap);
    }

    //미수강 4 - 다른팀에 속한 학생 목록 ByCategory
    public List<TeamMemberModel> getOtherTeamMemberListByCategory(HashMap hashMap){
        return teamMemberMapper.getOtherTeamMemberListByCategory(hashMap);
    }

    /**
     * 팀원 등록 (1) 일반 팀 등록
     * @param reqMap teamMemberAddForm.getMembers()
     */
    public ResponseService<String> insertTeamMember(HashMap reqMap) {
        String resMsg = "";
        try {
            if (teamMemberMapper.insertTeamMember(reqMap) < 1) {
                resMsg = "학생 추가에 실패했습니다.";
                throw new Exception("학생 추가에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMsg = "학생 추가중 오류가 발생했습니다.";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 팀원 등록 (2) 특별활동 등록
     * updateCourseStatusByList 신청서 항목을 업데이트 하는 점이 다르다.
     * @param reqMap teamMemberAddForm.getMembers()
     */
    @Transactional(rollbackOn = Exception.class)
    public ResponseService<String> insertTeamMemberBySpeacialTeam(HashMap reqMap) {
        String resMsg = "";
        try {
            if (teamMemberMapper.insertTeamMember(reqMap) < 1) {
                resMsg = "학생 추가에 실패했습니다.";
                throw new Exception("학생 추가에 실패했습니다.");
            }else{
                if(applyCourseMapper.updateCourseStatusByList(reqMap) < 1){
                    resMsg = "학생 추가중 신청서 업데이트에 실패했습니다.";
                    throw new Exception("학생 추가중 신청서 업데이트에 실패했습니다.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resMsg = "학생 추가중 오류가 발생했습니다.";
        }
        return new ResponseService<String>(resMsg, "");
    }

    /**
     * 팀원 탈퇴
     * @param fkTeamId 팀 아이디
     * @param fkSuserId 팀원 아이디
     */
    @Transactional(rollbackOn = Exception.class)
    public ResponseService<String> deleteTeamMember(Integer fkTeamId, String fkSuserId, Integer applyCourseId) throws Exception {
        String resMsg = "";
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("fkTeamId",fkTeamId);
        reqMap.put("fkSuserId",fkSuserId);
        try {
            if(teamMemberMapper.deleteTeamMember(reqMap)<1){
                throw new Exception("학생 탈퇴에 실패했습니다.");
            }else{
                if(applyCourseId>0){
                    ApplyCourseModel applyCourseModel = new ApplyCourseModel();
                    applyCourseModel.setApplyCourseId(applyCourseId);
                    applyCourseModel.setFkTeamId(-1);
                    if(applyCourseMapper.updateCourseStatus(applyCourseModel)<1){
                        throw new Exception("학생 탈퇴중 신청 변경에 실패했습니다.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("학생 탈퇴중 DB오류가 발생했습니다.");
        }

        return new ResponseService<String>(resMsg, "");
    }

}
