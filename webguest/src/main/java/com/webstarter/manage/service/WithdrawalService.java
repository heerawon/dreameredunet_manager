package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.WithdrawalMapper;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.StudentModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WithdrawalService {
    @Resource
    private WithdrawalMapper withdrawalMapper;

    /**
     * 탈퇴 회원 목록 반환
     * @return 탈퇴일 도래한 학생 목록
     */
    public ResponseService<List<StudentModel>> selectWithdrawalMemberList (){
        String resMsg = "";

        List<StudentModel> studentList = new ArrayList<>();
        try {
            studentList = withdrawalMapper.selectWithdrawalMemberList();
        }catch (Exception e){
            throw new RuntimeException("학생 탈퇴 중 DB 오류 발생");
        }
        return new ResponseService<List<StudentModel>>(resMsg,studentList);
    }

    /**
     * 7일 후 사용자 삭제 실행부 (단일)
     * @param userId 사용자 아이디
     */
    public ResponseService<String> updateUserWithdrawal (String userId){
        String resMsg = "";

        try {
            resMsg = setResetAllTables(userId);
        }catch (Exception e){
            throw new RuntimeException("학생 탈퇴 중 DB 오류 발생");
        }
        return new ResponseService<String>(resMsg,"");
    }

    /**
     * 7일 후 사용자 삭제 실행부 (리스트)
     */
    public ResponseService<String> updateUserWithdrawalByList (){
        String resMsg = "";

        try {
            List<StudentModel> studentList = withdrawalMapper.selectWithdrawalMemberList();
            for (int i = 0; i<studentList.size();i++){
                resMsg = setResetAllTables(studentList.get(i).getFkUserId());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("학생 탈퇴 중 DB 오류 발생");
        }
        return new ResponseService<String>(resMsg,"");
    }

    /**
     * 사용자 삭제 (개인정보 업데이트, 아이디 변경)
     * @param userId 삭제할 사용자 아이디
     */
    @Transactional(rollbackOn = Exception.class)
    public String setResetAllTables (String userId) {
        String resMsg = "";

        String generatedString = getRandomString();
        String newId = userId+generatedString;

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("userId",userId);
        reqMap.put("withdrawal","withdrawal"+generatedString);
        reqMap.put("newUserId",newId);

        if(withdrawalMapper.updateUserWithdrawal(reqMap)<1){
            resMsg = "사용자 개인정보 변경이 되지 않았습니다.";
        }
        if(withdrawalMapper.updateStudentWithdrawal(reqMap)<1){
            resMsg = "사용자 학생 개인정보 초기화가 되지 않았습니다.";
        }
        if(withdrawalMapper.updateShippingWithdrawal(reqMap)<1){
            resMsg = "사용자 배송 개인정보 초기화가 되지 않았습니다.";
        }
        if(withdrawalMapper.updateShippingLocationWithdrawal(reqMap)<1){
            resMsg = "사용자 배송지 개인정보 초기화가 되지 않았습니다.";
        }
        if(withdrawalMapper.updatePamentOrderWithdrawal(reqMap)<1){
            resMsg = "사용자 주문 개인정보 초기화가 되지 않았습니다.";
        }
        if(withdrawalMapper.updatePgWithdrawal(reqMap)<1){
            resMsg = "사용자 결제 개인정보 초기화가 되지 않았습니다.";
        }
        if(withdrawalMapper.deleteDanalInfo(reqMap)<1){
            resMsg = "사용자 다날 정보 삭제 되지 않았습니다.";
        }
        if(withdrawalMapper.updateUserId(reqMap) < 1){
            resMsg = "사용자 개인정보 초기화가 되지 않았습니다.";
        }
        if(withdrawalMapper.updateStudentId(reqMap) < 1){
            resMsg = "학생 개인정보 초기화가 되지 않았습니다.";
        }
        if(withdrawalMapper.updateShippingUserId(reqMap) < 1){
            resMsg = "배송 사용자 아이디 초기화가 되지 않았습니다.";
        }

        return resMsg;
    }

    // 현재시간 + 랜덤 20자 반환
    public String getRandomString (){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(); // 현재 날짜시간 구하기

        return sdf.format(cal.getTime())+generatedString;
    }
}
