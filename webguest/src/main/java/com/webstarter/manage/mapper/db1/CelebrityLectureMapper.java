package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.CelebrityLectureModel;

import java.util.HashMap;
import java.util.List;

public interface CelebrityLectureMapper {
    List<CelebrityLectureModel> getCelebLectureList(HashMap hashMap);
    int updateStatus(CelebrityLectureModel ceLecModel);
}
