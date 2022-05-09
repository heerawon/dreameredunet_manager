package com.webstarter.manage.controller;

import com.webstarter.manage.configure.LoginUser;
import com.webstarter.manage.mapper.db1.UserMapper;
import com.webstarter.manage.model.FeeModel;
import com.webstarter.manage.service.FeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Log4j2
@Controller
@RequestMapping("expired")
@RequiredArgsConstructor
public class expiredController {
    private final UserMapper userMapper;


    @GetMapping("update")
    @ResponseBody
    public String getTermRegistPage(Model model, @ModelAttribute FeeModel feeModel,

                                    HttpSession session) {
        var res = session.getAttributeNames();


        while(res.hasMoreElements()){
            log.info(res.nextElement());
        }


        userMapper.userUpdateRoleByExpiryDate();
        return "done";
    }


}
