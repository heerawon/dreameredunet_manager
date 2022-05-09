package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.HomeworkModel;

import java.util.List;

public interface T_HomeworkMapper {
    List<HomeworkModel> getHomeworkListByTeacherLimit(HomeworkModel homework);
    List<HomeworkModel> getHomeworkListByTeacher(HomeworkModel homework);
    HomeworkModel getHomeworkDetail(Integer homeworkId);

}
