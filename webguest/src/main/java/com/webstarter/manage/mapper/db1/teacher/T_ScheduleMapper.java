package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.LectureModel;

import java.util.HashMap;
import java.util.List;

public interface T_ScheduleMapper {
    List<LectureModel> getLectureListByTeacher(HashMap hashMap);
    List<String> getUpcomingDate(HashMap hashMap);
    List<LectureModel> getUpcomingLecture(HashMap hashMap);
    int updateLectureZoomLink(LectureModel lectureModel);
}
