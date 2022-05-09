package com.webstarter.manage.controller;

import com.webstarter.manage.model.CelebrityLectureModel;
import com.webstarter.manage.model.HttpMessageModel;
import com.webstarter.manage.model.ResponseMessage;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.service.CelebrityLectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("celebLecture")
public class CelebrityLectureController {

    @Autowired
    private CelebrityLectureService ceLecService;

    @GetMapping("/celebrityLectures")
    public String getcelebrityLectures(Model model,
                                       @RequestParam(defaultValue = "") String userName,
                                       @RequestParam(defaultValue = "") String studentGender,
                                       @RequestParam(defaultValue = "") String birthStartDt,
                                       @RequestParam(defaultValue = "") String birthEndDt,
                                       @RequestParam(defaultValue = "") String searchStartDt,
                                       @RequestParam(defaultValue = "") String searchEndDt,
                                       ResponseService<List<CelebrityLectureModel>> responseCelecList) {

        responseCelecList = ceLecService.getCelebLectureList(userName, studentGender, birthStartDt, birthEndDt, searchStartDt, searchEndDt);
        if(responseCelecList.isSuccess()){
            model.addAttribute("resList",responseCelecList.getResObjectData());
            model.addAttribute("userName",userName);
            model.addAttribute("studentGender",studentGender);
            model.addAttribute("birthStartDt",birthStartDt);
            model.addAttribute("birthEndDt",birthEndDt);
            model.addAttribute("searchStartDt",searchStartDt);
            model.addAttribute("searchEndDt",searchEndDt);
        }

        return "ex_function/celebrity_lecture/pub_celebrity_lecture_list";
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<HttpMessageModel> updateStatus(CelebrityLectureModel ceLecModel) {
        String msg = ceLecService.updateStatus(ceLecModel);
        if(msg.length()< 1){
            return new ResponseMessage(200, "success", "").getResponse();
        }
        else{
            return new ResponseMessage(500, msg, "재시도해주세요.").getResponse();
        }
    }

}
