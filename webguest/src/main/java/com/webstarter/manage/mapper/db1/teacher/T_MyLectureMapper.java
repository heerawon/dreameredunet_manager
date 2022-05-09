package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.LectureModel;
import com.webstarter.manage.model.StudentModel;
import com.webstarter.manage.model.TeamModel;

import java.util.HashMap;
import java.util.List;

public interface T_MyLectureMapper {
    List<LectureModel> selectTeamListByTeacher(HashMap hashMap);
    List<TeamModel> getBtnTeacherTeam(String fkUserId);
    List<StudentModel> selectMyLectureStudentList(HashMap hashMap);
    List<StudentModel> selectStudentDetailList(HashMap hashMap);
}
