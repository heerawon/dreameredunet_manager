package com.webstarter.manage.controller;

import com.sun.tools.jconsole.JConsoleContext;
import com.webstarter.manage.model.*;
import com.webstarter.manage.service.ApplyCourseService;
import com.webstarter.manage.service.TeamMemberService;
import com.webstarter.manage.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("teamMember")
public class TeamMemberController {
    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ApplyCourseService applyCourseService;

    //미수강 학생목록(강의 - 팀)
    @GetMapping("/list")
    public String getMemberList(Model model,
                                @RequestParam(value = "teamId", defaultValue = "") Integer fkTeamId,
                                @RequestParam(value = "fkProgramId", defaultValue = "") Integer fkProgramId,
                                @ModelAttribute TeamMemberModel TeamMemberModel) {

        //팀에 해당되는 프로그램 목록
        List<ProgramModel> programList = teamService.getProgramList();
        model.addAttribute("programList",programList);

        //팀 프로그램에 해당되는 학생 목록을 가져오기 위해 해당 연령 추출
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("fkTeamId",fkTeamId);
        log.info("fkProgramId >> "+fkProgramId);
        if("".equals(fkProgramId)){ //최초에는 팀에 정의된 연령으로 구함
            ProgramModel program = teamService.getProgramAge(fkTeamId);
            hashMap.put("startAge",program.getProgramStartAge());
            hashMap.put("endAge",program.getProgramEndAge());
        }else{
            if(fkProgramId>0){
                // 연령 선택시 해당 프로그램 연령을 가져옴
                ProgramModel programModel = teamService.getProgramAgeByProgramId(fkProgramId);
                hashMap.put("startAge",programModel.getProgramStartAge());
                hashMap.put("endAge",programModel.getProgramEndAge());
            }else{
                //그 외 선택시
                hashMap.put("startAge","");
                hashMap.put("endAge","");
            }
        }

        log.info("startAge >> "+hashMap.get("startAge"));
        log.info("endAge >> "+hashMap.get("endAge"));

        //미수강 학생 목록 1 (no team)
        List<TeamMemberModel> memberList = teamMemberService.getMemberList(hashMap);
        model.addAttribute("resList", memberList);
        log.info("list   resList :::::" + memberList);

        //미수강 학생 목록 2 (다른 team)
        List<TeamMemberModel> memberList2 = teamMemberService.getMemberList2(hashMap);
        model.addAttribute("resList2", memberList2);
        log.info("list   resList2 :::::" + memberList2);

        //현재 Team Id
        model.addAttribute("teamId", fkTeamId);
        model.addAttribute("fkProgramId",fkProgramId);

        return "ex_function/team/pub_team_register_student";
    }

    /**
     * 미수강 학생목록(특별활동)
     * @param fkTeamId 팀 아이디
     * @param fkCategoryId 팀 카테고리
     */
    @GetMapping("/listByCategory")
    public String listByCategory(Model model,
                                 @RequestParam(value = "teamId", defaultValue = "") Integer fkTeamId,
                                 @RequestParam(value = "fkCategoryId", defaultValue = "") Integer fkCategoryId,
                                 @ModelAttribute TeamMemberModel TeamMemberModel,
                                 ResponseService<List<ApplyCourseModel>> responseApplyCourseList) {

        //팀원으로 구성할 수 있는 신청자 목록
        responseApplyCourseList = applyCourseService.getApplyCourceListWithoutMyTeam(fkTeamId,fkCategoryId);
        var memberList = responseApplyCourseList.isSuccess()? responseApplyCourseList.getResObjectData(): new ArrayList();

        model.addAttribute("resList",memberList);
        model.addAttribute("teamId", fkTeamId);
        model.addAttribute("fkCategoryId",fkCategoryId);

        return "ex_function/team/pub_team_register_student";
    }

    //팀멤버 등록
    @PostMapping("/insert")
    public String insertTeamMember(@ModelAttribute("TeamMemberModel") TeamMemberAddForm teamMemberAddForm,
                                   HashMap<String,Object> reqMap,
                                   ResponseService<String> responseInsertTeamMember,
                                   ResponseService<String> responseInsertTeamMemberBySpecialTeam) {

        String resPage = "";
        int teamId = teamMemberAddForm.getMembers().get(0).getFkTeamId(); //페이지 반환에 필요한 팀 아이디
        reqMap.put("teamMemberModel",teamMemberAddForm.getMembers());

        if("".equals(teamMemberAddForm.getMembers().get(0).getApplyCourseId())){
            //일반팀인 경우
            responseInsertTeamMember = teamMemberService.insertTeamMember(reqMap);
            if(responseInsertTeamMember.isSuccess()) resPage = "redirect:/lecture/detail?id="+teamId;
            else {
                log.error(responseInsertTeamMember.getErrorMsg());
                resPage = "redirect:/teamMember/listByCategory?teamId="+teamId+"&error=error";
            }
        }else{
            //특별활동의 경우
            responseInsertTeamMemberBySpecialTeam = teamMemberService.insertTeamMemberBySpeacialTeam(reqMap);
            if(responseInsertTeamMemberBySpecialTeam.isSuccess()) resPage = "redirect:/lecture/detail?id="+teamId;
            else{
                log.error(responseInsertTeamMemberBySpecialTeam.getErrorMsg());
                resPage = "redirect:/teamMember/listByCategory?teamId="+teamId+"&error=error";
            }
        }

        return resPage;
    }

    /**
     * 팀 멤버 탈퇴
     * @param fkTeamId 팀 아이디
     * @param fkSuserId 팀원 아이디
     */
    @PostMapping(value = "deleteTeamMember")
    public ResponseEntity<HttpMessageModel> getSchedule(@RequestParam(defaultValue = "-1") Integer fkTeamId,
                                                        @RequestParam(defaultValue = "") String fkSuserId,
                                                        @RequestParam(defaultValue = "-1") Integer applyCourseId,
                                                        ResponseService<String> responseTeamMemberDelete ) throws Exception {
        log.info("fkTeamId"+fkTeamId);
        log.info("fkTeamId"+fkTeamId);
        log.info("applyCourseId"+applyCourseId);

        responseTeamMemberDelete = teamMemberService.deleteTeamMember(fkTeamId,fkSuserId,applyCourseId);

        if(responseTeamMemberDelete.isSuccess()){
            return new ResponseMessage(200, "success", "").getResponse();
        }else{
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }

}
