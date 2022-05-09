package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.StudentModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface WithdrawalMapper {
    List<StudentModel> selectWithdrawalMemberList ();
    int updateUserWithdrawal (HashMap hashMap);
    int updateStudentWithdrawal (HashMap hashMap);
    int updateShippingWithdrawal (HashMap hashMap);
    int updateShippingLocationWithdrawal (HashMap hashMap);
    int updatePamentOrderWithdrawal (HashMap hashMap);
    int updatePgWithdrawal (HashMap hashMap);
    int deleteDanalInfo (HashMap hashMap);
    int updateUserId (HashMap hashMap);
    int updateStudentId (HashMap hashMap);
    int updateShippingUserId (HashMap hashMap);
}
