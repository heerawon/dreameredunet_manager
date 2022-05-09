package com.webstarter.manage.controller;

import com.webstarter.manage.model.*;
import com.webstarter.manage.service.*;
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
@RequestMapping("specialActivity")
public class SpecialActivityController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private ApplyCourseService applyCourseService;

    @GetMapping("/list")
    public String getTeamList(Model model,
                              @RequestParam(defaultValue = "1") Integer division,
                              @RequestParam(defaultValue = "-1") Integer programId,
                              @RequestParam(defaultValue = "-1") Integer categoryId,
                              @RequestParam(defaultValue = "") String userName,
                              @RequestParam(defaultValue = "") String teamName,
                              @RequestParam(defaultValue = "") String searchStartDt,
                              @RequestParam(defaultValue = "") String searchEndDt,
                              ResponseService<List<TeamModel>> responseTeamList) {

        String resPage = "";

        //1. 검색 용 카테고리 리스트 가져오기 (시간이 없어서 해당 서비스는 개선 못함)
        List<TeamCategoryModel> categoryList = teamService.getTeamCategoryList();
        model.addAttribute("categoryList",categoryList);

        //2. 조건에 맞는 팀 리스트 반환
        responseTeamList  = teamService.getTeamList(division, programId, categoryId, userName, teamName, searchStartDt, searchEndDt);
        if(responseTeamList.isSuccess()){
            model.addAttribute("resList",responseTeamList.getResObjectData());
            model.addAttribute("categoryId",categoryId);
            model.addAttribute("userName",userName);
            model.addAttribute("teamName",teamName);
            model.addAttribute("searchStartDt",searchStartDt);
            model.addAttribute("searchEndDt",searchEndDt);
            resPage = "ex_function/special_activity/pub_special_team_list";
        }else{
            //에러 페이지 이동
            resPage = "ex_function/special_activity/pub_special_team_list?error";
        }
        return resPage;
    }

    @GetMapping("/register")
    public String getTeamRegister(Model model,
                                  @RequestParam(value = "id", defaultValue = "") Integer id,
                                  @RequestParam(value = "type", defaultValue = "") String type,
                                  @ModelAttribute TeamModel teamModel,
                                  ResponseService<List<StudentModel>> responseStudentList) {
        log.info("register :::::"+id);
        if("edit".equals(type)){
            teamModel = teamService.getTeamDetail(id);
            model.addAttribute("edit",1);
            model.addAttribute("urlType","update");
        }else{
            model.addAttribute("edit",0);
            model.addAttribute("urlType","insert");
        }
        model.addAttribute("reqModel",teamModel);

        List<TeamCategoryModel> categoryList = teamService.getTeamCategoryList();
        model.addAttribute("categoryList",categoryList);

        List<TeacherModel> teacherList = teacherService.getTeacherList();
        model.addAttribute("teacherList",teacherList);

        responseStudentList = studentService.getStudentList("","","","");
        if(responseStudentList.isSuccess()){
            model.addAttribute("studentList",responseStudentList.getResObjectData());
        }

        log.info("Team :::::"+teamModel);
        log.info("Team :::::"+teamModel.getTeamId());
        log.info("programList :::::"+categoryList);

        return "ex_function/special_activity/pub_special_team_register1";
    }

    @PostMapping("/insert")
    public String insertTeam(TeamModel teamModel) {
        log.info("insert   team :::::"+teamModel);
        log.info("insert   getFkCategoryId :::::"+teamModel.getFkCategoryId());
        teamModel.setDivision(1);
        teamModel.setTeamStatus(1);

        int teamId = -1;
        try{
            teamId = teamService.insertTeam(teamModel);
            log.info("insert TeacherId ::::::"+teamId);
        }catch (Exception e) {
            String msg = e.getMessage();
            return "redirect:detail?code="+msg;
        }
        return "redirect:detail?type=register&id="+teamId;
    }

    @GetMapping("/detail")
    public String teamDetail(@RequestParam(value = "id", defaultValue = "-1") Integer teamId,
                             @RequestParam(value = "type", defaultValue = "") String type,
                             Model model,
                             @ModelAttribute LectureListModel lectureListModel) {
        log.info("detail? :::::");
        log.info("teamId :::::"+teamId);
        TeamModel resDetail = teamService.getTeamDetail(teamId);
        log.info("resBoard :::::"+resDetail);
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

        return "ex_function/special_activity/pub_special_team_register2";
    }

    @GetMapping("/finishiedDetail")
    public String teamfinishiedDetail(@RequestParam(value = "id", defaultValue = "-1") Integer teamId,
                                      Model model) {
        String returnPage = "";

        log.info("finishiedDetail :::::");
        TeamModel resDetail = teamService.getTeamDetail(teamId);
        model.addAttribute("resModel",resDetail);

        log.info("finishiedDetail resModel :::::"+ resDetail);

        int haveLecture = teamService.getLectureCount(teamId);
        int haveTeamMember = teamService.getTeamMemberCount(teamId);

        log.info("haveLecture :::::"+haveLecture);
        log.info("haveTeamMember :::::"+haveTeamMember);

        if(haveLecture>0 && haveTeamMember>0){
            log.info("detail type ::::: 1");
            //returnPage = "redirect:teamMember/list?teamId="+teamId;
            returnPage = "redirect:/lecture/detail?id="+teamId;
        }else if(haveLecture>0){
            log.info("detail type ::::: 2");
            returnPage = "redirect:/lecture/detail?id="+teamId;
        }else if(haveTeamMember>0){
            log.info("detail type ::::: 3");
            returnPage = "redirect:/lecture/detail?id="+teamId;
            //returnPage = "redirect:teamMember/list?teamId="+teamId;
        }else{
            log.info("detail type ::::: 4");
            returnPage = "redirect:detail?type='register'&id="+teamId;
        }
        return returnPage;
    }

    @GetMapping("/delete")
    public String deleteBoard(@RequestParam(value = "id", defaultValue = "-1") int teamId) {
        log.info("teamId ::::::"+teamId);
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
        log.info("update   team :::::"+teamModel);
        log.info("update   getFkCategoryId :::::"+teamModel.getFkCategoryId());
        teamService.updateTeam(teamModel);
        log.info("update TeacherId ::::::"+teamModel.getTeamId());
        return "redirect:detail?id="+teamModel.getTeamId();
    }


    /**
     * 특별활동 신청 회원 목록 (2) : 신청 대기 회원 진입시
     * @param fkCategoryId 카테고리
     * @param userName 이름
     * @param studentGender 성별
     * @param birthStartDt 생일 - 검색 시작일
     * @param birthEndDt 생일 - 검색 종료일
     * @param searchStartDt 신청일 - 검색 시작일
     * @param searchEndDt 종료일 - 검색 종료일
     */
    @GetMapping("/waitingList")
    public String getWaitTeamList(Model model,
                                  @RequestParam(defaultValue = "-1") Integer teamId,
                                  @RequestParam(defaultValue = "-1") Integer fkCategoryId,
                                  @RequestParam(defaultValue = "") String userName,
                                  @RequestParam(defaultValue = "") String studentGender,
                                  @RequestParam(defaultValue = "") String birthStartDt,
                                  @RequestParam(defaultValue = "") String birthEndDt,
                                  @RequestParam(defaultValue = "") String searchStartDt,
                                  @RequestParam(defaultValue = "") String searchEndDt,
                                  ResponseService<List<ApplyCourseModel>> responseApplyCourseList) {

        //팀원으로 구성할 수 있는 신청자 목록
        responseApplyCourseList = applyCourseService.getApplyCourseList(teamId,fkCategoryId,userName,studentGender,birthStartDt,birthEndDt,searchStartDt,searchEndDt);
        var memberList = responseApplyCourseList.isSuccess()? responseApplyCourseList.getResObjectData(): new ArrayList();
        model.addAttribute("resList",memberList);
        model.addAttribute("userName",userName);
        model.addAttribute("studentGender",studentGender);
        model.addAttribute("birthStartDt",birthStartDt);
        model.addAttribute("birthEndDt",birthEndDt);

        return "ex_function/special_activity/pub_special_team_wait_list";
    }

}
