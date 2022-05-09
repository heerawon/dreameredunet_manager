package com.webstarter.manage.controller.teacher;


import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.HttpMessageModel;
import com.webstarter.manage.model.ReplyModel;
import com.webstarter.manage.model.ResponseMessage;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.teacher.T_ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping(value = "reply")
@RequiredArgsConstructor
public class T_ReplyController {
    private final T_ReplyService replyService;

    @GetMapping(value = "list")
    public String replyList (Model model){
        return "";
    }

    /**
     * 댓글 등록
     * @param replyModel
     */
    @PostMapping("/insert")
    public String insertReply(@ModelAttribute ReplyModel replyModel,
                              @LoginUser SessionUser user,
                              ResponseService<String> responseReplyInsert) throws Exception {

        log.info("replyModel :::::::: "+replyModel);

        String resPage = "";
        Integer type = replyModel.getType();

        switch (type){
            case 0:
                resPage = "redirect:/homework/detail?id=" + replyModel.getFkHomeworkId();
                break;
            case 1:
                resPage = "redirect:/qa/detail?id=" + replyModel.getFkQaId();
                break;
            case 2:
                Integer teamId = replyModel.getTeamId();
                Integer division = replyModel.getDivision();
                resPage = "redirect:/preparation/detail?teamId="+teamId+"&division="+division+"&preparationId=" + replyModel.getFkPreparationId();
                break;
            default:
        }

        replyModel.setFkTuserId(user.getUserId());
        replyModel.setFkSuserId("");
        responseReplyInsert = replyService.insertReply(replyModel);

        return resPage;
    }

    /**
     * 댓글 수정
     * @param replyModel
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<HttpMessageModel> updateReply(@ModelAttribute ReplyModel replyModel,
                                                        @LoginUser SessionUser user,
                                                        ResponseService<String> responseReplyUpdate) {
        replyModel.setFkTuserId(user.getUserId());
        replyModel.setFkSuserId("");
        responseReplyUpdate = replyService.updateReply(replyModel);
        if(responseReplyUpdate.isSuccess()){
            return new ResponseMessage(200, "success", "").getResponse();
        }
        else{
            return new ResponseMessage(500, responseReplyUpdate.getErrorMsg(), "재시도해주세요.").getResponse();
        }
    }

    /**
     * 댓글 삭제
     * @param replyModel
     */
    @PostMapping("/delete")
    public ResponseEntity<HttpMessageModel> deleteReply(@ModelAttribute  ReplyModel replyModel,
                                                        ResponseService<String> responseDelete) {

        responseDelete = replyService.deleteReply(replyModel);
        if(responseDelete.isSuccess()){
            return new ResponseMessage(200, "success", "").getResponse();
        }
        else{
            return new ResponseMessage(500, responseDelete.getErrorMsg(), "재시도해주세요.").getResponse();
        }

    }

    /**
     * 댓글 리스트 (+더보기와 공통으로 사용)
     * @param replyModel 기준 아이디 (qaId : 마지막 아이디, length: 기존 목록의 길이가 필요함 )
     */
    @PostMapping("/more")
    public ResponseEntity<HttpMessageModel> getMoreReply(ReplyModel replyModel,
                                                         ResponseService<List<ReplyModel>> responseReplyList) {
        int paramId = -1;
        int length = replyModel.getLength();
        int type = replyModel.getType();
        switch (type){
            case 0:
                paramId = replyModel.getFkHomeworkId();
                break;
            case 1:
                paramId = replyModel.getFkQaId();
                break;
            case 2:
                paramId = replyModel.getFkPreparationId();
                break;
        }

        responseReplyList = replyService.getReplyList(paramId,type,length);

        if(responseReplyList.isSuccess()){
            if(responseReplyList.getErrorMsg().length()>0){
                return new ResponseMessage(202, responseReplyList.getErrorMsg(), responseReplyList.getResObjectData()).getResponse();
            } else {
                return new ResponseMessage(200, "success", responseReplyList.getResObjectData()).getResponse();
            }
        } else {
            return new ResponseMessage(500, "false", "재시도해주세요.").getResponse();
        }
    }

}
