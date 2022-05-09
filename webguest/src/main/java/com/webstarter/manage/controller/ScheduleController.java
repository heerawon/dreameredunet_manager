package com.webstarter.manage.controller;


import com.webstarter.manage.mapper.db1.UserMapper;
import com.webstarter.manage.model.StudentModel;
import com.webstarter.manage.security.model.UserModel;
import com.webstarter.manage.service.SuremBisService;
import com.webstarter.manage.service.WithdrawalService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 주요기능
 *  매일 00시에 유저 등급변경
 *  오전 8시에 얼마 남지 않았음 전송
 */
@Component
@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class ScheduleController {
    private final UserMapper userMapper;
    private final WithdrawalService withdrawalService;

    @Scheduled(cron="0 0 00 * * ?")
    public void checkExpiry() {
        // * 단일 스레드로실행됩니다.
        userMapper.userUpdateRoleByExpiryDate();
        withdrawalService.updateUserWithdrawalByList();
    }
    @Scheduled(cron="0 0 09 * * ?")
    public void checkExpirySoon() {
        // * 단일 스레드로실행됩니다.
        // 구독이 7일 남은경우 발송
        List<String> lsCell = new ArrayList<String>();
        List<StudentModel> lsUser = userMapper.getUserListExpiredSoon(7);
        for(StudentModel model : lsUser){
            if(model.getStudentCell().length()>10){
                lsCell.add(model.getStudentCell());
            }
        }
        if(lsCell.size()>0){
            SuremBisService.callApiSubscribe(lsCell);
        }

    }

    @Scheduled(cron="0 25 20 * * ?")
    public void test() {
        // * 단일 스레드로실행됩니다.
        withdrawalService.updateUserWithdrawalByList();
    }

}
