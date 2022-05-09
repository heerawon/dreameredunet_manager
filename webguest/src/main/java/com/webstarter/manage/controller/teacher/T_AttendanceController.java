package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.LectureService;
import com.webstarter.manage.service.TeamService;
import com.webstarter.manage.service.teacher.T_AttendanceService;
import com.webstarter.manage.service.teacher.T_MyLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("attendance")
public class T_AttendanceController {

    @Autowired
    private T_MyLectureService t_myLectureService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private T_AttendanceService t_attendanceService;

    @Autowired
    private LectureService lectureService;

    /**
     * 출석 체크 화면 (팀의 학생별 강의 정보 )
     * @param user 사용자 인증정보 (필수)
     * @param teamId 팀 아이디 (필수)
     * @param model 화면에 돌려줄 값 세팅을 위한 모델
     * @return 출석현황 페이지 호출
     */
    @GetMapping("/lectureInfo")
    public String attendanceList (@LoginUser SessionUser user,
                                  @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                  Model model) {

        String teacherId = user.getUserId();

        //1. 선생님의 팀 목록 가져오기
        List<TeamModel> myTeamList = t_myLectureService.getBtnTeacherTeam(teacherId);
        model.addAttribute("myTeamList", myTeamList);

        //2. 팀 기초정보 가져오기
        TeamModel myTeam = teamService.getTeamDetail(teamId);
        model.addAttribute("myTeam", myTeam);
        int countMember = teamService.getTeamMemberCount(teamId);
        model.addAttribute("countMember",countMember);
        int countLecture = teamService.getLectureCount(teamId);
        model.addAttribute("countLecture",countLecture);

        //3. 수강학생
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("teamId",teamId.toString());
        hashMap.put("fkTuserId", user.getUserId());
//        List<StudentModel> studentList = t_attendanceService.getTeamMemberInfo(hashMap);
        List<StudentModel> studentList = t_myLectureService.selectStudentDetailList(hashMap);
        model.addAttribute("studentList",studentList);

        //4. 강의 정보
        List<LectureModel> lectureList = lectureService.selectLectureList(teamId);
        model.addAttribute("lectureList",lectureList);
        List<LectureModel> teamLectureList = t_attendanceService.getLectureInfoByTeamMember(hashMap);
        model.addAttribute("resList",teamLectureList);

        //5.오늘의 출석 현황
        // 5-1. 오늘 날짜에 강의가 있는지 //5-2. 오늘 날짜에 강의가 없다면 가장 최근 강의 날짜
        //LectureModel lectureModel = t_attendanceService.getMostRecentDate(hashMap);
        String resStrDate = t_attendanceService.getMostRecentDate(hashMap);
        if(resStrDate != null ) {
            // 5-3. 날짜의 출석현황 가져오기 (출석 인원수)
            hashMap.put("startDt", resStrDate);
            Integer attendCount = t_attendanceService.getAttendByDate(hashMap);
            model.addAttribute("attendDt", resStrDate);
            model.addAttribute("attendCount", attendCount);
            model.addAttribute("attendPercent",Math.round(((double)attendCount/(double)countMember)*100));
        }

        model.addAttribute("fkStartDt",resStrDate);


        return "/teacher/myLecture/attendance/lecture_attendance";
    }

    @PostMapping("/insert")
    public ResponseEntity<HttpMessageModel> changeAttendStatus(AttendanceModel attendModel){
        System.out.println("attend insert :::::" + attendModel);

        try {
            Integer attendId = t_attendanceService.insertAttend(attendModel);
            System.out.println("success ::::: attendId ::::: "+attendId);
            return new ResponseMessage(200, "success", "").getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }

    @PostMapping("/updateMemo")
    public ResponseEntity<HttpMessageModel> updateMemo (AttendanceModel attendModel){
        try {
            Integer attendId = t_attendanceService.insertAttend(attendModel);
            System.out.println("success ::::: attendId ::::: "+attendId);
            return new ResponseMessage(200, "success", "").getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }

    /**
     * 날짜별 출석현황 조회
     * @param resStrDate 기준 날짜
     * @param teamId 해당 팀 아이디
     */
    @PostMapping("/getScheduleByDate")
    public ResponseEntity<HttpMessageModel> getScheduleByDate (@RequestParam(defaultValue = "") String resStrDate,
                                                               @RequestParam(defaultValue = "-1") Integer teamId,
                                                               ResponseService<Integer> responseLectureCountService,
                                                               HashMap<String,Object> reqMap){

        try {
            Map<String,Object> resMap = new HashMap();
            //1. 팀의 학생수
            int countMember = teamService.getTeamMemberCount(teamId);

            //2. 날짜의 출석현황 가져오기 (출석 인원수)
            reqMap.put("startDt", resStrDate);
            reqMap.put("teamId", teamId);
            Integer attendCount = t_attendanceService.getAttendByDate(reqMap);

            resMap.put("countMember", countMember);
            resMap.put("attendDt", resStrDate);
            resMap.put("attendCount", attendCount);

            if("".equals(resStrDate)){
                responseLectureCountService = lectureService.getAllLectureCount(teamId);
                var allLectureCount = responseLectureCountService.getResObjectData();
                resMap.put("allLectureCount",allLectureCount);
                resMap.put("attendPercent",Math.round(((double)attendCount/(double)(countMember*allLectureCount))*100));
            }else {
                resMap.put("allLectureCount",0);
                resMap.put("attendPercent",Math.round(((double)attendCount/(double)countMember)*100));
            }

            return new ResponseMessage(200, "success", resMap).getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }
}
