package com.webstarter.manage.service;


import com.webstarter.manage.mapper.db1.TeacherMapper;
import com.webstarter.manage.mapper.db1.UserMapper;
import com.webstarter.manage.mapper.db1.teacher.T_MypageMapper;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.TeacherModel;
//import com.webstarter.manage.security.domain.UserPrincipal;
import com.webstarter.manage.security.Role;
import com.webstarter.manage.security.SecurityMemberDTO;
import com.webstarter.manage.security.SecurityMemberService;
import com.webstarter.manage.security.model.UserModel;
import com.webstarter.manage.security.model.UserRoleModel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Service
@AllArgsConstructor
@Slf4j
public class AuthService  {
    @Resource
    private UserMapper userMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private T_MypageMapper t_mypageMapper;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private SecurityMemberService securityMemberService;

    // 패스워드 암호화 객체
    @Resource
    BCryptPasswordEncoder pwEncoding;


    public void updateUserRole(){
        //기간만료 유저 처리
        userMapper.userUpdateRoleByExpiryDate();
    }


    public boolean userVerification(UserModel user) throws UsernameNotFoundException {
        boolean returnvalue = false;
        System.out.println("id ::::: "+ user.getUserId());
        System.out.println("pwd ::::: "+user.getUserPassword());

        UserModel userModel = new UserModel();


        UserModel userAuthes = userMapper.memberLogin(user);
        System.out.println("userAuthes : "+userAuthes);

        if (userAuthes.getUserId()==null){
            throw new UsernameNotFoundException("User "+user.getUserId()+" Not Found!");
        }else{
            if(!pwEncoding.matches(user.getUserPassword(), userAuthes.getUserPassword())){
                throw new BadCredentialsException(user.getUserId());
            }else{
                returnvalue = true;
            }
        }

        return returnvalue;
    }

    public String saveUser(UserModel user, String role) {
        System.out.println("UserModel::::::"+user);
        System.out.println("UserModel:getUserId:::::"+user.getUserId());
        System.out.println("UserModel:getUserPassword:::::"+user.getUserPassword());
        System.out.println("UserModel:getUserPassword:::::"+pwEncoding.encode(user.getUserPassword()));
        user.setUserPassword(pwEncoding.encode(user.getUserPassword()));
        user.setUserActive(1);
        user.setRole(Role.TEACHER);

        //유저 저장
        this.userMapper.setUserInfo(user);


        UserRoleModel userRole = new UserRoleModel();
        userRole.setRole(role);
        userRole.setFkUserId(user.getUserId());

        //유저 Role 저장
        userMapper.setUserRoleInfo(userRole);

        return user.getUserId();
    }

    @Transactional
    public String saveUserTeacher(UserModel user, TeacherModel teacherModel) {
        String userId = user.getUserId();
        String userPw = user.getUserPassword();
        user.setUserActive(1);
        try{
            //유저 저장
            SecurityMemberDTO securityMemberDTO = new SecurityMemberDTO(user.getUserId(),user.getUserName(),"",user.getUserPassword(),Role.TEACHER);

            //user 저장
            var resUser = this.securityMemberService.siginupMember(securityMemberDTO);

            //유저 Role 저장
//            userMapper.setUserRoleInfo(userRole);

            // teacher 정보 저장
            if(!"".equals(resUser.getUserId())){
                teacherModel.setFkUserId(resUser.getUserId());
                this.teacherMapper.insertTeacher(teacherModel);
            }else{
                throw new RuntimeException("user Insert Failed");
            }
        }catch (DuplicateKeyException e){
            throw new RuntimeException("Dup");
        }
        catch (Exception e ){
            throw new RuntimeException("saveUserTeacher ::::::");
        }
        return user.getUserId();
    }

    public void deleteUser(String userId) {
        userMapper.deleteUser(userId);
    }


    /**
     * 사용자로부터 새로운 비밀번호를 받아서 update
     * @param userPassword  새로변경 될PW
     * @param userId  대상 유저
     * @return
     */
    public ResponseService<String> updateUserPassword(String userPassword,String userId) {
        String resMsg ="";
        Map<String,Object> reqMap = new HashMap<String,Object>();

        if(userPassword.trim().length() < 8){
            resMsg = "암호는 공백없시 8자 이상 입력해주세요.";
        }
        try{
            reqMap.put("userPassword",pwEncoding.encode(userPassword));
            reqMap.put("userId",userId);
            if(t_mypageMapper.updateUserPw(reqMap) < 1){
                resMsg = "암호 변경 실패";
            }
        }catch (Exception e){
            e.printStackTrace();
            resMsg = "암호 변경 오류";
        }
        return new ResponseService<String>(resMsg, "");
    }
}
