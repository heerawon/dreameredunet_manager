package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.LectureService;
import com.webstarter.manage.service.SuremBisService;
import com.webstarter.manage.service.teacher.T_MyLectureService;
import com.webstarter.manage.service.teacher.T_ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.event.ObjectChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@Log4j2
@Controller
@RequestMapping("schedule")
@RequiredArgsConstructor
public class T_ScheduleController {

    private final T_ScheduleService t_scheduleService;
    private final T_MyLectureService t_myLectureService;
    private final LectureService lectureService;

    /**
     * 강사의 팀별 해당 달에 해당하는 일정 목록 가져오기
     * @param user 사용자 인증정보 (필수)
     * @param teamId 팀 아이디
     * @param searchMonth 기준 달 (default current date)
     * @param searchYear 기준 연 (default current date)
     * @return rest로 ResponseMessage 보냄
     */
    @PostMapping("/getSchedule")
    public ResponseEntity<HttpMessageModel> getLectureListByTeacher(@LoginUser SessionUser user,
                                                                    @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                                                    @RequestParam(value = "searchMonth", defaultValue = "") String searchMonth,
                                                                    @RequestParam(value = "searchYear", defaultValue = "") String searchYear){

        String teacherId = user.getUserId();
        Map<String,Object> resMap = new HashMap();

        HashMap<String,Object> reqMap = new HashMap<>();
        reqMap.put("fkTuserId",teacherId);
        reqMap.put("searchMonth",searchMonth);
        reqMap.put("searchYear",searchYear);
        reqMap.put("teamId",teamId);

        List<TeamModel> myTeamList = t_myLectureService.getBtnTeacherTeam(teacherId);
        resMap.put("myTeamList", myTeamList);

        List<LectureModel> resList = t_scheduleService.getLectureListByTeacher(reqMap);
        resMap.put("resList",resList);


        if(resList.size()>= 0){
            return new ResponseMessage(200, "success", resMap).getResponse();
        } else {
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }

    /**
     * 일정관리 첫 화면 세팅 - 강사의 강의 팀목록, 임박한 일정 목록
     * @param user 사용자 인증정보 (필수)
     * @param teamId 팀아이디 (추후 화면 전환시 사용, lectureModel때문에 변수명 부득이하게 변경)
     * @param startDt 캘린더에 표시할 기준월일
     * @param model 화면에 돌려줄 값 세팅을 위한 모델
     * @return 일정관리 페이지 호출
     */
    @GetMapping("/upcomingLecture")
    public String preparationList (@LoginUser SessionUser user,
                                   @RequestParam(value = "teamId", defaultValue = "") String teamId,
                                   @RequestParam(value = "startDt", defaultValue = "") String startDt,
                                   Model model) {
        String teacherId = user.getUserId();

        HashMap<String,String> reqMap = new HashMap<>();
        reqMap.put("fkTuserId",teacherId);

        //1. 선생님의 팀 목록 가져오기
        List<TeamModel> myTeamList = t_myLectureService.getBtnTeacherTeam(teacherId);

        if(myTeamList.size() < 1){
            return "redirect:/myLecture/dashboard?errCode=preparationList_001";
        }

        model.addAttribute("myTeamList", myTeamList);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("teamId", teamId);
        model.addAttribute("startDt", startDt);

        //2. 강사의 임박 일정 중 가장 빠른 2일
        List<String> dateList = t_scheduleService.getUpcomingDate(reqMap);
        model.addAttribute("dateList",dateList);

        //3. 2번의 강의 일정 목록 가져오기
        HashMap<Integer,Object> resMap = new HashMap<>();
        for (int i =0; i<dateList.size();i++){
            reqMap.put("searchDt",dateList.get(i));
            List<LectureModel> resList = t_scheduleService.getUpcomingLecture(reqMap);
            resMap.put(i,resList);
            model.addAttribute("resList"+Integer.toString(i+1),resList);
        }

        return "/teacher/schedule/schedule";
    }

    /**
     * 줌링크 수정 (일정관리- 팝업)
     * @param user 사용자 인증정보 (필수)
     * @param lectureId 강의 일정 id
     * @param zoomlink 수정할 줌 링크
     * @param tteamId 팀아이디 (추후 화면 전환시 사용, lectureModel때문에 변수명 부득이하게 변경)
     * @param startDt 캘린더에 표시할 기준월일
     * @param lectureModel 데이터 업데이트를 위한 모델
     * @return 일정 줌링크 업데이트 완료 후 초기 화면으로 redirect
     */
    @GetMapping("/updateZoomLink")
    public String deletePreparation(@LoginUser SessionUser user,
                                    @RequestParam(value = "lectureId", defaultValue = "-1") String lectureId,
                                    @RequestParam(value = "zoomlink", defaultValue = "") String zoomlink,
                                    @RequestParam(value = "tteamId", defaultValue = "") String tteamId,
                                    @RequestParam(value = "startDt", defaultValue = "") String startDt,
                                    LectureModel lectureModel) {

        String teacherId = user.getUserId();

        log.info("teamId :::::::"+tteamId);
        lectureModel.setZoomLink(zoomlink);
        lectureModel.setLectureId(lectureId);

        String msg = t_scheduleService.updateLectureZoomLink(lectureModel);
        if(msg.length()< 1){
            return "redirect:upcomingLecture?teamId="+tteamId+"&startDt="+startDt;
        }
        else{
            //TODO : 에러 뿌리기
            return "redirect:upcomingLecture?teamId="+tteamId+"&startDt="+startDt;
        }
    }

    /**
     * 줌링크 전송 (수정 후 전송)
     * @param lectureId 강의 일정 id
     * @param zoomlink 수정할 줌 링크
     * @param tteamId 팀아이디
     * @param startDt 캘린더에 표시할 기준월일
     */
    @PostMapping("sendMsg")
    @ResponseBody
    public String sendMessage(@LoginUser SessionUser user,
                              @RequestParam(value = "lectureId", defaultValue = "-1") Integer lectureId,
                              @RequestParam(value = "zoomlink", defaultValue = "") String zoomlink,
                              @RequestParam(value = "tteamId", defaultValue = "") String tteamId,
                              @RequestParam(value = "startDt", defaultValue = "") String startDt,
                              @RequestParam(value = "startTime", defaultValue = "") String startTime,
                              LectureModel reqLectureModel
    ) throws ParseException {

        //1. 기준날짜 가져오기
        //2. 오늘날짜
        //3. 기준일 , 오늘날짜 비교
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = dateFormat.parse(startDt+" "+startTime);

//        LocalDate now = LocalDate.now();
        Date now = new Date();
        System.out.println("Date-1: " + dateFormat.format(date1));
        System.out.println("Date-2: " + dateFormat.format(now));
        if(date1.after(now)){
            reqLectureModel.setZoomLink(zoomlink);
            reqLectureModel.setLectureId(lectureId.toString());
            t_scheduleService.updateLectureZoomLink(reqLectureModel);

            HashMap reqMap = new HashMap<String, Object>();
            List<String> lsCell = new ArrayList<String>();
            reqMap.put("fkTuserId",user.getUserId());
            reqMap.put("teamId",tteamId);
            List<StudentModel> studentList = t_myLectureService.selectMyLectureStudentList(reqMap);

            for(StudentModel model : studentList){
                if(model.getStudentCell().length()>10){
                    lsCell.add(model.getStudentCell());
                }
            }
            var resLectureService=lectureService.getLectureDetail(lectureId,user.getUserId());
            if(resLectureService.isSuccess()){
                LectureModel lectureModel = resLectureService.getResObjectData();
                try{
                    if(lsCell.size()>0){
                        SuremBisService.callApiZoomLink(lectureModel.getUserName(),lectureModel.getStartDt()+" "+lectureModel.getStartTime(),lectureModel.getZoomLink(),lsCell);
                    }
                }catch (Exception e){
                    return "전송실패";
                }
            }else{
                return "전송실패";
            }
        }else{
            return "강의 시간이 지나 전송할 수 없습니다.";
        }

        return "";
    }
}
