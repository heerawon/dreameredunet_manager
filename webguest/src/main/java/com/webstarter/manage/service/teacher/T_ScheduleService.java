package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_ScheduleMapper;
import com.webstarter.manage.model.LectureModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class T_ScheduleService {
    @Resource
    private T_ScheduleMapper t_scheduleMapper;

    public List<LectureModel> getLectureListByTeacher(HashMap hashMap){
        return t_scheduleMapper.getLectureListByTeacher(hashMap);
    }

    public List<String> getUpcomingDate (HashMap hashMap){
        return t_scheduleMapper.getUpcomingDate(hashMap);
    }

    public List<LectureModel> getUpcomingLecture(HashMap hashMap){
        return t_scheduleMapper.getUpcomingLecture(hashMap);
    }

    public String updateLectureZoomLink (LectureModel lectureModel){
        String resMsg = "";
        try{
            if(t_scheduleMapper.updateLectureZoomLink(lectureModel)<0)
                return "변경 된 항목이 없습니다.";
        }catch (Exception e){
            return "DB 업데이트 실패";
        }
        return resMsg;
    }
}
