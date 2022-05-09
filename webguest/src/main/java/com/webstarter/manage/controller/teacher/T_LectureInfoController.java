package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.LectureService;
import com.webstarter.manage.service.TeamService;
import com.webstarter.manage.service.teacher.T_MyLectureService;
import com.webstarter.manage.service.teacher.T_TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("t_lecture")
public class T_LectureInfoController {

    @Autowired
    private T_MyLectureService t_myLectureService;

    @Autowired
    private T_TeamService t_teamService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private LectureService lectureService;

    //일정 수정 페이지 이동
    @GetMapping("/lectureInfo")
    public String lectureDetail(Model model ,
                                @LoginUser SessionUser user,
                                @RequestParam(defaultValue = "-1") Integer teamId,
                                @ModelAttribute LectureListModel lectureListModel) {

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

        List<LectureModel> lectureList = lectureService.selectLectureList(teamId);
        model.addAttribute("resList", lectureList);
        model.addAttribute("fkTeamId",teamId);

        model.addAttribute("reqModel",lectureListModel);

        return "teacher/myLecture/lectureInfo/lecture_info_detail";
    }

    /**
     * 강의 수정 (단건)
     * @param fkTeamId 강의 팀 아이디
     * @param lectureModel 수정할 강의정보 받아오는 model (단건 처리)
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
                return new ResponseMessage(502, "false", responseUpdateTeamInfo.getErrorMsg()).getResponse();
            }
        }else{
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }
}
