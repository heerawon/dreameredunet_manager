package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_MyLectureMapper;
import com.webstarter.manage.model.LectureModel;
import com.webstarter.manage.model.StudentModel;
import com.webstarter.manage.model.TeamModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class T_MyLectureService {

    @Resource
    private T_MyLectureMapper t_myLectureMapper;

    public List<LectureModel> selectTeamListByTeacher(HashMap hashMap){
        return t_myLectureMapper.selectTeamListByTeacher(hashMap);
    }

    public List<TeamModel> getBtnTeacherTeam(String fkUserId){
        return t_myLectureMapper.getBtnTeacherTeam(fkUserId);
    }

    public List<StudentModel> selectMyLectureStudentList(HashMap hashMap){
        return t_myLectureMapper.selectMyLectureStudentList(hashMap);
    }

    public List<StudentModel> selectStudentDetailList(HashMap hashMap){
        return t_myLectureMapper.selectStudentDetailList(hashMap);
    }
}
