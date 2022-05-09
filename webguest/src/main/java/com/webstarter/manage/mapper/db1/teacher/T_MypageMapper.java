package com.webstarter.manage.mapper.db1.teacher;

import com.webstarter.manage.model.TeacherModel;

import java.util.HashMap;
import java.util.Map;

public interface T_MypageMapper {
    TeacherModel getMyDetailInfo(String fkUserId);
    int updateUserPw(Map reqMap);
}

