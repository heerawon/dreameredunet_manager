package com.webstarter.manage.controller;

import com.webstarter.manage.model.*;
import com.webstarter.manage.service.LectureService;
import com.webstarter.manage.service.StudentService;
import com.webstarter.manage.service.TeacherService;
import com.webstarter.manage.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Log4j2
@Controller
@RequestMapping("team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private LectureService lectureService;

    @GetMapping("/list")
    public String getTeamList(Model model,
                              @RequestParam(defaultValue = "0") Integer division,
                              @RequestParam(defaultValue = "-1") Integer programId,
                              @RequestParam(defaultValue = "-1") Integer categoryId,
                              @RequestParam(defaultValue = "") String userName,
                              @RequestParam(defaultValue = "") String teamName,
                              @RequestParam(defaultValue = "") String searchStartDt,
                              @RequestParam(defaultValue = "") String searchEndDt,
                              ResponseService<List<TeamModel>> responseTeamList) {

        String resPage = "";

        //1. 검색 용 프로그램 리스트 가져오기 (시간이 없어서 해당 서비스는 개선 못함)
        List<ProgramModel> programList = teamService.getProgramList();
        model.addAttribute("programList",programList);

        //2. 조건에 맞는 팀 리스트 반환
        responseTeamList  = teamService.getTeamList(division, programId, categoryId, userName, teamName, searchStartDt, searchEndDt);
        if(responseTeamList.isSuccess()){
            model.addAttribute("resList",responseTeamList.getResObjectData());
            model.addAttribute("programId",programId);
            model.addAttribute("userName",userName);
            model.addAttribute("teamName",teamName);
            model.addAttribute("searchStartDt",searchStartDt);
            model.addAttribute("searchEndDt",searchEndDt);
            resPage = "ex_function/team/pub_team_list";
        }else{
            //에러 페이지 이동
            resPage = "ex_function/team/pub_team_list?error";
        }
        return resPage;
    }

    @GetMapping("/register")
    public String getTeamRegister(@RequestParam(value = "id", defaultValue = "") Integer id,
                                     @RequestParam(value = "type", defaultValue = "") String type,
                                     Model model,@ModelAttribute TeamModel teamModel) {
        if("edit".equals(type)){
            teamModel = teamService.getTeamDetail(id);
            model.addAttribute("edit",1);
            model.addAttribute("urlType","update");
        }else{
            model.addAttribute("edit",0);
            model.addAttribute("urlType","insert");
        }
        model.addAttribute("reqModel",teamModel);

        List<ProgramModel> programList = teamService.getProgramList();
        model.addAttribute("programList",programList);

        //강의 일정등을 고려 하여 가져와야 함 (미작업 / 그냥 가져옴)
        List<TeacherModel> teacherList = teacherService.getTeacherTeamList();
        model.addAttribute("teacherList",teacherList);

        return "ex_function/team/pub_team_register1";
    }

    @PostMapping("/insert")
    public String insertTeam(TeamModel teamModel) {
        log.info("insert   team :::::"+teamModel);
        teamModel.setDivision(0);
        teamModel.setTeamStatus(1);

        int teamId = -1;
        try{
            teamId = teamService.insertTeam(teamModel);
        }catch (Exception e) {
            String msg = e.getMessage();
            return "redirect:detail?code="+msg;
        }

        return "redirect:detail?type=register&id="+teamId;
    }

    @GetMapping("/detail")
    public String teamDetail(@RequestParam(value = "id", defaultValue = "-1") Integer teamId,
                             @RequestParam(value = "type", defaultValue = "") String type,
                             Model model ,
                             @ModelAttribute LectureListModel lectureListModel) {
        TeamModel resDetail = teamService.getTeamDetail(teamId);
        model.addAttribute("resModel",resDetail);

        //일정 등록에 필요한 세팅
        if("edit".equals(type)){
            List<LectureModel> lectureList = lectureService.selectLectureList(teamId);
            model.addAttribute("resList", lectureList);
            model.addAttribute("edit",1);
            model.addAttribute("urlType","update");
            model.addAttribute("fkTeamId",teamId);
        }else{
            model.addAttribute("edit",0);
            model.addAttribute("urlType","insert");
            model.addAttribute("fkTeamId",teamId);
        }
        model.addAttribute("reqModel",lectureListModel);

        return "ex_function/team/pub_team_register2";
    }

    @GetMapping("/finishiedDetail")
    public String teamfinishiedDetail(@RequestParam(value = "id", defaultValue = "-1") Integer teamId,
                                      Model model) {
        String returnPage = "";

        TeamModel resDetail = teamService.getTeamDetail(teamId);
        model.addAttribute("resModel",resDetail);

        int haveLecture = teamService.getLectureCount(teamId);
        int haveTeamMember = teamService.getTeamMemberCount(teamId);

        if(haveLecture>0 && haveTeamMember>0){
            //returnPage = "redirect:teamMember/list?teamId="+teamId;
            returnPage = "redirect:/lecture/detail?id="+teamId;
        }else if(haveLecture>0){
            returnPage = "redirect:/lecture/detail?id="+teamId;
        }else if(haveTeamMember>0){
            returnPage = "redirect:/lecture/detail?id="+teamId;
            //returnPage = "redirect:teamMember/list?teamId="+teamId;
        }else{
            returnPage = "redirect:detail?type='register'&id="+teamId;
        }
        return returnPage;
    }

    @GetMapping("/delete")
    public String deleteBoard(@RequestParam(value = "id", defaultValue = "-1") int teamId) {
        String resPath = "redirect:list";
        String resMsg = teamService.deleteTeam(teamId);
        if(resMsg.length()>0){
            //에러 처리
            resPath = resPath+"&error="+resMsg;
        }
        return resPath;
    }

    @PostMapping("/update")
    public String updateTeam(TeamModel teamModel) {
        String resPage = "";

        teamService.updateTeam(teamModel);
        if(teamModel.getTotalLecture() != null){
            //팀 상세로 반환
            resPage = "redirect:detail?id="+teamModel.getTeamId()+"&type=edit";
        }else {
            //강의 상세로 반환 (여기서 학생 등록 할 수 있음)
            resPage = "redirect:/lecture/detail?id="+teamModel.getTeamId();
        }
        return resPage;
    }

    /**
     * 신청 대기 회원 목록 조회
     * TODO 구독 신청일시 기간 검색 일단 제외 -> 추후 논의 후 작업 필요
     */
    @GetMapping("/waitingList")
    public String getWaitTeamList(Model model,
                                  @RequestParam(defaultValue = "0") Integer division,
                                  @RequestParam(defaultValue = "") String userName,
                                  @RequestParam(defaultValue = "") String studentGender,
                                  @RequestParam(defaultValue = "") String birthStartDt,
                                  @RequestParam(defaultValue = "") String birthEndDt,
                                  ResponseService<List<StudentModel>> responseStudentList) {
        responseStudentList = studentService.getWaitTeamList(division, userName, studentGender, birthStartDt, birthEndDt);
        var resList = responseStudentList.isSuccess()? responseStudentList.getResObjectData(): new ArrayList();
            model.addAttribute("resList",resList);
            model.addAttribute("userName",userName);
            model.addAttribute("studentGender",studentGender);
            model.addAttribute("birthStartDt",birthStartDt);
            model.addAttribute("birthEndDt",birthEndDt);
        return "ex_function/team/pub_team_wait_list";
    }

}
