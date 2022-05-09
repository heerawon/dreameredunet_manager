package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.teacher.QaService;
import com.webstarter.manage.service.teacher.T_ReplyService;
import com.webstarter.manage.service.teacher.T_TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("qa")
public class T_QaController {
    @Autowired
    private QaService qaService;

    @Autowired
    private T_TeamService t_teamService;

    @Autowired
    private T_ReplyService t_replyService;

    @GetMapping("/list")
    public String qaListByTeacher(Model model,
                                  @ModelAttribute QaModel qaModel,
                                  @LoginUser SessionUser user) {
        String teacherId = user.getUserId();

        //검색값 반환 teamId=&userName=&title=
        model.addAttribute("reqTeamId",qaModel.getTeamId());
        model.addAttribute("reqUserName",qaModel.getUserName());
        model.addAttribute("reqTitle",qaModel.getTitle());

        //1. 선생님의 팀 반환
        List<TeamModel> teamList = t_teamService.getTeamListByTeacher(teacherId);
        model.addAttribute("teamList", teamList);

        //2. 전체 QA 리스트 반환
        qaModel.setTeacherId(teacherId);
        log.info("qaModel ::::: ",qaModel);
        List<QaModel> qaList = qaService.getAllQaByTeacher(qaModel);
        model.addAttribute("resList",qaList);

        //3. 해결 QA 리스트 반환
        qaModel.setQaStatus(1);
        log.info("QaStatus ::::: ",qaModel.getQaStatus());
        List<QaModel> qaList2 = qaService.getAllQaByTeacher(qaModel);
        model.addAttribute("resList2",qaList2);

        //4. 미해결 QA 리스트 반환
        qaModel.setQaStatus(0);
        log.info("QaStatus ::::: ",qaModel.getQaStatus());
        List<QaModel> qaList3 = qaService.getAllQaByTeacher(qaModel);
        model.addAttribute("resList3",qaList3);

        return "/teacher/qa/pub_community_qa_list";
    }

    @GetMapping("/detail")
    public String qaDetail(Model model,
                           @RequestParam(value = "id", defaultValue = "-1") Integer qaId,
                           @ModelAttribute ReplyModel replyModel,
                           @LoginUser SessionUser user,
                           ResponseService<List<ReplyModel>> replyModelResponseService,
                           ResponseService<ReplyModel> responseLastDayService) {

        // 1. QA 상세 정보 조희
        QaModel resQa = qaService.getQaDetail(qaId);
        model.addAttribute("resModel",resQa);
        System.out.println("qaDetail :::::: qaId"+qaId);
        System.out.println("resModel ::::::"+model.getAttribute("resModel"));

        // 2. 해당 QA의 댓글 목록 가져오기
        replyModelResponseService = t_replyService.getReplyList(qaId,1,0);
        if(replyModelResponseService.isSuccess()){
            model.addAttribute("replyList", replyModelResponseService.getResObjectData());
        }

        // 2-1. 해당 QA의 댓글 목록 중 수정 가능한 날짜 반환 (params : fk로 설정된 아이디, 타입)
        responseLastDayService = t_replyService.getLastReply(qaId,1);
        if (responseLastDayService.isSuccess()) {
            model.addAttribute("lastReplyRegDt", responseLastDayService.getResObjectData().getRegDt());
            log.error("lastReplyRegDt"+ responseLastDayService.getResObjectData().getRegDt());
        }else{
            log.error("lastReplyRegDt"+ "????");
        }


        // 2-2. 댓글 총 개수
        int countAllReply = t_replyService.getCountReply(qaId,1);
        model.addAttribute("countAllReply",countAllReply);

        // 3. 인풋에 전달할 값 세팅
        replyModel.setFkQaId(qaId);
        replyModel.setType(1);
        replyModel.setFkSuserId(user.getUserId());

        return "/teacher/qa/pub_community_qa_detail";
    }

}
