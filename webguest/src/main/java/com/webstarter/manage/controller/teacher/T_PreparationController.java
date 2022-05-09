package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.TeamService;
import com.webstarter.manage.service.teacher.T_MyLectureService;
import com.webstarter.manage.service.teacher.T_PreparationService;
import com.webstarter.manage.service.teacher.T_ReplyService;
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
@RequestMapping("preparation")
public class T_PreparationController {

    @Autowired
    private T_PreparationService t_preparationService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private T_ReplyService t_replyService;

    @Autowired
    private T_MyLectureService t_myLectureService;

    @GetMapping("/list")
    public String preparationList (@LoginUser SessionUser user,
                                   @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                   @RequestParam(value = "division", defaultValue = "-1") Integer division,
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
        //3.예습,복습 목록
        HashMap<String,Integer> hashMap = new HashMap<>();
        hashMap.put("fkTeamId",teamId);
        hashMap.put("division",division);
        List<PreparationModel> resList = t_preparationService.getPreparationList(hashMap);
        model.addAttribute("resList",resList);

        if(division == 0){
            return "/teacher/myLecture/review/lecture_review";
        }else{
            return "/teacher/myLecture/preview/lecture_preview";
        }

    }

    @GetMapping("/register")
    public String registerPreparation (@LoginUser SessionUser user,
                                 @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                 @RequestParam(value = "division", defaultValue = "-1") Integer division,
                                 @RequestParam(value = "type", defaultValue = "") String type,
                                 @RequestParam(value = "preparationId", defaultValue = "") Integer preparationId,
                                 @ModelAttribute PreparationModel preparationModel, Model model) {

        String teacherId = user.getUserId();
        //1. 선생님의 팀 목록 가져오기
        List<TeamModel> myTeamList = t_myLectureService.getBtnTeacherTeam(teacherId);
        model.addAttribute("myTeamList", myTeamList);
        model.addAttribute("teacherId", teacherId);
        //2. 팀 기초정보 가져오기
        TeamModel myTeam = teamService.getTeamDetail(teamId);
        model.addAttribute("myTeam", myTeam);
        int countMember = teamService.getTeamMemberCount(teamId);
        model.addAttribute("countMember",countMember);
        int countLecture = teamService.getLectureCount(teamId);
        model.addAttribute("countLecture",countLecture);
        //3. 예복습 모델 세팅
        preparationModel.setFkTeamId(teamId);
        preparationModel.setDivision(division);
        if ("edit".equals(type)) {
            preparationModel = t_preparationService.getPreparationDetail(preparationId);
            log.info("register ::::: edit ::::: "+preparationModel);
            model.addAttribute("edit", 1);
            model.addAttribute("urlType","update");
        } else {
            model.addAttribute("edit", 0);
            model.addAttribute("urlType","insert");
        }
        model.addAttribute("resModel", preparationModel);

        if(division==0){
            return "/teacher/myLecture/review/lecture_review_register";
        }else{
            return "/teacher/myLecture/preview/lecture_preview_register";
        }
    }

    @PostMapping("/insert")
    public String insertPreparation(@LoginUser SessionUser user,
                                    PreparationModel preparationModel) {
        String teacherId = user.getUserId();
        Integer teamId = preparationModel.getFkTeamId();
        Integer division = preparationModel.getDivision();
        Integer preparationId = 0;
        //appearance 값이 true 가 아니면 0 으로 세팅
        if(preparationModel.getAppearance() ==null){
            preparationModel.setAppearance(0);
        }
        //최초건의 경우 예외적으로 1로 세팅 (1개는 무조건 노출)
        PreparationModel exPrepa = t_preparationService.getExposurePreparationId(preparationModel);
        if(exPrepa == null){
            preparationModel.setAppearance(1);
        }
        try {
            preparationId = t_preparationService.insertPreparation(preparationModel);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO:에러 다시 register로 보내기
            return "redirect:detail?teamId="+teamId+"&division="+ division + "&code=30000";
        }
        return "redirect:detail?teamId="+teamId+"&division="+ division+"&preparationId="+preparationId;
    }

