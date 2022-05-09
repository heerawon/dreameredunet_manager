package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.TeamMapper;
import com.webstarter.manage.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TeamService {
    @Resource
    private TeamMapper teamMapper;

    public List<ProgramModel> getProgramList(){
        return teamMapper.getProgramList();
    }

    public List<TeamCategoryModel> getTeamCategoryList(){
        return teamMapper.getTeamCategoryList();
    }

    public ProgramModel getProgramAge(Integer teamId){
        return teamMapper.getProgramAge(teamId);
    }

    public ProgramModel getProgramAgeByProgramId(Integer programId){
        return teamMapper.getProgramAgeByProgramId(programId);
    }

    public ResponseService<List<TeamModel>> getTeamList(Integer division,Integer programId, Integer categoryId, String userName, String teamName, String searchStartDt, String searchEndDt) {
        String resMsg = "";
        List<TeamModel> teamList = new ArrayList<TeamModel>();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("division",division); // division 1: 특별활동 , 0: 팀
        hashMap.put("programId",programId);
        hashMap.put("categoryId",categoryId);
        hashMap.put("userName",userName);
        hashMap.put("teamName",teamName);
        hashMap.put("searchStartDt",searchStartDt);
        hashMap.put("searchEndDt",searchEndDt);

        try {
            teamList = teamMapper.getTeamList(hashMap);
        }catch (Exception e){
            resMsg = "수강 중인 팀 목록 조회 실패";
        }

        return new ResponseService<List<TeamModel>>(resMsg, teamList);
    }

    public int insertTeam(TeamModel teamModel){
        teamMapper.insertTeam(teamModel);
        return teamModel.getTeamId();
    }

    public int getLectureCount (Integer teamId){
        return teamMapper.getCountLecture(teamId);
    }

    public int getTeamMemberCount (Integer teamId){
        return teamMapper.getCountTeamMember(teamId);
    }

    public TeamModel getTeamDetail (Integer teamId){
        return teamMapper.getTeamDetail(teamId);
    }

    public String deleteTeam(Integer teamId){
        String resMsg = "";
        try{
            teamMapper.deleteTeam(teamId);
        }catch (Exception e){
            resMsg = e.getMessage();
        }
        return resMsg;
    }

    public String updateTeam(TeamModel teamModel){
        String resMsg = "";
        try{
            teamMapper.updateTeam(teamModel);
        }catch (Exception e){
            resMsg = e.getMessage();
        }
        return resMsg;
    }

    public String updateTeamSchedule(TeamModel teamModel){
        String resMsg = "";
        try{
            teamMapper.updateTeamSchedule(teamModel);
        }catch (Exception e){
            resMsg = e.getMessage();
        }
        return resMsg;
    }

    public List<TeamModel> getTeacherTeam(String fkUserId) {
        return teamMapper.getTeacherTeam(fkUserId);
    }
}
