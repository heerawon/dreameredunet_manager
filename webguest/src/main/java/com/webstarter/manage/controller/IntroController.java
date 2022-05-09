package com.webstarter.manage.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("intro")
public class IntroController {
    /* 메인 페이지 */

    @GetMapping("/loginComplete")
    public String main(Authentication authentication) {

        log.info(":::::: "+authentication);

        if (authentication != null) {
            log.info("타입정보 : " + authentication.getClass());

            // 세션 정보 객체 반환
            WebAuthenticationDetails web = (WebAuthenticationDetails)authentication.getDetails();
            log.info("세션ID : " + web.getSessionId());
            log.info("접속IP : " + web.getRemoteAddress());

            // UsernamePasswordAuthenticationToken에 넣었던 UserDetails 객체 반환
            UserDetails userVO = (UserDetails) authentication.getPrincipal();
            log.info("ID정보 : " + userVO.getUsername());
        }

        return "security_user/home";
    }
}