    @PostMapping("/update")
    public String updatePreparation(@LoginUser SessionUser user,
                                    @RequestParam(value = "preparationId", defaultValue = "") Integer preparationId,
                                    PreparationModel preparationModel) {
        String teacherId = user.getUserId();
        Integer teamId = preparationModel.getFkTeamId();
        Integer division = preparationModel.getDivision();
        if(preparationModel.getAppearance() ==null){
            preparationModel.setAppearance(0);
        }
        try {
            t_preparationService.updatePreparation(preparationModel);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:register?teamId="+teamId+"&division="+division+"&edit=1"+"&code=30000";
        }
        return "redirect:detail?teamId="+teamId+"&division="+division+"&preparationId="+preparationId;
    }

    @GetMapping("/detail")
    public String preparationDetail(Model model,
                                    @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                    @RequestParam(value = "division", defaultValue = "-1") Integer division,
                                    @RequestParam(value = "preparationId", defaultValue = "-1") Integer preparationId,
                                    @ModelAttribute ReplyModel replyModel,
                                    @LoginUser SessionUser user,
                                    ResponseService<List<ReplyModel>> replyModelResponseService,
                                    ResponseService<ReplyModel> responseLastDayService) {

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
        //3. 예,복습 상세
        PreparationModel preparation = t_preparationService.getPreparationDetail(preparationId);
        model.addAttribute("resModel", preparation);

        //4. 예,복습  댓글 목록 가져오기
        replyModelResponseService = t_replyService.getReplyList(preparationId,2,0);
        var replyList = replyModelResponseService.isSuccess()?replyModelResponseService.getResObjectData():new ArrayList<ReplyModel>();
        model.addAttribute("replyList", replyList);

        //4-1. 해당 QA의 댓글 목록 중 수정 가능한 날짜 반환 (params : fk로 설정된 아이디, 타입)
        responseLastDayService = t_replyService.getLastReply(preparationId,2);
        var lastReplyRegDt = responseLastDayService.isSuccess()?responseLastDayService.getResObjectData().getRegDt(): new ReplyModel();
        model.addAttribute("lastReplyRegDt", lastReplyRegDt);

        //4-2. 댓글 총 개수
        int countAllReply = t_replyService.getCountReply(preparationId,2);
        model.addAttribute("countAllReply",countAllReply);

        //5. 인풋에 전달할 값 세팅
        replyModel.setFkPreparationId(preparationId);
        replyModel.setType(2);
        replyModel.setFkSuserId(user.getUserId());

        if(division==0){
            return "/teacher/myLecture/review/lecture_review_detail";
        }else {
            return "/teacher/myLecture/preview/lecture_preview_detail";
        }
    }

    @GetMapping("/delete")
    public String deletePreparation(@LoginUser SessionUser user,
                                    @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                    @RequestParam(value = "division", defaultValue = "-1") Integer division,
                                    @RequestParam(value = "preparationId", defaultValue = "-1") Integer preparationId) {
        log.info("preparationId :::::::"+preparationId);
        String teacherId = user.getUserId();
        String msg = t_preparationService.deletePreparation(preparationId);
        if(msg.length()< 1){
            return "redirect:list?teacherId="+teacherId+"&teamId="+teamId+"&division="+division;
        }
        else{
            //TODO : 에러 뿌리기
            return "redirect:detail?teacherId="+teacherId+"&teamId="+teamId+"&preparationId="+preparationId;
        }
    }

    @PostMapping("/updateExposure")
    public ResponseEntity<HttpMessageModel> updateStatus(PreparationModel preparationModel) {
        log.info("preparationModel ::::::"+preparationModel);
        //1. 기존 노출 되는 내역 있는지 조회 -> 있으면 해제
        PreparationModel exPrepa = t_preparationService.getExposurePreparationId(preparationModel);
        if(exPrepa != null && !"".equals(exPrepa.getPreparationId())){
            log.info(">>>>> "+exPrepa.getPreparationId());
            exPrepa.setAppearance(0);
            t_preparationService.exposurePreparation(exPrepa);
        }
        preparationModel.setAppearance(1);
        log.info("preparationModel ::::::"+preparationModel);
        String msg = t_preparationService.exposurePreparation(preparationModel);
        if(msg.length()< 1){
            return new ResponseMessage(200, "success", "").getResponse();
        }
        else{
            return new ResponseMessage(500, msg, "재시도해주세요.").getResponse();
        }
    }

}

