package com.webstarter.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "pub2")
public class PubController2 {

    //일정
    @GetMapping(value = "/schedule")
    public String schedule(Model model) {
        return "/publish2/schedule";
    }

    //내강의 - 전체 대시보드
    @GetMapping(value = "/lecture/dashboard/total")
    public String lectureDashboardTotal(Model model) {
        return "/publish2/lecture_dashboard_total";
    }

    //내강의 - 팀 대시보드
    @GetMapping(value = "/lecture/dashboard/team")
    public String lectureDashboardTeam(Model model) {
        return "/publish2/lecture_dashboard_team";
    }

    //내강의 - 복습
    @GetMapping(value = "/lecture/review")
    public String lectureReview(Model model) {
        return "/publish2/lecture_review";
    }

    //내강의 - 복습등록
    @GetMapping(value = "/lecture/review/register")
    public String lectureReviewRegister(Model model) {
        return "/publish2/lecture_review_register";
    }

    //내강의 - 복습상세
    @GetMapping(value = "/lecture/review/detail")
    public String lectureReviewDetail(Model model) {
        return "/publish2/lecture_review_detail";
    }

    //내강의 - 예습
    @GetMapping(value = "/lecture/preview")
    public String lecturePreview(Model model) {
        return "/publish2/lecture_preview";
    }

    //내강의 - 예습등록
    @GetMapping(value = "/lecture/preview/register")
    public String lecturePreviewRegister(Model model) {
        return "/publish2/lecture_preview_register";
    }

    //내강의 - 예습상세
    @GetMapping(value = "/lecture/preview/detail")
    public String lecturePreviewDetail(Model model) {
        return "/publish2/lecture_preview_detail";
    }

    //내강의 - Q&A 목록
    @GetMapping(value = "/lecture/qa/list")
    public String lectureQaList(Model model) {
        return "/publish2/lecture_qa_list";
    }

    //내강의 - Q&A 상세
    @GetMapping(value = "/lecture/qa/detail")
    public String lectureQaDetail(Model model) {
        return "/publish2/lecture_qa_detail";
    }

    //내강의 - 과제 목록
    @GetMapping(value = "/lecture/homework/list")
    public String lectureHomeworkList(Model model) {
        return "/publish2/lecture_homework_list";
    }

    //내강의 - 과제 상세
    @GetMapping(value = "/lecture/homework/detail")
    public String lectureHomeworkDetail(Model model) {
        return "/publish2/lecture_homework_detail";
    }

    //내강의 - 출석현황
    @GetMapping(value = "/lecture/attendance")
    public String lectureAttendance(Model model) {
        return "/publish2/lecture_attendance";
    }

    //내강의 - 팀원목록
    @GetMapping(value = "/lecture/member/list")
    public String lectureMemberList(Model model) {
        return "/publish2/lecture_member_list";
    }



    //커뮤니티 - Q&A 목록
    @GetMapping(value = "/community/qa/list")
    public String communityQaList(Model model) {
        return "/publish2/community_qa_list";
    }

    //커뮤니티 - Q&A 상세
    @GetMapping(value = "/community/qa/detail")
    public String communityQaDetail(Model model) {
        return "/publish2/community_qa_detail";
    }

    //커뮤니티 - 과제 목록
    @GetMapping(value = "/community/homework/list")
    public String communityHomeworkList(Model model) {
        return "/publish2/community_homework_list";
    }

    //커뮤니티 - 과제 상세
    @GetMapping(value = "/community/homework/detail")
    public String communityHomeworkDetail(Model model) {
        return "/publish2/community_homework_detail";
    }

    //커뮤니티 - 내 정보
    @GetMapping(value = "/info")
    public String info(Model model) {
        return "/publish2/info";
    }



    //커뮤니티 - 내 정보
    @GetMapping(value = "/test")
    public String test(Model model) {
        return "/publish2/test";
    }




}