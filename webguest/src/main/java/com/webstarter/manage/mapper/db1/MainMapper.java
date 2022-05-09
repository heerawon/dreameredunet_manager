package com.webstarter.manage.mapper.db1;



import com.webstarter.manage.model.MainUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 *  Main DB (Main DB)
 *  Test 용 샘플입니다.
 *
 */
@Mapper
@Repository
public interface MainMapper {
    String isLoginHashMap (Map<String, Object> reqMap); // Map 으로 요청
    String isLoginModel (MainUserModel reqModel); //  Model 로 요청
    String isLoginString (String reqStr); //   String 으로 요청
    List<MainUserModel> getUserList (); // Model 로 받기


}