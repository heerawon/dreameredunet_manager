package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.security.model.UserModel;
import com.webstarter.manage.service.AuthService;
import com.webstarter.manage.service.teacher.MypageService;
import com.webstarter.manage.service.teacher.T_MyLectureService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("mypage")
@RequiredArgsConstructor
public class T_MypageController {
    private final AuthService authService;
    private final MypageService mypageService;
    private final T_MyLectureService t_myLectureService;

    @GetMapping("moveto")
    public String TeacherDetailOrDashboard (Model model,
                                            @RequestParam(defaultValue = "") String errCode,
                                            @LoginUser SessionUser user) {

        String resPage = "";
        String fkUserId = user.getUserId();

        //1. 강사의 팀 목록
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fkTuserId", fkUserId);
        List<LectureModel> teamList = t_myLectureService.selectTeamListByTeacher(hashMap);

        if(teamList.size()>0){
            resPage = "redirect:/myLecture/dashboard";
        }else {
            TeacherModel resTeacher = mypageService.getMyDetailInfo(fkUserId);
            model.addAttribute("resModel", resTeacher);
            model.addAttribute("errMsg", ConstData.getCode(errCode));
            resPage = "/teacher/mypage/pub_mypage_info";
        }

        return resPage;
    }

    @GetMapping("info")
    public String TeacherDetail(Model model,
                                @RequestParam(defaultValue = "") String errCode,
                                @LoginUser SessionUser user) {

        String resPage = "";
        String fkUserId = user.getUserId();

        TeacherModel resTeacher = mypageService.getMyDetailInfo(fkUserId);
        model.addAttribute("resModel", resTeacher);
        model.addAttribute("errMsg", ConstData.getCode(errCode));
        resPage = "/teacher/mypage/pub_mypage_info";

        return resPage;
    }

    /**
     * 새로운 pw로 변경
     *
     * @param newPwd
     * @return
     */
    @PostMapping("changePw")
    @ResponseBody
    public String TeacherChangePw(Model model,
                                  @RequestParam(defaultValue = "") String newPwd,
                                  @LoginUser SessionUser user) {
        ResponseService<String> responseService = authService.updateUserPassword(newPwd, user.getUserId());  //공백 제거
        return responseService.isSuccess() ? "true" : "false";


    }
}
