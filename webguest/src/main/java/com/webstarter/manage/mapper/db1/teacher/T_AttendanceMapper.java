package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.AttendanceModel;
import com.webstarter.manage.model.LectureModel;
import com.webstarter.manage.model.StudentModel;

import java.util.HashMap;
import java.util.List;

public interface T_AttendanceMapper {
    List<StudentModel> getTeamMemberInfo(HashMap hashMap);
    List<LectureModel> getLectureInfoByTeamMember(HashMap hashMap);
    String getMostRecentDate(HashMap hashMap);
    Integer getAttendByDate(HashMap hashMap);
    int insertAttend(AttendanceModel attendanceModel);
}
