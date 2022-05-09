package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.TeamModel;

import java.util.List;

public interface T_TeamMapper {
    List<TeamModel> getTeamListByTeacher (String teacherId);
}

