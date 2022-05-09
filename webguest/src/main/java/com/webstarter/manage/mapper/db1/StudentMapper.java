package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.StudentModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface StudentMapper {
    List<StudentModel> getStudentList(HashMap hashMap);
    int insertStudent(StudentModel student);
    StudentModel getStudentDetail(String fkUserId);
    int updateStudent (StudentModel student);
    int updateUserName (HashMap hashMap);
    List<StudentModel> getWaitTeamList(HashMap hashMap);
    int updateUserRole (HashMap hashMap);
    int updateUserWithdrawal (HashMap hashMap);
}
