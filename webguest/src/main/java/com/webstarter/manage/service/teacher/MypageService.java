package com.webstarter.manage.service.teacher;

import com.webstarter.manage.mapper.db1.teacher.T_MypageMapper;
import com.webstarter.manage.model.TeacherModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MypageService {
    @Resource
    private T_MypageMapper tMypageMapper;

    public TeacherModel getMyDetailInfo(String fkUserId){
        return tMypageMapper.getMyDetailInfo(fkUserId);
    }
}
