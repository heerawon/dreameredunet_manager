package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.TeamMemberModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface TeamMemberMapper {
    List<TeamMemberModel> getTeamMemberList(Integer fkTeamId);
    List<TeamMemberModel> getMemberList(HashMap hashMap);
    List<TeamMemberModel> getMemberList2(HashMap hashMap);
    List<TeamMemberModel> getNoTeamMemberListByCategory(HashMap hashMap);
    List<TeamMemberModel> getOtherTeamMemberListByCategory(HashMap hashMap);
    int insertTeamMember (HashMap<String,Object> map);  // 팀 수강생 추가
    int deleteTeamMember (HashMap<String,Object> map); // 수강생 탈퇴
}
