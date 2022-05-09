package com.webstarter.manage.security;


import com.webstarter.manage.security.model.SessionUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class SecurityMemberService implements UserDetailsService {
    private final HttpSession httpSession;
    private final SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityMemberEntiry> securityMemberEntiryOptional = securityRepository.findByUserId(username);

        //계정이 없으면 null
        SecurityMemberEntiry securityMemberEntiry = securityMemberEntiryOptional.orElse(null);


        if (securityMemberEntiry == null) {
            throw new UsernameNotFoundException("로그인 실패");
        }

        log.info("securityMemberEntiry :::::: " + securityMemberEntiry.toString());


        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(securityMemberEntiry.getRoleKey()));
//        authorities.add(new SimpleGrantedAuthority(Role.TEACHER.getKey()));

        log.info("username : " + username);

        //Session 에 로그인한 유저 정보 저장
        httpSession.setAttribute("user", new SessionUser(securityMemberEntiry));

        return new User(securityMemberEntiry.getUserId(), securityMemberEntiry.getUserPassword(), authorities);
    }

    public SecurityMemberEntiry siginupMember(SecurityMemberDTO securityMemberVO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        securityMemberVO.setUserPassword(bCryptPasswordEncoder.encode(securityMemberVO.getUserPassword()));
        //회원가입 처리
        return securityRepository.save(securityMemberVO.toEntity());
    }
}