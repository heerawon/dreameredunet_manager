package com.webstarter.manage.controller;

import com.webstarter.manage.model.HttpMessageModel;
import com.webstarter.manage.model.ResponseMessage;
import com.webstarter.manage.model.ResponseService;
import com.webstarter.manage.service.WithdrawalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("withdrawal")
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/updateUserInfo")
    public ResponseEntity<HttpMessageModel> withdrawalStudentTest (@RequestParam(value = "id", defaultValue = "") String userId,
                                                                   ResponseService<String> responseDeleteService) {
        log.info("withdrawal userId ::::::"+userId);

        try {
            responseDeleteService = withdrawalService.updateUserWithdrawal(userId);
            if(responseDeleteService.isSuccess()){
                log.info("new id : "+responseDeleteService.getResObjectData());
                return new ResponseMessage(200, "success", responseDeleteService.getResObjectData()).getResponse();
            }else {
                return new ResponseMessage(202, "success", "변경되지 않았습니다. 재시도해주세요. ").getResponse();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(500, e.getMessage(), "재시도해주세요.").getResponse();
        }
    }
}
