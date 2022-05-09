package com.webstarter.manage.controller;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.LectureService;
import com.webstarter.manage.service.StudentService;
import com.webstarter.manage.service.TeamMemberService;
import com.webstarter.manage.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("lecture")
public class LectureController {
    @Autowired
    private LectureService lectureService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private StudentService studentService;

    //강의 일정 목록
    @GetMapping("/list")
    public String getLectureList(Model model) {
        List<LectureModel> TeamList = lectureService.selectLectureList(0);
        model.addAttribute("resList", TeamList);
        return "ex_function/team/pub_team_list";
    }

    //강의 상세
    @GetMapping("/detail")
    public String lectureDetail(@RequestParam(value = "id", defaultValue = "") Integer fkTeamId,
                                Model model) {

        String returnPage = "";

        //팀 정보
        TeamModel resDetail = teamService.getTeamDetail(fkTeamId);
        model.addAttribute("resModel",resDetail);

        //다가오는 강의 정보
        LectureModel nearLecture = lectureService.selectNearestLecture(fkTeamId);
        model.addAttribute("nearLecture",nearLecture);

        //강의 일정 목록
        List<LectureModel> lectureList = lectureService.selectLectureList(fkTeamId);
        model.addAttribute("resList", lectureList);

        //수강 학생 목록 (강의에 수강상태인 학생)
        List<TeamMemberModel> teamMemberList = teamMemberService.getTeamMemberList(fkTeamId);
        model.addAttribute("teamMemberList", teamMemberList);

        if(resDetail.getDivision()>0){
            //specail team
            returnPage = "ex_function/special_activity/pub_special_team_register3";
        }else{
            //team
            returnPage = "ex_function/team/pub_team_register3";
        }

        return returnPage;
    }

    //강의 일정 등록
    @PostMapping("/insert")
    public String insertLecture(@RequestParam("fkTeamId") Integer fkTeamId,
                                @ModelAttribute("lectureAddForm") LectureAddForm lectureAddForm,
                                HashMap<String,Object> reqMap) {

        if(lectureAddForm.getLectures() == null){
            //값 가져오기 실패
            return "";
        }

        //강의 등록시 팀 테이블에 있는 일정(전체강의수, 강의시작일) 업데이트
        TeamModel teamModel = new TeamModel();
        teamModel.setTeamId(fkTeamId);
        teamModel.setStartDt(lectureAddForm.getLectures().get(0).getStartDt());
        teamModel.setEndDt(lectureAddForm.getLectures().get(lectureAddForm.getLectures().size()-1).getStartDt()); //마지막 항목의 시작일
        teamModel.setTotalLecture(lectureAddForm.getLectures().size());
        teamService.updateTeamSchedule(teamModel);

        reqMap.put("lectureModel",lectureAddForm.getLectures());
        String msg = lectureService.insertLecture(reqMap);

        if(msg.length()<1){
            //성공
        }else{
            //실패
        }

        return "redirect:detail?id="+fkTeamId;
    }

    //강의 일정 전체 업데이트
    @PostMapping("/updateAll")
    public String updateLecture(@RequestParam("fkTeamId") Integer fkTeamId,
                                @ModelAttribute("lectureAddForm") LectureAddForm lectureAddForm,
                                HashMap<String,Object> reqMap) {

        if(lectureAddForm.getLectures() == null){
            //값 가져오기 실패
            return "";
        }

        //강의 등록시 팀 테이블에 있는 일정(전체강의수, 강의시작일) 업데이트
        TeamModel teamModel = new TeamModel();
        teamModel.setTeamId(fkTeamId);
        teamModel.setStartDt(lectureAddForm.getLectures().get(0).getStartDt());
        teamModel.setEndDt(lectureAddForm.getLectures().get(lectureAddForm.getLectures().size()-1).getStartDt()); //마지막 항목의 시작일
        teamModel.setTotalLecture(lectureAddForm.getLectures().size());
        teamService.updateTeamSchedule(teamModel);

        reqMap.put("lectureModel",lectureAddForm.getLectures());
        String msg = lectureService.insertLecture(reqMap);

        if(msg.length()<1){
            //성공
        }else{
            //실패
        }
        return "redirect:detail?id="+fkTeamId;
    }

