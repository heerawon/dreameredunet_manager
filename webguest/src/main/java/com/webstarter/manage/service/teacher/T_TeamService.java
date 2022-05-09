package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_TeamMapper;
import com.webstarter.manage.model.TeamModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class T_TeamService {
    @Resource
    private T_TeamMapper t_teamMapper;

    public List<TeamModel> getTeamListByTeacher(String teacherId){
        return t_teamMapper.getTeamListByTeacher(teacherId);
    }
}
