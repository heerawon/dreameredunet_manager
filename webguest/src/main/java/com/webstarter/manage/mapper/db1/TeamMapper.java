package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.ProgramModel;
import com.webstarter.manage.model.TeamCategoryModel;
import com.webstarter.manage.model.TeamModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface TeamMapper {
    List<ProgramModel> getProgramList();
    List<TeamCategoryModel> getTeamCategoryList();
    ProgramModel getProgramAge(Integer teamId);
    ProgramModel getProgramAgeByProgramId(Integer programId);
    List<TeamModel> getTeamList(HashMap hashMap);
    int insertTeam (TeamModel team);  // 팀 추가
    int getCountLecture (Integer teamId);
    int getCountTeamMember (Integer teamId);
    TeamModel getTeamDetail (Integer teamId);
    int updateTeam (TeamModel team);
    int updateTeamSchedule (TeamModel team);
    int deleteTeam (Integer teamId);
    List<TeamModel> getTeacherTeam (String fkUserId);
    TeamModel getTeamLectureSchedule (Integer teamId);
    int updateTeamLectureSchedule(TeamModel teamModel);
}