    /**
     * 강의 수정
     * @param fkTeamId 강의 팀 아이디
     * @param lectureModel 수정할 강의정보 받아오는 model
     */
    @PostMapping("/updateLecture")
    public ResponseEntity<HttpMessageModel> updateOneLecture(@RequestParam("fkTeamId") Integer fkTeamId,
                                                             @ModelAttribute LectureListModel lectureModel,
                                                             ResponseService<String> responseUpdateService,
                                                             ResponseService<String> responseUpdateTeamInfo) {

        responseUpdateService = lectureService.updateOneLecture(lectureModel);
        if(responseUpdateService.isSuccess()){
            responseUpdateTeamInfo = lectureService.updateTeamInfoLatest(fkTeamId);
            if(responseUpdateTeamInfo.isSuccess()){
                return new ResponseMessage(200, "success", "").getResponse();
            }else{
                log.error(responseUpdateTeamInfo.getErrorMsg());
                return new ResponseMessage(502, "false", responseUpdateTeamInfo.getErrorMsg()).getResponse();
            }
        }else{
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }


    /**
     * 강의 단건 추가
     * @param lectureModel 강의 정보 모델
     */
    @PostMapping(value = "insertMoreLecture")
    public ResponseEntity<HttpMessageModel> insertMoreLecture(@ModelAttribute LectureListModel lectureModel,
                                                              ResponseService<String> responseInsertMoreLecture,
                                                              ResponseService<String> responseUpdateTeamInfo) {

        responseInsertMoreLecture = lectureService.insertOneLecture(lectureModel);

        if(responseInsertMoreLecture.isSuccess()){
            responseUpdateTeamInfo = lectureService.updateTeamInfoLatest(lectureModel.getFkTeamId());
            if(responseUpdateTeamInfo.isSuccess()){
                return new ResponseMessage(200, "success", responseInsertMoreLecture.getResObjectData()).getResponse();
            }else{
                log.error(responseUpdateTeamInfo.getErrorMsg());
                return new ResponseMessage(502, "false", responseUpdateTeamInfo.getErrorMsg()).getResponse();
            }
        }else{
            log.error(responseInsertMoreLecture.getErrorMsg());
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }

    /**
     * TODO : 비동기로 만드려고 했으나 스크립트 조정에 시간을 너무 많이 할애 할 것 같아서 전송으로 처리 함
     * 강의 단건 삭제
     * @param lectureId 삭제할 강의 아이디
     * @param fkTeamId 삭제 후 화면 반환을 위해 받는 팀 아이디
     */
    @GetMapping("/deleteOneLecture")
    public String deleteOneLecture(@RequestParam(defaultValue = "") Integer lectureId,
                                   @RequestParam(defaultValue = "") Integer fkTeamId,
                                   ResponseService<String> responseDeleteLecture,
                                   ResponseService<String> responseUpdateTeamInfo) {

        String returnPage = "";

        responseDeleteLecture = lectureService.deleteLecture(lectureId, fkTeamId);
        if(responseDeleteLecture.isSuccess()){
            responseUpdateTeamInfo = lectureService.updateTeamInfoLatest(fkTeamId);
            if(responseUpdateTeamInfo.isSuccess()){
                returnPage = "redirect:/team/detail?type=edit&id="+fkTeamId;
            }else {
                log.error("here 1 ");
                log.error(responseUpdateTeamInfo.getErrorMsg());
                returnPage = "redirect:/team/detail?type=edit&id="+fkTeamId+"&error=error";
            }
        }else{
            //에러 페이지
            log.error("here 2 ");
            returnPage = "redirect:/team/detail?type=edit&id="+fkTeamId+"&error=error";
        }

        return returnPage;
    }

    /**
     * 팀의 일정 목록 (강의 상세 화면 캘린더)
     * @param teamId 팀 아이디
     * @param searchMonth 기준 달 (default current date)
     * @param searchYear 기준 연 (default current date)
     */
    @PostMapping("/getTeamSchedule")
    public ResponseEntity<HttpMessageModel> getLectureListByTeam(@RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                                                 @RequestParam(value = "searchMonth", defaultValue = "") String searchMonth,
                                                                 @RequestParam(value = "searchYear", defaultValue = "") String searchYear,
                                                                 ResponseService<List<LectureModel>> responseScheduleList ){

        responseScheduleList = lectureService.getLectureListByTeam(teamId, searchMonth, searchYear);

        if(responseScheduleList.isSuccess()){
            return new ResponseMessage(200, "success", responseScheduleList.getResObjectData()).getResponse();
        } else {
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }

}

