package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.CategoryModel;
import com.webstarter.manage.model.TeacherModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TeacherMapper {
    List<CategoryModel> getCategoryList();
    int changeIsMain (TeacherModel teacher);
    List<TeacherModel> getTeacherList();
    List<TeacherModel> getTeacherTeamList();
    void insertTeacher(TeacherModel teacher);
    TeacherModel getTeacherDetail(String fkUserId);
    int updateTeacher (TeacherModel teacher);
    int updateTeacherUserInfo (Map reqMap);
    int moveItemUp(Map reqMap);
    int moveItemDown(Map reqMap);
    int moveItemTop(Map reqMap);
    int moveItemBottom(Map reqMap);
    int updateItemSortNum(); //순서 재정렬
}
