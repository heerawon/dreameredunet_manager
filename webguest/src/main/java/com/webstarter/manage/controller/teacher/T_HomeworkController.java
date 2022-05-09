package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.teacher.HomeworkService;
import com.webstarter.manage.service.teacher.T_ReplyService;
import com.webstarter.manage.service.teacher.T_TeamService;
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
@RequestMapping("homework")
public class T_HomeworkController {
    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private T_TeamService t_teamService;

    @Autowired
    private T_ReplyService t_replyService;

    @GetMapping("/list")
    public String homeworkListByTeacher(Model model,
                                        @ModelAttribute HomeworkModel homework,
                                        @LoginUser SessionUser user) {

        String teacherId = user.getUserId();

        //검색값 반환 teamId=&userName=&title=
        model.addAttribute("reqTeamId",homework.getTeamId());
        model.addAttribute("reqUserName",homework.getUserName());
        model.addAttribute("reqTitle",homework.getTitle());

        List<TeamModel> teamList = t_teamService.getTeamListByTeacher(teacherId);
        model.addAttribute("teamList", teamList);

        homework.setTeacherId(teacherId);
        log.info("homework ::::: "+homework);
        List<HomeworkModel> homeworkList = homeworkService.getHomeworkListByTeacher(homework);
        model.addAttribute("resList",homeworkList);

        return "/teacher/homework/pub_community_homework_list";
    }

    @GetMapping("/detail")
    public String homeworkDetail(Model model,
                                 @RequestParam(value = "id", defaultValue = "-1") Integer homeworkId,
                                 @ModelAttribute ReplyModel replyModel,
                                 @LoginUser SessionUser user,
                                 HomeworkModel resHomework,
                                 ResponseService<List<ReplyModel>> replyModelResponseService,
                                 ResponseService<ReplyModel> responseLastDayService) {

        // 1. 과제 상세 정보 조회
        resHomework = homeworkService.getHomeworkDetail(homeworkId);
        model.addAttribute("resModel",resHomework);

        // 2. 해당 과제의 댓글 목록 가져오기
        replyModelResponseService = t_replyService.getReplyList(homeworkId,0,0);
        if(replyModelResponseService.isSuccess()){
            model.addAttribute("replyList", replyModelResponseService.getResObjectData());
        }

        // 2-1. 해당 과제의 댓글 목록 중 수정 가능한 날짜 반환 (params : fk로 설정된 아이디, 타입)
        responseLastDayService = t_replyService.getLastReply(homeworkId,0);
        if (responseLastDayService.isSuccess()) {
            model.addAttribute("lastReplyRegDt", responseLastDayService.getResObjectData().getRegDt());
        }

        // 2-2. 댓글 총 개수
        int countAllReply = t_replyService.getCountReply(homeworkId,0);
        model.addAttribute("countAllReply",countAllReply);

        // 3. 인풋에 전달할 값 세팅
        replyModel.setFkHomeworkId(homeworkId);
        replyModel.setType(0);
        replyModel.setFkSuserId(user.getUserId());

        return "/teacher/homework/pub_community_homework_detail";
    }

}
