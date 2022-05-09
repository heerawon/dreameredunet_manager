package com.webstarter.manage.mapper.db1;

import com.webstarter.manage.model.StudentModel;
import com.webstarter.manage.security.model.UserModel;
import com.webstarter.manage.security.model.UserRoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    UserModel findUserByLoginId (String userName);
    UserModel memberLogin (UserModel user);
    int setUserInfo (UserModel userModel);
    int setUserRoleInfo (UserRoleModel userRoleModel);

    List<UserModel> getUserList (String userType);
    int setManagerInfo (UserModel userModel);
    UserModel getUserInfo(String userId);

    int deleteUser (String userId);
    int userUpdateRoleByExpiryDate (); //기간만료 유저
    List<StudentModel> getUserListExpiredSoon (Integer intervalDate); //기간만료 유저  date 일 후 만료되는 유저
}
