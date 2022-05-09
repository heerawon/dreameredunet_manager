package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.ApplyCourseModel;
import com.webstarter.manage.model.CelebrityLectureModel;

import java.util.HashMap;
import java.util.List;

public interface ApplyCourseMapper {
    List<ApplyCourseModel> getApplyCourceListWithoutMyTeam(HashMap hashMap);
    List<ApplyCourseModel> getApplyCourceList(HashMap hashMap);
    int updateCourseStatus(ApplyCourseModel applyCourseModel);
    int updateCourseStatusByList (HashMap<String,Object> map);  // 팀 수강생 추가시 신청서 업데이트
}
