package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_HomeworkMapper;
import com.webstarter.manage.model.HomeworkModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HomeworkService {
    @Resource
    private T_HomeworkMapper t_homeworkMapper;

    public List<HomeworkModel> getHomeworkListByTeacherLimit (HomeworkModel homework){
        return t_homeworkMapper.getHomeworkListByTeacherLimit(homework);
    }

    public List<HomeworkModel> getHomeworkListByTeacher (HomeworkModel homework){
        return t_homeworkMapper.getHomeworkListByTeacher(homework);
    }

    public HomeworkModel getHomeworkDetail(Integer homeworkId){
        return t_homeworkMapper.getHomeworkDetail(homeworkId);
    }


}
