package com.webstarter.manage.security;

import com.webstarter.manage.configure.ConstData;
import com.webstarter.manage.model.LectureModel;
import com.webstarter.manage.model.TeacherModel;
import com.webstarter.manage.security.model.SessionUser;
import com.webstarter.manage.service.teacher.T_MyLectureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
        HttpSession session = request.getSession();
        SessionUser user = (SessionUser) session.getAttribute("user");

        log.info("onAuthenticationSuccess :::: user "+ user.toString());

        if (user.getRole() == Role.ADMIN) {
            setDefaultTargetUrl("/board/list");
        }else if(user.getRole() == Role.TEACHER){
            setDefaultTargetUrl("/mypage/moveto");
        }

        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            // 인증 받기 전 url로 이동하기
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }else{
            // 기본 url로 가도록 함함
            redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
        }


    }
}