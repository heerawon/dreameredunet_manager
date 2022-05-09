package com.webstarter.manage.controller;


import com.webstarter.manage.security.SecurityMemberDTO;
import com.webstarter.manage.security.SecurityMemberService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
@Log4j2
//https://otrodevym.tistory.com/entry/spring-boot-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0-8-security-%EC%84%A4%EC%A0%95-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%86%8C%EC%8A%A4?category=815039
@RequestMapping(value = "secu")
public class SecurityController {
    private SecurityMemberService securityMemberService;

    // 메인 페이지
    @GetMapping("/index")
    public String index() {
        log.info("index");
        return "secu/index";
    }

    // 회원 가입 페이지
    @GetMapping("/signup")
    public String dispSignup() {
        log.info("dispSignup : /signup");
        return "secu/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String execSignup(SecurityMemberDTO securityMemberDTO) {
        log.info("execSignup : /signup");

        securityMemberService.siginupMember(securityMemberDTO);
        return "redirect:/secu/user/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String dispLogin() {
        log.info("dispLogin");
        return "secu/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/login/result")
    public String dispLoginResult() {
        log.info("dispLoginResult");
        return "secu/login_success";
    }

    // 로그아웃 페이지
    @GetMapping("/logout/result")
    public String dispLogoutResult() {
        log.info("dispLogoutResult");
        return "secu/logout";
    }

    @GetMapping("/info")
    public String dispInfo() {
        log.info("dispInfo");
        return "secu/info";
    }

    @GetMapping("/admin")
    public String dispAdmin() {
        log.info("dispAdmin");
        return "secu/admin";
    }
}