package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_AttendanceMapper;
import com.webstarter.manage.model.AttendanceModel;
import com.webstarter.manage.model.LectureModel;
import com.webstarter.manage.model.StudentModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class T_AttendanceService {

    @Resource
    private T_AttendanceMapper t_attendanceMapper;

    public List<StudentModel> getTeamMemberInfo(HashMap hashMap){
        return t_attendanceMapper.getTeamMemberInfo(hashMap);
    }

    public List<LectureModel> getLectureInfoByTeamMember(HashMap hashMap){
        return t_attendanceMapper.getLectureInfoByTeamMember(hashMap);
    }

    public String getMostRecentDate (HashMap hashMap){
        return t_attendanceMapper.getMostRecentDate(hashMap);
    }

    public Integer getAttendByDate (HashMap hashMap){
        return t_attendanceMapper.getAttendByDate(hashMap);
    }

    public int insertAttend (AttendanceModel attendModel) throws Exception{
        try{
            t_attendanceMapper.insertAttend(attendModel);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("insertHomeworkReply Err");
        }
        return attendModel.getAttendanceId();
    }
}
