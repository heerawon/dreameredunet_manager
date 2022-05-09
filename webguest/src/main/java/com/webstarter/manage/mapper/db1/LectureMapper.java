package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.LectureListModel;
import com.webstarter.manage.model.LectureModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface LectureMapper {
    List<LectureModel> selectLectureList(Integer fkTeamId);
    LectureModel selectNearestLecture (Integer fkTeamId);
    LectureModel getLectureDetail (Map reqMap);
    int updateLecture (LectureListModel lectureModel);
    int deleteLecture (Integer lectureId);
    int insertLecture (HashMap<String,Object> map);  // 팀 추가
    int insertOneLecture (LectureListModel lectureListModel);
    List<LectureModel> getLectureListByTeam(HashMap hashMap);
    int setLectureChapter (Integer fkTeamId);
    int getAllLectureCount (HashMap hashMap);
}
