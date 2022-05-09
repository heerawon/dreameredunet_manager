package com.webstarter.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping(value = "pub")
public class PubController {

    //공지사항 목록
    @GetMapping(value = "/notice/list")
    public String noticeList(Model model) {
        return "/publish/notice_list";
    }

    //공지사항 상세
    @GetMapping(value = "/notice/detail")
    public String noticeDetail(Model model) {
        return "/publish/notice_detail";
    }

    //공지사항 등록&수정
    @GetMapping(value = "/notice/register")
    public String noticeRegister(Model model) {
        return "/publish/notice_register";
    }


    //강사 목록
    @GetMapping(value = "/teacher/list")
    public String teacherList(Model model) {
        return "/publish/teacher_list";
    }

    //강사 상세
    @GetMapping(value = "/teacher/detail")
    public String teacherDetail(Model model) {
        return "/publish/teacher_detail";
    }

    //강사 등록&수정
    @GetMapping(value = "/teacher/register")
    public String teacherRegister(Model model) {
        return "/publish/teacher_register";
    }


    //팀 목록
    @GetMapping(value = "/team/list")
    public String teamList(Model model) {
        return "/publish/team_list";
    }

    //팀 상세
    @GetMapping(value = "/team/detail")
    public String teamDetail(Model model) {
        return "/publish/team_detail";
    }

    //팀 등록 - 선생 목록
    @GetMapping(value = "/team/register/teacher")
    public String teamRegisterTeacher(Model model) {
        return "/publish/team_register_teacher";
    }

    //팀 등록 - 학생 목록
    @GetMapping(value = "/team/register/student")
    public String teamRegisterStudent(Model model) {
        return "/publish/team_register_student";
    }

    //팀 등록1
    @GetMapping(value = "/team/register1")
    public String teamRegister1(Model model) {
        return "/publish/team_register1";
    }

    //팀 등록2
    @GetMapping(value = "/team/register2")
    public String teamRegister2(Model model) {
        return "/publish/team_register2";
    }

    //신청대기회원 목록
    @GetMapping(value = "/team/wait/list")
    public String classWaitList(Model model) {
        return "/publish/team_wait_list";

    }



    //특별활동 팀목록
    @GetMapping(value = "/special/team/list")
    public String specialTeamList(Model model) {
        return "/publish/special_team_list";
    }

    //특별활동 팀 상세
    @GetMapping(value = "/special/team/detail")
    public String specialTeamDetail(Model model) {
        return "/publish/special_team_detail";
    }

    //특별활동 팀 등록 - 선생 목록
    @GetMapping(value = "/special/team/register/teacher")
    public String specialTeamRegisterTeacher(Model model) {
        return "/publish/special_team_register_teacher";
    }

    //특별활동 팀 등록 - 학생 목록
    @GetMapping(value = "/special/team/register/student")
    public String specialTeamRegisterStudent(Model model) {
        return "/publish/special_team_register_student";
    }

    //특별활동 팀 등록1
    @GetMapping(value = "/special/team/register1")
    public String specialTeamRegister1(Model model) {
        return "/publish/special_team_register1";
    }

    //특별활동 팀 등록2
    @GetMapping(value = "/special/team/register2")
    public String specialTeamRegister2(Model model) {
        return "/publish/special_team_register2";
    }

    //특별활동 신청대기회원 목록
    @GetMapping(value = "/special/team/wait/list")
    public String specialTeamWaitList(Model model) {
        return "/publish/special_team_wait_list";
    }



    //사용자 목록
    @GetMapping(value = "/user/list")
    public String userList(Model model) {
        return "/publish/user_list";
    }

    //사용자 수정
    @GetMapping(value = "/user/update")
    public String userUpdate(Model model) {
        return "/publish/user_update";
    }



    //주문 목록
    @GetMapping(value = "/puborder/list")
    public String orderList(Model model) {
        return "/publish/order_list";
    }

    //주문 상세
    @GetMapping(value = "/puborder/detail")
    public String orderDatail(Model model) {
        return "/publish/order_detail";
    }


    //배송 목록
    @GetMapping(value = "/order/delivery/list")
    public String orderDeliveryList(Model model) {
        return "/publish/order_delivery_list";
    }

    //배송 등록1
    @GetMapping(value = "/order/delivery/register1")
    public String orderDeliveryRegister1(Model model) {
        return "/publish/order_delivery_register1";
    }

    //배송 등록2
    @GetMapping(value = "/order/delivery/register2")
    public String orderDeliveryRegister2(Model model) {
        return "/publish/order_delivery_register2";
    }

    //배송 등록 - 목록에서 발송 버튼 클릭 시
    @GetMapping(value = "/order/delivery/register3")
    public String orderDeliveryRegister3(Model model) {
        return "/publish/order_delivery_register3";
    }

    //배송 등록 - 학생선택
    @GetMapping(value = "/order/student/choice")
    public String orderStudentChoice(Model model) {
        return "/publish/order_student_choice";
    }

    //개인정보 처리방침
    @GetMapping(value = "/setting/terms")
    public String settingTerms(Model model) {
        return "/publish/setting_terms";
    }

    //이용약관
    @GetMapping(value = "/setting/terms2")
    public String settingTerms2(Model model) {
        return "/publish/setting_terms2";
    }

    //구독료 관리
    @GetMapping(value = "/setting/subscribe")
    public String settingSubscribe(Model model) {
        return "/publish/setting_subscribe";
    }

    //구독료 관리
    @GetMapping(value = "/setting/products")
    public String settingProducts(Model model) {
        return "/publish/setting_products";
    }


    @GetMapping("/upload")
    public String mainUploadTestPage22(Map<String, Object> reqMap, Model model) {
        return "ex_main/main_upload";
    }




}