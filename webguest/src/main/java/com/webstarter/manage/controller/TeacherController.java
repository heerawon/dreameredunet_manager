package com.webstarter.manage.controller;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.CategoryModel;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.model.TeacherModel;
import com.webstarter.manage.model.TeamModel;
import com.webstarter.manage.security.Role;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.security.model.UserModel;
import com.webstarter.manage.service.AuthService;
import com.webstarter.manage.service.TeacherService;
import com.webstarter.manage.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("teacher")
@AllArgsConstructor
public class TeacherController {
    private final AuthService authService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeamService teamService;

    @GetMapping("list")
    public String getTeacherList(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            Model model
                                 ) {
        List<TeacherModel> TeacherList = teacherService.getTeacherList();
        model.addAttribute("resList",TeacherList);
        model.addAttribute("page",page);

        return "ex_function/teacher/pub_teacher_list";
    }

    @GetMapping("changeIsMain")
    public String changeIsMain(@RequestParam("fkUserId") String fkUserId
                             , @RequestParam("teacherIsMain") int teacherIsMain) {
        TeacherModel teacher  = new TeacherModel();
        teacher.setFkUserId(fkUserId);
        if(teacherIsMain==0){
            teacher.setTeacherIsMain(1);
        }else{
            teacher.setTeacherIsMain(0);
        }
        teacherService.changeIsMain(teacher);
        return "redirect:list";
    }

    @GetMapping("register")
    public String getTeacherRegister(@RequestParam(value = "id", defaultValue = "") String id,
                                     @RequestParam(value = "type", defaultValue = "") String type,
                                     @RequestParam(value = "code", required = false, defaultValue = "") String code,
                                     @ModelAttribute TeacherModel teacherModel,
                                     Model model
                                     ) {
        if("edit".equals(type)){
            teacherModel = teacherService.getTeacherDetail(id);
            model.addAttribute("edit",1);
            model.addAttribute("urlType","update");
        }else{
            model.addAttribute("edit",0);
            model.addAttribute("urlType","insert");
        }
//        List<CategoryModel> categoryList = teacherService.getCategoryList();
//        model.addAttribute("categoryList",categoryList);

        model.addAttribute("reqModel",teacherModel);
        model.addAttribute("msg",ConstData.getCode(code)); // 에러미시지 추가는 제일 하단에
        return "ex_function/teacher/pub_teacher_register";
    }

    @PostMapping("insert")
    public String insertTeacher(TeacherModel teacherModel) {
        UserModel user = new UserModel();
        user.setUserId(teacherModel.getUserId());
        user.setUserPassword(teacherModel.getUserPassword());
        user.setUserName(teacherModel.getUserName());
        teacherModel.setFkUserId(teacherModel.getUserId());

        try{
            authService.saveUserTeacher(user,teacherModel);
        }catch (Exception e){
            String errMsg = "";
            if("Dup".equals(e.getMessage())){
                errMsg = "insertTeacher_001";
            }else{
                errMsg = "insertTeacher_002";
            }
            return "redirect:register?code="+ errMsg;
        }
        return "redirect:detail?id="+teacherModel.getUserId();
    }

    @GetMapping("detail")
    public String TeacherDetail(@RequestParam(value = "id", defaultValue = "") String fkUserId, Model model) {
        TeacherModel resTeacher = teacherService.getTeacherDetail(fkUserId);
        model.addAttribute("resModel",resTeacher);

        List<TeamModel> teamModel = teamService.getTeacherTeam(fkUserId);
        model.addAttribute("resList",teamModel);
        return "ex_function/teacher/pub_teacher_detail";
    }

    @PostMapping("update")
    public String updateTeacher(
            TeacherModel teacherModel) {
        String teacherId = teacherModel.getUserId();
        String resPath =  "redirect:detail";

        String resMsg = teacherService.updateTeacher(teacherModel);
        if(resMsg.length()>0){
            resPath = resPath + "?error=" +resMsg;
        }else{
            resPath = resPath +"?id="+ teacherId;
        }
        return resPath;
    }

    @GetMapping("moveItem")
    public String moveTeacherItem(
            @RequestParam(value = "fkUserId", defaultValue = "") String fkUserId,
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
                                  Model model) {
        teacherService.moveItem(fkUserId,type.equals("up") ? true:false);
        return "redirect:/teacher/list?page="+page;
    }


    /**
     * 최상단, 최하단 이동
     * @param fkUserId  이동 될 아이디 목록
     * @param type up, down
     * @param page 완료 후 datatable 해당 페이지로 이동
     * @return
     */
    @GetMapping("moveItemBothEnds")
    public String moveTeacherItemBothEnds(
            @RequestParam(value = "fkUserId", defaultValue = "") String[] fkUserId,
            @RequestParam(value = "type", defaultValue = "") String type,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            Model model) {


        log.info("fkUserId controller ::::::" + fkUserId[0]);

        teacherService.moveItemBothEnds(fkUserId,type.equals("up") ? true:false);
        teacherService.updateItemSorting();

        return "redirect:/teacher/list?page="+page;
    }

    @GetMapping("delete")
    public String deleteTeacher(@RequestParam(value = "id", defaultValue = "") String userId) {
        log.info("delete userId ::::::"+userId);
//        authService.deleteUser(userId);
        return "redirect:list";
    }

    /**
     * 새로운 pw로 변경
     *
     * @param newPwd
     * @return
     */
    @PostMapping("changeTeacherPw")
    @ResponseBody
    public String adminChangeTeacherPw(
                                  @RequestParam(defaultValue = "") String newPwd,
                                  @RequestParam(defaultValue = "") String teacherId,
                                  @LoginUser SessionUser user) {
        if(user.getRole()== Role.ADMIN){
            ResponseService<String> responseService = authService.updateUserPassword(newPwd,teacherId);  //공백 제거
            return responseService.isSuccess() ? "true" : "false";
        }
        return "false";
    }
}
