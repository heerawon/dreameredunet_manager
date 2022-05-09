package com.webstarter.manage.service;

import com.webstarter.manage.mapper.db1.StudentMapper;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.StudentModel;
import com.webstarter.manage.model.TeamModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;

    /**
     * 학생 목록 조회
     * @param userName 학생 이름
     * @param studentGender 학생 성별
     * @param birthStartDt 생일 검색용 - 기간 시작일
     * @param birthEndDt 생일 검색용 - 기간 종료일
     */
    public ResponseService<List<StudentModel>> getStudentList(String userName, String studentGender, String birthStartDt, String birthEndDt){
        String resMsg = "";
        List<StudentModel> studentList = new ArrayList<StudentModel>();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userName", userName);
        hashMap.put("studentGender", studentGender);
        hashMap.put("birthStartDt", birthStartDt);
        hashMap.put("birthEndDt", birthEndDt);

        try {
            studentList = studentMapper.getStudentList(hashMap);
        }catch (Exception e){
            resMsg = "사용자 목록 조회 실패";
        }

        return new ResponseService<List<StudentModel>>(resMsg, studentList);
    }

    /**
     * 학생 상세 조회
     * @param fkUserId 조회할 회원의 아이디
     */
    public ResponseService<StudentModel>  getStudentDetail(String fkUserId){
        String resMsg = "";
        StudentModel student = new StudentModel();
        try {
            student = studentMapper.getStudentDetail(fkUserId);
        }catch (Exception e){
            resMsg = "사용자 상세 조회중 실패";
        }
        return new ResponseService<StudentModel>(resMsg, student);
    }

    /**
     * 학생 정보 수정
     * @param student 학생 정보를 업데이트 할 모델
     */
    @Transactional(rollbackOn = Exception.class)
    public ResponseService<String> updateStudent (StudentModel student){
        String resMsg = "";
        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("userName",student.getUserName());
        reqMap.put("userId",student.getUserId());
        try {
            if(studentMapper.updateUserName(reqMap) < 1){
                resMsg = "사용자 이름 변경 되지 않았습니다.";
            }
            if(studentMapper.updateStudent(student) < 1){
                resMsg = "사용자 정보 변경이 되지 않았습니다.";
            }
            if(student.getExpiredDt() != null){
                reqMap.put("role","MEMBER");
                if(studentMapper.updateUserRole(reqMap) < 1){
                    resMsg = "사용자 권한 변경이 되지 않았습니다.";
                }
            }
        }catch (Exception e){
            throw new RuntimeException("학생 정보 수정 중 DB 오류 발생");
        }
        return new ResponseService<String>(resMsg,"");
    }

    /**
     * 신청 대기 회원 목록 조회
     * @param division 구분 (0: 팀 / 1: 특별활동)
     * @param userName 학생 이름
     * @param studentGender 학생 성별
     * @param birthStartDt 학생 생일 검색 - 시작일
     * @param birthEndDt 학생 생일 검색 - 완료일
     */
    public ResponseService<List<StudentModel>> getWaitTeamList(Integer division, String userName, String studentGender, String birthStartDt, String birthEndDt){
        String resMsg = "";
        List<StudentModel> waitList = new ArrayList<StudentModel>();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("division", division);
        hashMap.put("userName", userName);
        hashMap.put("studentGender", studentGender);
        hashMap.put("birthStartDt", birthStartDt);
        hashMap.put("birthEndDt", birthEndDt);

        try {
            waitList = studentMapper.getWaitTeamList(hashMap);
        }catch (Exception e){
            resMsg = "신청 대기 회원 목록 조회 실패";
        }

        return new ResponseService<List<StudentModel>>(resMsg, waitList);
    }

    /**
     * 회원 탈퇴 세팅
     * @param userId 탈퇴 회원 아이디
     */
    @Transactional(rollbackOn = Exception.class)
    public ResponseService<String> updateUserWithdrawal (String userId){
        String resMsg = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance(); // 현재 날짜시간 구하기
        cal.add(Calendar.DATE,7);

        log.info("delete userId ::::::"+userId);

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("role","GUEST");
        reqMap.put("withdrawalDt",sdf.format(cal.getTime()));
        reqMap.put("userId",userId);
        try {
            if(studentMapper.updateUserRole(reqMap) < 1){
                resMsg = "사용자 정보 변경이 되지 않았습니다.";
            }
            if(studentMapper.updateUserWithdrawal(reqMap) < 1){
                resMsg = "사용자 탈퇴 변경 되지 않았습니다.";
            }
        }catch (Exception e){
            throw new RuntimeException("학생 탈퇴 중 DB 오류 발생");
        }
        return new ResponseService<String>(resMsg,sdf.format(cal.getTime()));
    }

}
