package com.webstarter.manage.controller.teacher;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.model.*;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.LectureService;
import com.webstarter.manage.service.TeacherService;
import com.webstarter.manage.service.TeamService;
import com.webstarter.manage.service.teacher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("myLecture")
public class T_MyLectureController {

    @Autowired
    private T_MyLectureService t_myLectureService;

    @Autowired
    private T_TeamService t_teamService;

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private QaService qaService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private T_PreparationService t_preparationService;

    @Autowired
    private LectureService lectureService;

    //전체 대시보드
    @GetMapping("/dashboard")
    public String dashboardInfo(
            Model model,
            @LoginUser SessionUser user,
            @RequestParam(value = "division", defaultValue = "") String division,
            @RequestParam(value = "progress", defaultValue = "") String progress,
            @RequestParam(defaultValue = "") String errCode,
            HomeworkModel homework, QaModel qaModel) {

        String teacherId = user.getUserId();
        //1. 강사의 팀 목록
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fkTuserId", teacherId);
        hashMap.put("division", division);
        hashMap.put("progress", progress);
        List<LectureModel> teamList = t_myLectureService.selectTeamListByTeacher(hashMap);
        model.addAttribute("teamList", teamList);
        model.addAttribute("division", division);
        model.addAttribute("progress", progress);
        //2. 강사의 과제 목록 (Limit 5)
        homework.setTeacherId(teacherId);
        List<HomeworkModel> homeworkList = homeworkService.getHomeworkListByTeacherLimit(homework);
        model.addAttribute("homeworkList", homeworkList);
        //3. 강사의 QA 목록 (Limit 5)
        qaModel.setTeacherId(teacherId);
        List<QaModel> qaList = qaService.getAllQaByTeacherLimit(qaModel);
        model.addAttribute("qaList", qaList);
        //4. 수강 학생 목록
        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("fkTuserId", teacherId);
        List<StudentModel> studentList = t_myLectureService.selectMyLectureStudentList(hashMap2);

        model.addAttribute("studentList", studentList);

        model.addAttribute("errMsg", ConstData.getCode(errCode)); // 에러메시지 최하단
        return "/teacher/myLecture/lecture_dashboard_total";
    }

    //팀 대시보드
    @GetMapping("/teamDashboard")
    public String dashboardInfo(@LoginUser SessionUser user,
                                @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                HomeworkModel homework, QaModel qaModel, Model model) {

        String teacherId = user.getUserId();

        //1. 선생님의 팀 목록 가져오기
        List<TeamModel> myTeamList = t_myLectureService.getBtnTeacherTeam(teacherId);
        model.addAttribute("myTeamList", myTeamList);
        //2. 팀 기초정보 가져오기
        TeamModel myTeam = teamService.getTeamDetail(teamId);
        model.addAttribute("myTeam", myTeam);
        int countMember = teamService.getTeamMemberCount(teamId);
        model.addAttribute("countMember", countMember);
        int countLecture = teamService.getLectureCount(teamId);
        model.addAttribute("countLecture", countLecture);
        //3. 복습 회차 가져오기
        PreparationModel reqModel = new PreparationModel();
        reqModel.setFkTeamId(teamId);
        reqModel.setDivision(0);
        PreparationModel reviewModel = t_preparationService.getExposurePreparationId(reqModel);
        HashMap<String, Integer> reqhashMap = new HashMap<>();
        reqhashMap.put("fkTeamId", teamId);
        reqhashMap.put("division", 0);
        int reviewCount = t_preparationService.selectPreparationCount(reqhashMap);
        model.addAttribute("reviewModel", reviewModel);
        model.addAttribute("reviewCount", reviewCount);
        //4. 예습 회차 가져오기
        reqModel.setDivision(1);
        PreparationModel previewModel = t_preparationService.getExposurePreparationId(reqModel);
        reqhashMap.put("division", 1);
        int previewCount = t_preparationService.selectPreparationCount(reqhashMap);
        model.addAttribute("previewModel", previewModel);
        model.addAttribute("previewCount", previewCount);
        //5. 출석현황 가져오기 (추후)

        //6. 수강학생목록 가져오기 (5명) //TODO: 경우에 따라 Limit
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fkTuserId", teacherId);
        hashMap.put("teamId", teamId.toString());
        List<StudentModel> studentList = t_myLectureService.selectMyLectureStudentList(hashMap);
        model.addAttribute("studentList", studentList);
        //7. 해당팀의 QA 가져오기
        qaModel.setTeacherId(teacherId);
        qaModel.setTeamId(teamId);
        List<QaModel> qaList = qaService.getAllQaByTeacherLimit(qaModel);
        model.addAttribute("qaList", qaList);
        //8. 해당팀의 과제 가져오기
        homework.setTeacherId(teacherId);
        homework.setTeamId(teamId);
        List<HomeworkModel> homeworkList = homeworkService.getHomeworkListByTeacherLimit(homework);
        model.addAttribute("homeworkList", homeworkList);

        System.out.println("myTeamList ::::: " + model.getAttribute("myTeamList"));
        System.out.println("myTeam ::::: " + model.getAttribute("myTeam"));
        System.out.println("countMember ::::: " + model.getAttribute("countMember"));
        System.out.println("studentList ::::: " + model.getAttribute("studentList"));
        System.out.println("homeworkList ::::: " + model.getAttribute("homeworkList"));
        System.out.println("qaList ::::: " + model.getAttribute("qaList"));

        return "/teacher/myLecture/lecture_dashboard_team";
    }

    //학생 목록
    @GetMapping("/teamMemberList")
    public String teamMemberList(@LoginUser SessionUser user,
                                 @RequestParam(value = "teamId", defaultValue = "-1") Integer teamId,
                                 Model model) {
        String teacherId = user.getUserId();

        //1. 선생님의 팀 목록 가져오기
        List<TeamModel> myTeamList = t_myLectureService.getBtnTeacherTeam(teacherId);
        model.addAttribute("myTeamList", myTeamList);
        //2. 팀 기초정보 가져오기
        TeamModel myTeam = teamService.getTeamDetail(teamId);
        model.addAttribute("myTeam", myTeam);
        int countMember = teamService.getTeamMemberCount(teamId);
        model.addAttribute("countMember", countMember);
        int countLecture = teamService.getLectureCount(teamId);
        model.addAttribute("countLecture", countLecture);
        //3. 수강학생목록 가져오기 (전체)
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fkTuserId", teacherId);
        hashMap.put("teamId", teamId.toString());
        List<StudentModel> studentList = t_myLectureService.selectStudentDetailList(hashMap);
        model.addAttribute("studentList", studentList);

        return "/teacher/myLecture/student/lecture_member_list";
    }



}
